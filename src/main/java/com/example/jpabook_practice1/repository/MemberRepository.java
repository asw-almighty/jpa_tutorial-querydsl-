package com.example.jpabook_practice1.repository;

import com.example.jpabook_practice1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>
{
	List<Member> findByName(String name);
}
