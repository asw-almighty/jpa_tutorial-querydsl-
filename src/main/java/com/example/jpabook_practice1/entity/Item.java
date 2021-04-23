package com.example.jpabook_practice1.entity;

import com.example.jpabook_practice1.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item
{
	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;

	private String name;
	private int price;
	private int stockQuantity;

	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();

	//==비즈니스로직==//
	public void addStock(int quantity)
	{
		this.stockQuantity += quantity;
	}

	public void removeStock(int quantity)
	{
		if(this.stockQuantity < quantity)
		{
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity -= quantity;
	}
}
