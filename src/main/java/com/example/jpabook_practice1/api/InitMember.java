package com.example.jpabook_practice1.api;

import com.example.jpabook_practice1.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 샘플 데이터 입력
 */
@Component
@RequiredArgsConstructor
public class InitMember
{
	private final InitService initService;

	@PostConstruct
	public void init()
	{
		initService.dbInit1();
		initService.dbInit2();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService{

		private final EntityManager em;

		public void dbInit1()
		{
			Member member = createMember("userA", "서울", "1", "111");
			em.persist(member);

			Book book1 = createBook("JPA BOOK1", 10000, 100);
			em.persist(book1);

			Book book2 = createBook("JPA BOOK2", 20000, 100);
			em.persist(book2);

			OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
			OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);
			Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
			em.persist(order);
		}

		public void dbInit2()
		{
			Member member = createMember("userB", "진주", "2", "2222");
			em.persist(member);

			Book book1 = createBook("SPRING1 BOOK1", 20000, 200);
			em.persist(book1);

			Book book2 = createBook("SPRING BOOK2", 40000, 100);
			em.persist(book2);

			Delivery delivery = createDelivery(member);

			OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 3);
			OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 4);

			Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

			em.persist(order);
		}

		private Member createMember(String name, String city, String street, String zipcode)
		{
			return new Member(name, new Address(city,street,zipcode));
		}

		private Book createBook(String name, int price, int stockQuantity)
		{
			Book book = new Book();
			book.setName(name);
			book.setPrice(price);
			book.setStockQuantity(stockQuantity);

			return book;
		}

		private Delivery createDelivery(Member member)
		{
			Delivery delivery = new Delivery();
			delivery.setAddress(member.getAddress());
			return delivery;
		}

	}
}
