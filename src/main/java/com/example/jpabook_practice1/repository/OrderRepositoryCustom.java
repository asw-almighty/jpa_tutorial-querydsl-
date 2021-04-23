package com.example.jpabook_practice1.repository;

import com.example.jpabook_practice1.dto.OrderSearchDto;
import com.example.jpabook_practice1.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom
{
	//검색
	public List<Order> findAll(OrderSearchDto orderSearchDto);
}
