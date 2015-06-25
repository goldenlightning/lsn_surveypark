package com.cmb.surveypark.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cmb.surveypark.model.Page;
import com.cmb.surveypark.model.Question;
import com.cmb.surveypark.service.SurveyService;

@Controller
@Scope("prototype")
public class QuestionAction extends BaseAction<Question>  {

	private static final long serialVersionUID = 1L;


	@Resource
	private SurveyService surveyService;

	private Integer sid;
	private Integer pid;
	
	private Integer qid;
	
	/**
	 * 设计调查->增加问题
	 * @return
	 */
	public String toSelectQuestionType() {
		return "selectQuestionTypePage";
	}
	
	/**
	 * 到达问题设计页面
	 * @return
	 */
	public String toDesignQuestionPage() {
		return "" + this.model.getQuestionType();
	}
	
	public String saveOrUpdateQuestion() {
		Page page = new Page();
		page.setId(pid);
		this.model.setPage(page);
		surveyService.saveOrUpdateQuestion(this.model);
		return "designSurveyAction";
	}
	
	public String deleteQuestion() {
		surveyService.deleteQuestion(qid);
		return "designSurveyAction";
	}
	
	/**
	 * 编辑问题
	 * @return
	 */
	public String editQuestion() {
		this.model = surveyService.getQuestion(qid);
		return "" + this.model.getQuestionType();
	}


	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

}
