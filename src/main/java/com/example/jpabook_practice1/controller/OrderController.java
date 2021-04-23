package com.example.jpabook_practice1.controller;

import com.example.jpabook_practice1.dto.OrderSearchDto;
import com.example.jpabook_practice1.entity.Item;
import com.example.jpabook_practice1.entity.Member;
import com.example.jpabook_practice1.entity.Order;
import com.example.jpabook_practice1.service.ItemService;
import com.example.jpabook_practice1.service.MemberService;
import com.example.jpabook_practice1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController
{
	private final OrderService orderService;
	private final MemberService memberService;
	private final ItemService itemService;

	@GetMapping("/order")
	public String createForm(Model model)
	{
		List<Member> members = memberService.findMembers();
		List<Item> items = itemService.findItems();

		model.addAttribute("members", members);
		model.addAttribute("items", items);

		return "orders/orderForm";
	}

	@PostMapping("/order")
	public String order(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") int count)
	{
		orderService.order(memberId, itemId, count);
		return "redirect:/orders";
	}

	@GetMapping("/orders")
	public String orderList(@ModelAttribute("orderSearch") OrderSearchDto orderSearchDto, Model model)
	{
		List<Order> orders = orderService.findOrders(orderSearchDto);
		model.addAttribute("orders", orders);

		return "orders/orderList";
	}

	@PostMapping("/orders/{orderId}/cancel")
	public String cancelOrder(@PathVariable("orderId") Long orderId)
	{
		orderService.cancelOrder(orderId);
		return "redirect:/orders";
	}
}
