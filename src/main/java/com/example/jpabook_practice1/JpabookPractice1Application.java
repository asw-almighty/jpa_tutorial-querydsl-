package com.example.jpabook_practice1;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;

@SpringBootApplication
public class JpabookPractice1Application
{

	public static void main(String[] args)
	{
		SpringApplication.run(JpabookPractice1Application.class, args);
	}

	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em)
	{
		return new JPAQueryFactory(em);
	}
}
