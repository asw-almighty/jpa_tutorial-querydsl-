package com.example.jpabook_practice1.controller;

import com.example.jpabook_practice1.dto.MemberFormDto;
import com.example.jpabook_practice1.entity.Address;
import com.example.jpabook_practice1.entity.Member;
import com.example.jpabook_practice1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController
{
	private final MemberService memberService;

	@GetMapping("/members/new")
	public String createForm(Model model)
	{
		model.addAttribute("memberForm", new MemberFormDto());
		return "members/createMemberForm";
	}

	@PostMapping("/members/new")
	public String create(@Valid MemberFormDto form, BindingResult result)
	{
		if(result.hasErrors())
		{
			return "members/createMemberForm";
		}

		Member member = new Member(form.getName(), new Address(form.getCity(), form.getStreet(), form.getZipcode()));
		memberService.join(member);

		return "redirect:/";
	}

	@GetMapping("/members")
	public String list(Model model)
	{
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}
}
