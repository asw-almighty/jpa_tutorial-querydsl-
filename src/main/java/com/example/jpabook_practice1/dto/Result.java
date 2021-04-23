package com.example.jpabook_practice1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T>
{
	private T data;
}
