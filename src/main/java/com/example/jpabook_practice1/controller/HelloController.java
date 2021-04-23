package com.example.jpabook_practice1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HelloController
{
	@GetMapping("/")
	public String hello(Model model)
	{
		return "home";
	}
}
