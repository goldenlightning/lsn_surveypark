package com.cmb.surveypark.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cmb.surveypark.model.Page;
import com.cmb.surveypark.model.Survey;
import com.cmb.surveypark.model.User;
import com.cmb.surveypark.service.SurveyService;
import com.cmb.surveypark.struts2.UserAware;

/**
 * 移动/复制页面Action
 * @author ThinkPad
 *
 */
@Controller
@Scope("prototype")
public class MoveOrCopyPageAction extends BaseAction<Page> implements UserAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer srcPid;
	private Integer targetPid;
	private int pos;
	private Integer sid;
	
	@Resource
	private SurveyService surveyService;

	private User user;
	private List<Survey> mySurveys;
	
	public String toSelectTargetPage() {
		this.mySurveys = surveyService.getSurveyWithPages(user);
		return "moveOrCopyPageListPage";
	}
	
	public String doMoveOrCopyPage() {
		surveyService.moveOrCopyPage(srcPid, targetPid, pos);
		return "designSurveyAction";
	}
	
	public Integer getSrcPid() {
		return srcPid;
	}

	public void setSrcPid(Integer srcPid) {
		this.srcPid = srcPid;
	}





	@Override
	public void setUser(User user) {
		this.user = user;
	}





	public List<Survey> getMySurveys() {
		return mySurveys;
	}





	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	public Integer getTargetPid() {
		return targetPid;
	}

	public void setTargetPid(Integer targetPid) {
		this.targetPid = targetPid;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
}
