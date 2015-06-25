package com.cmb.surveypark.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDataSource {

	@Test
	public void getConnection() throws SQLException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		DataSource ds = ac.getBean("dataSource", DataSource.class);
		System.out.println(ds.getConnection());
	}
	
}
