package com.cmb.surveypark.model.statistics;

import java.util.ArrayList;
import java.util.List;

import com.cmb.surveypark.model.Question;
/**
 * 问题统计模型
 * @author ThinkPad
 *
 */
public class QuestionStatisticsModel {

	private Question question;
	private int count;
	
	private List<OptionStatisticsModel> options = new ArrayList<OptionStatisticsModel>();

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<OptionStatisticsModel> getOptions() {
		return options;
	}

	public void setOptions(List<OptionStatisticsModel> options) {
		this.options = options;
	}
	
}
