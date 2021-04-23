package com.example.jpabook_practice1.service;

import com.example.jpabook_practice1.entity.Item;
import com.example.jpabook_practice1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService
{
	private final ItemRepository itemRepository;
	private final EntityManager em;

	//상품등록,수정
	@Transactional
	public void saveItem(Item item)
	{
		itemRepository.save(item);
	}

	@Transactional
	public void updateItem(Long itemId, String name, int price)
	{
		Item findItem = em.find(Item.class, itemId);

		findItem.setPrice(price);
		findItem.setName(name);
	}

	//상품 전체 조회
	public List<Item> findItems(){
		return itemRepository.findAll();
	}

	//상품 단건 조회
	public Item findItem(Long itemId)
	{
		return itemRepository.findOne(itemId);
	}

}
