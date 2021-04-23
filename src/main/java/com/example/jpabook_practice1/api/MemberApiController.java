package com.example.jpabook_practice1.api;

import com.example.jpabook_practice1.dto.*;
import com.example.jpabook_practice1.entity.Address;
import com.example.jpabook_practice1.entity.Member;
import com.example.jpabook_practice1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController
{
	private final MemberService memberService;

	/**
	 * v1
	 * 문제: 엔티티를 주고받는다(엔티티가 변경되면 API 스펙이 변함)
	 * 엔티티에 API 검증을 위한 로직이 들어간다(@NotEmpty)
	 *
	 * @param member
	 * @return
	 */
	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member)
	{
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	/**
	 * v2
	 * 해결: 엔티티와 API 스펙을 분리.
	 * 엔티티와 프리젠테이션 계층을 위한 로직을 분리!(notempty)
	 * @param form
	 * @return
	 */
	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid MemberFormDto form)
	{
		Member member = new Member(form.getName(), new Address(form.getCity(), form.getStreet(), form.getZipcode()));

		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	/**
	 * 수정
	 * PUT은 전체를 업데이트할때!
	 * PATCH나 POST를 사용하자
	 * @param id
	 * @param request
	 * @return
	 */
	@PutMapping("/api/v2/members/{id}")
	public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
	                                           @RequestBody @Valid UpdateMemberRequest request)
	{
		memberService.update(id, request.getName());
		Member member = memberService.findMember(id);
		return new UpdateMemberResponse(member.getId(), member.getName());
	}

	/**
	 * 회원조회
	 * 문제점: 엔티티를 직접 외부에 노출.
	 *        엔티티에 프레젠테이션 계층을 위한 로직이 추가됨
	 *        엔티티가 변경되면 API 스펙도 변경!
	 * @return
	 */
	@GetMapping("/api/v1/members")
	public List<Member> membersV1()
	{
		return memberService.findMembers();
	}

	/**
	 * 조회한 Member를 Stream을 만들어서 MemberDto로 변환해서 반환한다.
	 *
	 * Result(T) 클래스를 사용!
	 * 나중에 요소를 추가할 수 있다.
	 * {
	 *      "data" : [],
	 *      "추가된 key": "추가된 value"
	 * }
	 *
	 * 엔티티가 변해도 API 스펙이 변하지 않는다!
	 * @return
	 */
	@GetMapping("/api/v2/members")
	public Result membersV2()
	{
		List<Member> findMembers = memberService.findMembers();
		List<MemberDto> collect = findMembers.stream()
				.map(member -> new MemberDto(member.getName()))
				.collect(Collectors.toList());

		return new Result(collect);
	}

}
