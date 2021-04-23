package com.example.jpabook_practice1.repository;

import com.example.jpabook_practice1.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository
{
	private final EntityManager em;

	//상품등록
	public void save(Item item)
	{
		if (item.getId() == null)
		{
			em.persist(item);
		} else
		{
			em.merge(item);
		}

	}

	//상품 개별 조회
	public Item findOne(Long id)
	{
		return em.find(Item.class, id);
	}

	//상품 전체 조회
	public List<Item> findAll()
	{
		return em.createQuery(
				"select i from Item i"
		).getResultList();
	}
}
