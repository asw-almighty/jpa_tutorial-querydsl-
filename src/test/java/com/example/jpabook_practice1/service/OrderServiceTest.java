package com.example.jpabook_practice1.service;

import com.example.jpabook_practice1.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest
{
	@Autowired
	EntityManager em;

	@Autowired
	OrderService orderService;

	/**
	 * 상품 주문이 성공해야 한다.
	 * 상품을 주문할 때, 수량을 초과하면 안된다.
	 * 주문 취소가 성공해야 한다.
	 */
	
	@Test
	public void 상품주문() throws Exception
	{
	    //given
		Member member = new Member("member1",new Address("서울", "경기", "1231-112"));
		em.persist(member);

		Item item = new Book();
		item.setName("book1");
		item.setStockQuantity(10);
		item.setPrice(10000);
		em.persist(item);

	    //when
		Long orderId = orderService.order(member.getId(), item.getId(), 5);
		Order order = em.find(Order.class, orderId);

		//then
		assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
		assertThat(order.getOrderItems().size()).isEqualTo(1);
		assertThat(order.getMember()).isEqualTo(member);
		assertThat(item.getStockQuantity()).isEqualTo(5);
	}
	@Test
	public void 상품주문시_수량초과여부() throws Exception
	{
	    //given
		Member member = new Member("member1",new Address("서울", "경기", "1231-112"));
		em.persist(member);

		Item item = new Book();
		item.setName("book1");
		item.setStockQuantity(10);
		item.setPrice(10000);
		em.persist(item);

	    //when
		orderService.order(member.getId(), item.getId(), 11);
	    //then
		fail("수량을 초과하는 주문건을 확인하여야 한다");
	}
	@Test
	public void 주문취소() throws Exception
	{
		//given
		Member member = new Member("member1",new Address("서울", "경기", "1231-112"));
		em.persist(member);

		Item item = new Book();
		item.setName("book1");
		item.setStockQuantity(10);
		item.setPrice(10000);
		em.persist(item);

		Long orderId = orderService.order(member.getId(), item.getId(), 9);

		//when
		orderService.cancelOrder(orderId);

	    //then
		assertThat(item.getStockQuantity()).isEqualTo(10);
		assertThat(em.find(Order.class, orderId).getStatus()).isEqualTo(OrderStatus.CANCEL);
	}
}
