package com.example.jpabook_practice1.repository;

import com.example.jpabook_practice1.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>
{
}
