package com.tang.test;


import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tang.bean.Person;
import com.tang.service.PersonService;

public class SpringJDBCTest {
	
	public static void main(String[] args) {
		ApplicationContext act = new ClassPathXmlApplicationContext("springmvc-servlet.xml");
		PersonService personService = (PersonService) act.getBean("personService");
		 Person person = new Person();
		         person.setName("苏东坡");
		          person.setAge(21);
		          person.setSex("男");
		  
		          // 保存一条记录
		          personService.save(person);
		          List<Person> person1 = personService.getPerson();
		                   System.out.println("++++++++得到所有Person");
	}

}
