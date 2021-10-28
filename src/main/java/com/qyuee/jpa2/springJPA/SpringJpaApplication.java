package com.qyuee.jpa2.springJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaApplication.class, args);
		Hello hello = new Hello();
		hello.setName("name");
		System.out.println("hello = " + hello.getName());
	}

}
