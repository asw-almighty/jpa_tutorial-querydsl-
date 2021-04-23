package com.example.jpabook_practice1.repository;

import com.example.jpabook_practice1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom
{
}
