package com.example.jpabook_practice1.dto;

import com.example.jpabook_practice1.entity.Address;
import com.example.jpabook_practice1.entity.Order;
import com.example.jpabook_practice1.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderDto
{
	private Long orderId;
	private String name;
	private LocalDateTime orderDate; //주문시간
	private OrderStatus orderStatus;
	private Address address;

	public SimpleOrderDto(Order order)
	{
		orderId = order.getId();
		name = order.getMember().getName();
		orderDate = order.getOrderDate();
		orderStatus = order.getStatus();
		address = order.getDelivery().getAddress();
	}
}
