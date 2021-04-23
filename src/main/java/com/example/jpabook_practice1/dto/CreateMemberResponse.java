package com.example.jpabook_practice1.dto;

import lombok.Data;

@Data
public class CreateMemberResponse
{
	private Long id;

	public CreateMemberResponse(Long id)
	{
		this.id = id;
	}
}
