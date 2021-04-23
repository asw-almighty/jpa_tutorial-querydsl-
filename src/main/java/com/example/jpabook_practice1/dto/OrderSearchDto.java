package com.example.jpabook_practice1.dto;

import com.example.jpabook_practice1.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderSearchDto
{
	private String memberName;  //주문자 이름
	private OrderStatus orderStatus; //주문상태
}
