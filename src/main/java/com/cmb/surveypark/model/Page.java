package com.cmb.surveypark.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 页面类
 * 
 * @author ThinkPad
 *
 */
public class Page implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2001040467944764970L;
	private Integer id;
	private String title = "未命名";
	private String description;

	// 页序
	private float orderno;


	// 建立从Page到Survey之间多对一关联关系
	private transient Survey survey;

	// 建立从Page到Question之间一对多关联关系
	private Set<Question> questions = new HashSet<Question>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		if(id != null) {
			this.orderno = id;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public float getOrderno() {
		return orderno;
	}

	public void setOrderno(float orderno) {
		this.orderno = orderno;
	}

}
