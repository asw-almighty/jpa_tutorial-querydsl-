package com.example.jpabook_practice1.dto;

import com.example.jpabook_practice1.entity.Address;
import com.example.jpabook_practice1.entity.Order;
import com.example.jpabook_practice1.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryDto
{
	private Long orderId;
	private String name;
	private LocalDateTime orderDate; //주문시간
	private OrderStatus orderStatus;
	private Address address;

	public SimpleOrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address)
	{
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
	}
}
