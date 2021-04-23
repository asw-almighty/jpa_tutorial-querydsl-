package com.example.jpabook_practice1.dto;

import com.example.jpabook_practice1.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto
{
	private String itemName;
	private int orderPrice;
	private int count;

	public OrderItemDto(OrderItem orderItem)
	{
		itemName = orderItem.getItem().getName();
		orderPrice = orderItem.getOrderPrice();
		count = orderItem.getCount();
	}
}
