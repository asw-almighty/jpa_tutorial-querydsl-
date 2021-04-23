package com.example.jpabook_practice1.repository;

import com.example.jpabook_practice1.entity.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class MemberRepository
{
	@PersistenceContext
	EntityManager em;

	//회원가입
	public void save(Member member)
	{
		em.persist(member);
	}

	public Member findOne(Long id)
	{
		return em.find(Member.class, id);
	}

	public List<Member> findAll()
	{
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}

	public List<Member> findByUsername(String username)
	{
		return em.createQuery(
				"select m from Member m" +
						    " where m.name = :username", Member.class)
				.setParameter("username", username)
				.getResultList();
	}
}
