package com.example.jpabook_practice1.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member
{
	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String name;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	public Member(String name, Address address)
	{
		this.name = name;
		this.address = address;
	}
}
