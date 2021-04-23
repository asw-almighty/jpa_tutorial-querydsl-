package com.example.jpabook_practice1.api;

import com.example.jpabook_practice1.dto.SimpleOrderDto;
import com.example.jpabook_practice1.dto.SimpleOrderQueryDto;
import com.example.jpabook_practice1.entity.Order;
import com.example.jpabook_practice1.repository.OrderRepository;
import com.example.jpabook_practice1.repository.query.OrderQueryRepository;
import javassist.Loader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController
{
	private final OrderRepository orderRepository;
	private final OrderQueryRepository orderQueryRepository;
	/**
	 * v1: 주문조회
	 * hibernate5Module을 Bean 에 등록해주자!(프록시 때문에 Jackson라이브러리가 Json을 생성할 때 오류가 난다)
	 * hibernate5module은 초기화된 프록시 객체만 노출한다. 아닌건 null
	 *
	 * member와 delivery에 각각 @jsonignore를 해줘야 하고..
	 * orderItems는 초기화되지 않았으므로 null로 표시된다.
	 *
	 * 문제: 엔티티를 직접 노출.
	 *      지연 로딩이 있기 때문에, 프록시가 존재한다.
	 *      jackson객체는 프록시를 json으로 어떻게 생성하는지 모른다.
	 * @return
	 */
	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1()
	{
		List<Order> all = orderRepository.findAll();

		//강제초기화(LAZY로 되어 있어, 프록시를 받아오기 때문)
		for (Order order : all)
		{
			order.getMember().getName();
			order.getDelivery().getAddress();
		}

		return all;
	}

	/**
	 * v2: 엔티티를 DTO로 변환
	 *
	 * 엔티티를 조회해서 DTO로 변환(fetch join은 사용x)
	 * -단점: 지연로딩으로 쿼리 N(지연로딩의 개수)번 호출
	 * 처음 order를 호출 (1번)
	 *
	 * (조회된 order1)
	 * 그다음 member를 호출 (1번)
	 * 그다음 delivery를 호출 (1번)
	 *
	 * (조회된 order2)
	 * 그다음 member를 호출 (1번)
	 * 그다음 delivery를 호출 (1번)
	 *
	 * 총 1 + N + N (N:지연로딩의개수는 2)
	 * @return
	 */
	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> orderV2()
	{
		List<Order> orders = orderRepository.findAll();

		List<SimpleOrderDto> result = orders
				.stream()
				.map(SimpleOrderDto::new)
				.collect(Collectors.toList());

		return result;
	}

	/**@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	 * v3: 페치 조인@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	 * 쿼리 한번에 조회!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	 * 문제 - 엔티티로 조회했다.@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	 * 그래도 이게 제일 좋다@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	 * @return
	 */
	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDto> orderV3()
	{
		List<Order> orders = orderRepository.findAllWithFetchJoin();

		List<SimpleOrderDto> result = orders
				.stream()
				.map(SimpleOrderDto::new)
				.collect(Collectors.toList());

		return result;
	}

	/**
	 * v4: 엔티티가 아닌, DTO로 Fetch join!
	 * 일반적인 SQL처럼 원하는 값을 직접 조회!
	 * -문제 : 리포지토리 재사용성 떨어짐, API 스펙에 맞춘 코드가 리포지토리에 들어가는 단점이 있음!
	 *
	 *
	 * 쿼리 방식 선택 방법!
	 * 1. 우선 엔티티를 먼저 조회하고, 이후에 DTO로 변환하는 방법 사용!
	 * 2. 필요하면 페치 조인으로 성능 최적화
	 * 3. 그래도 성능이 안나오면 DTO로 직접 조회하는 방법 사용!
	 * 4. 그래도 성능이 안나오면 최후의 방법은 네이티브 SQL이나 스프링 JDBC Template을 이용해서 해결!
	 * @return
	 */
	@GetMapping("/api/v4/simple-orders")
	public List<SimpleOrderQueryDto> orderV4()
	{
		return orderQueryRepository.findAllWithFetchJoinAndDto();
	}
}
