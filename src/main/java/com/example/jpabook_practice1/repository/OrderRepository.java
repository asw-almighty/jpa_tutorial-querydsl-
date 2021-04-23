package com.example.jpabook_practice1.repository;

import com.example.jpabook_practice1.dto.OrderSearchDto;
import com.example.jpabook_practice1.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository
{
	private final EntityManager em;

	public void save(Order order)
	{
		em.persist(order);
	}

	public Order findOrder(Long orderId)
	{
		return em.find(Order.class, orderId);
	}

	//검색
//	public List<Order> findAll(OrderSearchDto orderSearchDto)
//	{
//
//	}
	//검색
	public List<Order> findAllByJpql(OrderSearchDto orderSearchDto)
	{
		String jpql = "select o from Order o join o.member m";
		boolean isFirstCondition = true;

		//주문 상태 검색
		if (orderSearchDto.getOrderStatus() != null)
		{
			if (isFirstCondition)
			{
				jpql += " where";
				isFirstCondition = false;
			} else
			{
				jpql += " and";
			}
			jpql += " o.status = :status";
		}

		//회원 이름 검색
		if (orderSearchDto.getMemberName() != null)
		{
			if (isFirstCondition)
			{
				jpql += " where";
				isFirstCondition = false;
			} else
			{
				jpql += " and";
			}
			jpql += " m.name like :name";
		}

		TypedQuery<Order> query = em.createQuery(jpql, Order.class)
				.setMaxResults(1000);

		if(orderSearchDto.getOrderStatus() != null)
		{
			query = query.setParameter("status", orderSearchDto.getOrderStatus());
		}
		if(StringUtils.hasText(orderSearchDto.getMemberName()))
		{
			query = query.setParameter("name", orderSearchDto.getMemberName());
		}

		return query.getResultList();
	}

	//검색
	public List<Order> findAllByCriteria(OrderSearchDto orderSearchDto)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Order> o = cq.from(Order.class);
		Join<Object, Object> m = o.join("member", JoinType.INNER);

		List<Predicate> criteria = new ArrayList<>();

		//주문 상태 검색
		if (orderSearchDto.getOrderStatus() != null)
		{
			Predicate status = cb.equal(o.get("status"), orderSearchDto.getOrderStatus());
			criteria.add(status);
		}

		if(StringUtils.hasText(orderSearchDto.getMemberName()))
		{
			Predicate name = cb.like(m.get("name"), "%" + orderSearchDto.getMemberName() + "%");
			criteria.add(name);
		}

		cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
		return query.getResultList();
	}
}
