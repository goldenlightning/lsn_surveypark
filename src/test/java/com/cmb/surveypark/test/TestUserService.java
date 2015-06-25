package com.cmb.surveypark.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cmb.surveypark.model.User;
import com.cmb.surveypark.service.UserService;

public class TestUserService {

	private static UserService userService;
	
	@BeforeClass
	public static void initUserService() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		userService = (UserService) context.getBean("userService");
	}
	
	
	/**
	 * 插入用户
	 */
	@Test
	public void insertUser() {
		User u = new User();
		u.setEmail("golden@163.com");
		u.setName("golden");
		u.setNickName("lightning");
		userService.saveEntity(u);
	}

}
