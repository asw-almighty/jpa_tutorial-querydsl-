package com.example.jpabook_practice1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberResponse
{
	private Long id;
	private String name;
}
