package com.cmb.surveypark.service;

import java.util.List;

import com.cmb.surveypark.model.Answer;
import com.cmb.surveypark.model.Page;
import com.cmb.surveypark.model.Question;
import com.cmb.surveypark.model.Survey;
import com.cmb.surveypark.model.User;

public interface SurveyService {

	List<Survey> findMySurveys(User user);

	Survey newSurvey(User user);

	Survey getSurvey(Integer sid);

	Survey getSurveyWithChildren(Integer sid);

	void updateSurvey(Survey model);

	void saveOrUpdatePage(Page model);

	void deletePage(Integer pid);

	Page getPage(Integer pid);

	void saveOrUpdateQuestion(Question model);

	void deleteQuestion(Integer pid);

	/**
	 * 删除调查，同时删除页面
	 * @param sid
	 */
	void deleteSurvey(Integer sid);

	Question getQuestion(Integer qid);

	void clearAnswers(Integer sid);

	void toggleStatus(Integer sid);

	void updateLogoPhotoPath(Integer sid, String string);

	List<Survey> getSurveyWithPages(User user);

	void moveOrCopyPage(Integer srcPid, Integer targetPid, int pos);

	List<Survey> finAllAvailableSurveys();

	Page getFirstPage(Integer sid);

	Page getPrePage(Integer currPid);

	Page getNextPage(Integer currPid);

	void saveAnswers(List<Answer> processAnswers);
	
}
