package com.cmb.surveypark.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmb.surveypark.dao.BaseDao;
import com.cmb.surveypark.model.Answer;
import com.cmb.surveypark.model.Page;
import com.cmb.surveypark.model.Question;
import com.cmb.surveypark.model.Survey;
import com.cmb.surveypark.model.User;
import com.cmb.surveypark.service.SurveyService;
import com.cmb.surveypark.util.DataUtil;
import com.google.common.base.Objects;
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

	@Resource(name="surveyDao")
	private BaseDao<Survey> surveyDao;
	
	@Resource(name="pageDao")
	private BaseDao<Page> pageDao;
	
	@Resource(name="questionDao")
	private BaseDao<Question> questionDao;
	
	@Resource(name="answerDao")
	private BaseDao<Answer> answerDao;
	
	@Override
	public List<Survey> findMySurveys(User user) {
		String hql = "from Survey s where s.user.id = ?";
		return surveyDao.findEntityByHQL(hql, user.getId());
	}

	@Override
	public Survey newSurvey(User user) {
		Survey survey = new Survey();
		survey.setUser(user);
		Page page = new Page();
		survey.getPages().add(page);
		surveyDao.saveEntity(survey);
		pageDao.saveEntity(page);
		return survey;
	}

	@Override
	public Survey getSurvey(Integer sid) {
		return surveyDao.getEntity(sid);
	}

	@Override
	public Survey getSurveyWithChildren(Integer sid) {
		Survey survey = this.getSurvey(sid);
		for(Page page : survey.getPages()) {
			page.getQuestions().size();
		}
		return survey;
	}

	@Override
	public void updateSurvey(Survey model) {
		surveyDao.updateEntity(model);
	}

	@Override
	public void saveOrUpdatePage(Page model) {
		pageDao.saveOrUpdateEntity(model);
	}

	@Override
	public void deletePage(Integer pid) {
		String hql = "delete from Answer a where a.questionId in (select id from Question q where q.page.id = ?)";
		answerDao.batchEntityByHQL(hql, pid);
		
		hql = "delete from Question q where q.page.id = ?";
		questionDao.batchEntityByHQL(hql, pid);
		
		hql = "delete from Page p where p.id = ?";
		pageDao.batchEntityByHQL(hql, pid);
		
	}

	@Override
	public Page getPage(Integer pid) {
		return pageDao.getEntity(pid);
	}

	@Override
	public void saveOrUpdateQuestion(Question model) {
		questionDao.saveOrUpdateEntity(model);
	}

	@Override
	public void deleteQuestion(Integer pid) {
		String hql = "delete from Answer a where a.questionId = ?";
		answerDao.batchEntityByHQL(hql, pid);
		
		hql = "delete from Question q where q.id = ?";
		questionDao.batchEntityByHQL(hql, pid);
	}

	
	@Override
	public void deleteSurvey(Integer sid) {
		String hql = "delete from Answer a where a.surveyId = ?";
		answerDao.batchEntityByHQL(hql, sid);
		
		hql = "delete from Question q where q.page.id in (select id from Page p where p.survey.id = ?)";
		questionDao.batchEntityByHQL(hql, sid);
		
		hql = "delete from Page p where p.survey.id = ?";
		pageDao.batchEntityByHQL(hql, sid);
		
		
		hql = "delete from Survey s where s.id = ?";
		surveyDao.batchEntityByHQL(hql, sid);
		
	}

	@Override
	public Question getQuestion(Integer qid) {
		return questionDao.getEntity(qid);
	}

	@Override
	public void clearAnswers(Integer sid) {
		String hql = "delete from Answer a where a.surveyId = ?";
		answerDao.batchEntityByHQL(hql, sid);
	}

	@Override
	public void toggleStatus(Integer sid) {
		Survey survey = this.getSurvey(sid);
		String hql = "update Survey set closed = ? where id = ?";
		surveyDao.batchEntityByHQL(hql, !survey.isClosed(), survey.getId());
	}

	@Override
	public void updateLogoPhotoPath(Integer sid, String path) {
		String hql = "update Survey s set s.logoPhotoPath = ? where s.id = ?";
		surveyDao.batchEntityByHQL(hql, path, sid);
	}

	@Override
	public List<Survey> getSurveyWithPages(User user) {
		List<Survey> list = findMySurveys(user);
		for(Survey survey : list) {
			survey.getPages().size();
		}
		return list;
	}

	@Override
	public void moveOrCopyPage(Integer srcPid, Integer targetPid, int pos) {
		Page srcPage = getPage(srcPid);
		Survey srcSurvey = srcPage.getSurvey();
		Page targetPage = getPage(targetPid);
		Survey targSurvey = targetPage.getSurvey();
		
		//移动
		if(Objects.equal(srcSurvey.getId(), targSurvey.getId())) {
			setOrderno(srcPage, targetPage, pos);
		} else {	//复制
			srcPage.getQuestions().size();
			Page copyPage = (Page) DataUtil.deeplyCopy(srcPage);
			copyPage.setSurvey(targSurvey);
			//保存页面 
			pageDao.saveEntity(copyPage);
			for(Question q : copyPage.getQuestions()) {
				questionDao.saveEntity(q);
			}
			setOrderno(copyPage, targetPage, pos);
		}
	}

	private void setOrderno(Page srcPage, Page targetPage, int pos) {
		//判断位置：0-之前，1-之后
		if(pos == 0) { //之前
			if(isFirstPage(targetPage)) {
				srcPage.setOrderno(targetPage.getOrderno() - 0.01f);
			} else {
				Page prePage = getPrePage(targetPage);
				srcPage.setOrderno((targetPage.getOrderno() + prePage.getOrderno()) / 2);
			}
		} else {	//之后
			if(isLastPage(targetPage)) {
				srcPage.setOrderno(targetPage.getOrderno() + 0.01f);
			} else {
				Page nextPage = getNextPage(targetPage);
				srcPage.setOrderno((targetPage.getOrderno() + nextPage.getOrderno()) / 2);
			}
		}
		
	}

	private Page getNextPage(Page page) {
		String hql = "from Page p where p.survey.id = ? and p.orderno > ? order by p.orderno asc";
		List<Page> list = pageDao.findEntityByHQL(hql, page.getSurvey().getId(), page.getOrderno());
		return list.get(0);
	}

	private boolean isLastPage(Page page) {
		String hql = "select count(*) from Page p where p.survey.id = ? and p.orderno > ?";
		Long count = (Long) pageDao.uniqueResult(hql, page.getSurvey().getId(), page.getOrderno());
		return count == 0;
	}

	private Page getPrePage(Page page) {
		String hql = "from Page p where p.survey.id = ? and p.orderno < ? order by p.orderno desc";
		List<Page> list = pageDao.findEntityByHQL(hql, page.getSurvey().getId(), page.getOrderno());
		return list.get(0);
	}

	/**
	 * 判断指定页面是否是所在调查首页
	 * @param page
	 * @return
	 */
	private boolean isFirstPage(Page page) {
		String hql = "select count(*) from Page p where p.survey.id = ? and p.orderno < ?";
		Long count = (Long) pageDao.uniqueResult(hql, page.getSurvey().getId(), page.getOrderno());
		return count == 0;
	}

	@Override
	public List<Survey> finAllAvailableSurveys() {
		String hql = "from Survey s where s.closed = ?";
		return surveyDao.findEntityByHQL(hql, false);
	}

	@Override
	public Page getFirstPage(Integer sid) {
		String hql = "from Page p where p.survey.id = ? order by orderno asc";
		List<Page> pages = pageDao.findEntityByHQL(hql, sid);
		Page p = pages.get(0);
		p.getQuestions().size();
		p.getSurvey().getTitle();
		return p;
	}

	@Override
	public Page getPrePage(Integer currPid) {
		Page page = this.getPage(currPid);
		page = this.getPrePage(page);
		page.getQuestions().size();
		return page;
	}

	@Override
	public Page getNextPage(Integer currPid) {
		Page page = this.getPage(currPid);
		page = this.getNextPage(page);
		page.getQuestions().size();
		return page;
	}

	@Override
	public void saveAnswers(List<Answer> answers) {
		Date date = new Date();
		String uuid = UUID.randomUUID().toString();
		for(Answer answer : answers) {
			answer.setUuid(uuid);
			answer.setAnswerTime(date);
			answerDao.saveEntity(answer);
		}
	}

	
	

}
