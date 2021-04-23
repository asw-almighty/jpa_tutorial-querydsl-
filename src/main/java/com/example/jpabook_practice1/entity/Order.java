package com.example.jpabook_practice1.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter
public class Order
{
	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "order", cascade = ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@OneToOne(cascade = ALL, fetch = LAZY)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	//==연관관계 메서드(생성메서드 만들기 위해)==//
	private void setMember(Member member)
	{
		this.member = member;
		member.getOrders().add(this);
	}

	private void setDelivery(Delivery delivery)
	{
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	private void addOrderItem(OrderItem orderItem)
	{
		this.orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	//==생성 메서드==//
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems)
	{
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for (OrderItem orderItem : orderItems)
		{
			order.addOrderItem(orderItem);
		}
		order.status = OrderStatus.ORDER;
		order.orderDate = LocalDateTime.now();
		return order;
	}

	//==비즈니스 로직==//
	/**
	 * 주문 취소
	 */
	public void cancel()
	{
		if(delivery.getStatus() == DeliveryStatus.COMP)
		{
			throw new IllegalStateException("주문취소된 상품은 취소가 불가능합니다.");
		}

		this.status = OrderStatus.CANCEL;
		for (OrderItem orderItem : orderItems)
		{
			orderItem.cancel();
		}
	}
	//==조회 로직==//
	/**
	 * 총 주문 가격 조회
	 */
	public int getTotalPrice(){
		int totalPrice = 0;
		for (OrderItem orderItem : orderItems)
		{
			totalPrice += orderItem.getOrderItemPrice();
		}
		return totalPrice;
	}
}
