package com.example.jpabook_practice1.repository;

import com.example.jpabook_practice1.dto.OrderSearchDto;
import com.example.jpabook_practice1.entity.Order;
import com.example.jpabook_practice1.entity.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.jpabook_practice1.entity.QMember.member;
import static com.example.jpabook_practice1.entity.QOrder.order;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom
{
	private final JPAQueryFactory queryFactory;
	private final EntityManager em;

	//검색
	//select * from order o inner join member m where m.name=#{membername} and o.status=${status}
	public List<Order> findAll(OrderSearchDto orderSearchDto)
	{
		return queryFactory
				.selectFrom(order)
				.join(order.member, member)
				.where(memberNameEq(orderSearchDto.getMemberName()),
						orderStatusEq(orderSearchDto.getOrderStatus()))
				.fetch();
	}

	private BooleanExpression memberNameEq(String memberName)
	{
		return hasText(memberName) ? member.name.eq(memberName) : null;
	}

	private BooleanExpression orderStatusEq(OrderStatus orderStatus)
	{
		return !isNull(orderStatus) ? order.status.eq(orderStatus) : null;
	}

	public List<Order> findAllWithFetchJoin()
	{
		return em.createQuery(
				"select o from Order o" +
						" inner join fetch o.member m" +
						" inner join fetch o.delivery d", Order.class
		).getResultList();
	}

	public List<Order> findAllWithFetchJoinV3()
	{
		return em.createQuery(
				"select distinct o from Order o" +
						" inner join fetch o.member m" +
						" inner join fetch o.delivery d" +
						" inner join fetch o.orderItems oi" +
						" inner join fetch oi.item i", Order.class
		).getResultList();
	}

	public List<Order> findAllWithFetchJoin_page(int offset, int limit)
	{
		return em.createQuery(
				"select o from Order o" +
						" inner join fetch o.member m" +
						" inner join fetch o.delivery d", Order.class)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}
}
