package com.cmb.surveypark.service;

import com.cmb.surveypark.model.statistics.QuestionStatisticsModel;

public interface StatisticsService {

	public QuestionStatisticsModel statistics(Integer qid);
	
}

