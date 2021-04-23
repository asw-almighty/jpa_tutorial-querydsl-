package com.example.jpabook_practice1.repository;

import com.example.jpabook_practice1.dto.OrderSearchDto;
import com.example.jpabook_practice1.entity.Order;
import com.example.jpabook_practice1.entity.OrderStatus;
import com.example.jpabook_practice1.entity.QMember;
import com.example.jpabook_practice1.entity.QOrder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.example.jpabook_practice1.entity.QMember.*;
import static com.example.jpabook_practice1.entity.QOrder.*;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom
{
	private final JPAQueryFactory queryFactory;

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
		return StringUtils.hasText(memberName) ? member.name.eq(memberName) : null;
	}

	private BooleanExpression orderStatusEq(OrderStatus orderStatus)
	{
		return !Objects.isNull(orderStatus) ?order.status.eq(orderStatus) : null;
	}
}
