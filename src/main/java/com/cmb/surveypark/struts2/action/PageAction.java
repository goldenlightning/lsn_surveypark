package com.cmb.surveypark.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cmb.surveypark.model.Page;
import com.cmb.surveypark.model.Survey;
import com.cmb.surveypark.service.SurveyService;
@Controller
@Scope("prototype")
public class PageAction extends BaseAction<Page> {

	private static final long serialVersionUID = 1L;

	private Integer sid;
	
	private Integer pid;
	
	@Resource
	private SurveyService surveyService;
	
	/**
	 * 增加页
	 * @return
	 */
	public String toAddPage() {
		return "addPagePage";
	}
	
	/**
	 * 保存/更新页面
	 * @return
	 */
	public String saveOrUpdatePage() {
		Survey survey = new Survey();
		survey.setId(sid);
		this.model.setSurvey(survey);
		surveyService.saveOrUpdatePage(this.model);
		return "designSurveyAction";
	}
	
	/**
	 * 删除页面
	 * @return
	 */
	public String deletePage() {
		surveyService.deletePage(pid);
		return "designSurveyAction";
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	public String editPage() {
		this.model = surveyService.getPage(pid);
		return "editPagePage";
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
	
}
