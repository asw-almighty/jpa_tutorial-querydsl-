package com.example.jpabook_practice1.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberFormDto
{

	@NotEmpty(message = "회원 이름은 필수")
	private String name;
	private String city;
	private String street;
	private String zipcode;
}
