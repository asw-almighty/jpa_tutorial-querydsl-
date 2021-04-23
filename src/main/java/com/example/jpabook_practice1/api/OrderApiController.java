package com.example.jpabook_practice1.api;

import com.example.jpabook_practice1.dto.OrderDto;
import com.example.jpabook_practice1.dto.OrderItemDto;
import com.example.jpabook_practice1.entity.Order;
import com.example.jpabook_practice1.entity.OrderItem;
import com.example.jpabook_practice1.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * v1: 엔티티 직접 노출
 * v2: 엔티티로 조회, DTO로 반환(페치조인x)
 * v3: 엔티티로 조회, DTO로 반환(페치조인o) -> 페이징이 안됨!
 * v3.1: v3의 페이징 못하는 문제점 해결!
 * v4: DTO로 조회, 컬렉션 N 조회
 * v5: DTO로 조회, 컬렉션 1조회 @@@@@@@@@@최적화 버전(쿼리1+1)
 * v6: DTO로 조회, 플랫 데이터(쿼리는1이지만 페이징x)
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController
{
	private final OrderRepository orderRepository;

	/**
	 * v1: 엔티티 직접 노출
	 * 엔티티를 직접 노출하므로... 좋지 않은 방법
	 * @return
	 */
	@GetMapping("/api/v1/orders")
	public List<Order> ordersV1()
	{
		List<Order> orders = orderRepository.findAll();

		//LAZY로 인한 강제 초기화. 해주지 않으면 null로 반환된다.
		for (Order order : orders)
		{
			order.getMember().getName();
			order.getDelivery().getAddress();

			List<OrderItem> orderItems = order.getOrderItems();
			orderItems
					.stream().forEach(oi -> oi.getItem().getName());
		}

		return orders;
	}

	/**
	 * v2: 엔티티로 호출하고, DTO로 변환해서 반환.(fetch join 기능을 쓰지 않음)
	 * SQL 실행 = order조회(1번)
	 *           member조회(N번)+delivery조회(N번)+orderItems조회(N번)
	 *                                          item조회(N번)
	 * 쿼리가 엄청 나온다.
	 * @return
	 */
	@GetMapping("/api/v2/orders")
	public List<OrderDto> ordersV2()
	{
		List<Order> orders = orderRepository.findAll();

		return orders.stream().map(OrderDto::new).collect(Collectors.toList());
	}

	/**
	 * v3: 엔티티로 호출하고, DTO로 변환해서 변환.(fetch join 기능 사용) 컬렉션 페치 조인.
	 * jpql에 distinct가 있는데, order와 orderItems가 일대다 조건이기때문에 join을 하면 order가 중복된다.
	 * JPA의 distinct는 SQL의 distinct의 기능과 함께, 같은 엔티티가 조회되면 애플리케이션에서 중복을 걸러준다.
	 *
	 * 단점! 페이징 불가능 >> id는 같은데 orderItems의 내용이 다르기 때문에 데이터베이스에서 DISTINCT로 제외되지 않는다.
	 *                    중복 처리되지 않은 정보가 페이징되서 DB로부터 애플리케이션으로 넘어오면, 정보가 꼬여버린다. 그래서 페이징은 안된다!
	 *                    컬렉션 페치 조인 할때는 페이징을 쓰면 안된다!
	 * @return
	 */
	@GetMapping("/api/v3/orders")
	public List<OrderDto> orderV3()
	{
		List<Order> orders = orderRepository.findAllWithFetchJoinV3();

		return orders.stream().map(OrderDto::new).collect(Collectors.toList());
	}

	/**
	 * v3.1: 엔티티로 조회, DTO로 반환.
	 * @@특징: 먼저 ToOne 관계만 페치 조인으로 최적화. 컬렉션 관계는 @BatchSize로 최적화!
	 * spring.jpa.properties.hibernate.default_batch_fetch_size:1000으로 설정!
	 *
	 * 장점: 쿼리 호출 수가 1+N >> 1+1로 바뀐다.. 근데 지금 해보면 전부 하나로 가져온다.
	 * 페치 조인 방식과 비교해서 쿼리 호출수가 약간은 증가하지만, DB 전송량이 감소한다.
	 * 이 방식은 페이징 가능!
	 * @return
	 */
	@GetMapping("/api/v3.1/orders")
	public List<OrderDto> orderv3_page(@RequestParam(value="offset", defaultValue = "0") int offset,
	                                   @RequestParam(value="limit", defaultValue = "100") int limit)
	{
		List<Order> orders = orderRepository.findAllWithFetchJoin_page(offset, limit);

		return orders.stream()
				.map(OrderDto::new).collect(Collectors.toList());
	}
/**
 * V4할차례!
 */
}
