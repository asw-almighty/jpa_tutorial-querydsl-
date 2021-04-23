package com.example.jpabook_practice1.service;

import com.example.jpabook_practice1.entity.Address;
import com.example.jpabook_practice1.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest
{
	@Autowired
	MemberService memberService;

	@Autowired
	EntityManager em;

	@Test
	public void 회원가입_테스트() throws Exception
	{
	    //when
		Member member3 = new Member("member3",new Address("서울", "경기", "1231-112"));
		Long memberId = memberService.join(member3);

		//then
		assertThat(member3).isEqualTo(memberService.findMember(memberId));
	}

	@Test
	public void 회원가입_중복_테스트() throws Exception
	{
	    //given
		Member member1 = new Member("member1",new Address("서울", "경기", "1231-112"));
		em.persist(member1);

	    //when
		Member member2 = new Member("member1",new Address("서울", "경기", "1231-112"));
		memberService.join(member2);

		//then
		Assertions.fail("중복회원 체크 수정 요청");
	}

}