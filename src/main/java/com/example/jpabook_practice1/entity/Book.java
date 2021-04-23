package com.example.jpabook_practice1.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("B")
@Entity
@Getter
@Setter
public class Book extends Item
{
	private String author;
	private String isbn;
}
