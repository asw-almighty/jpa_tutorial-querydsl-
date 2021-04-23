package com.example.jpabook_practice1.service;

import com.example.jpabook_practice1.dto.OrderSearchDto;
import com.example.jpabook_practice1.entity.*;
import com.example.jpabook_practice1.repository.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService
{
	private final OrderJpaRepository orderJpaRepository;
	private final ItemJpaRepository itemJpaRepository;
	private final MemberJpaRepository memberJpaRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;

	//주문 등록
	@Transactional
	public Long order(Long memberId, Long itemId, int count)
	{
		//엔티티 조회
		Member member = memberRepository.findById(memberId).get();
//		Member member = memberJpaRepository.findOne(memberId);
		Item item = itemRepository.findById(itemId).get();
//		Item item = itemJpaRepository.findOne(itemId);

		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		delivery.setStatus(DeliveryStatus.READY);

		//주문상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		//주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);

		//주문 저장
		orderRepository.save(order);
//		orderJpaRepository.save(order);
		return order.getId();
	}

	//주문 취소
	@Transactional
	public void cancelOrder(Long orderId)
	{
		//주문 엔티티 조회
		Order order = orderRepository.findById(orderId).get();
//		Order order = orderJpaRepository.findOrder(orderId);

		//주문 취소
		order.cancel();
	}
	//주문 조회
	public List<Order> findOrders(OrderSearchDto orderSearchDto)
	{
		return orderRepository.findAll(orderSearchDto);
//		return orderJpaRepository.findAllByJpql(orderSearchDto);
	}

}
