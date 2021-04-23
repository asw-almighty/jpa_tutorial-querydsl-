package com.example.jpabook_practice1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class OrderItem
{
	@Id
	@GeneratedValue
	@Column(name = "order_Item_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@JsonIgnore
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private int orderPrice;
	private int count;

	//== 연관관계 메서드 ==//
	public void setOrder(Order order)
	{
		this.order = order;
	}

	//== 생성 메서드 ==//
	public static OrderItem createOrderItem(Item item, int orderPrice, int count)
	{
		OrderItem orderItem = new OrderItem();
		orderItem.item = item;
		orderItem.orderPrice = orderPrice;
		orderItem.count = count;
		item.removeStock(count);
		return orderItem;
	}

	//==비즈니스 로직==//

	/**
	 * 주문취소
	 */
	public void cancel()
	{
		item.addStock(count);
	}

	/**
	 * 총 상품 가격
	 */
	public int getOrderItemPrice()
	{
		return orderPrice * count;
	}
}
