package com.example.jpabook_practice1.repository.query;

import com.example.jpabook_practice1.dto.SimpleOrderQueryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository
{
	private final EntityManager em;

	public List<SimpleOrderQueryDto> findAllWithFetchJoinAndDto()
	{
		return em.createQuery(
				"select" +
						" new com.example.jpabook_practice1.dto.SimpleOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
						" from Order o" +
						" inner join o.member m" +
						" inner join o.delivery d", SimpleOrderQueryDto.class
		).getResultList();
	}
}
