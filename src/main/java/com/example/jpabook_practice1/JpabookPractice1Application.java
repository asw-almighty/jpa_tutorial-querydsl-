package com.example.jpabook_practice1;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class JpabookPractice1Application
{

	public static void main(String[] args)
	{
		SpringApplication.run(JpabookPractice1Application.class, args);
	}

	//querydsl 등록
	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em)
	{
		return new JPAQueryFactory(em);
	}


	/**
	 * 초기화 된 프록시 객체만 노출!
	 * 이게 없으면 프록시 객체를 Jackson 객체가 어떻게 JSON으로 쓸지 모른다.
	 *
	 * @return
	 */
	@Bean
	Hibernate5Module hibernate5Module()
	{
		return new Hibernate5Module();
	}
}
