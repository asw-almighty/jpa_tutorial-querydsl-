package com.example.jpabook_practice1.service;

import com.example.jpabook_practice1.entity.Member;
import com.example.jpabook_practice1.repository.MemberRepository;
import jdk.jshell.spi.ExecutionControlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService
{
	private final MemberRepository memberRepository;

	/**
	 * 회원 등록(+중복회원 검증)
	 * @param member
	 * @return
	 */
	public Long join(Member member)
	{
		validateDuplicateMember(member); //중복 회원 검증

		memberRepository.save(member);
		return member.getId();
	}

	/**
	 * 중복 회원 검증
	 * @param member
	 */
	private void validateDuplicateMember(Member member)
	{
		List<Member> findMember = memberRepository.findByUsername(member.getName());

		if (!findMember.isEmpty())
		{
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	/**
	 * 전체 회원 조회
	 * @return
	 */
	public List<Member> findMembers()
	{
		return memberRepository.findAll();
	}

	/**
	 * id로 특정 회원 조회
	 * @param id
	 * @return
	 */
	public Member findMember(Long id)
	{
		return memberRepository.findOne(id);
	}
}
