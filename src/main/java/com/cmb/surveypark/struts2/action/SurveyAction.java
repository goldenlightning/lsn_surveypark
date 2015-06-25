package com.cmb.surveypark.struts2.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cmb.surveypark.model.Survey;
import com.cmb.surveypark.model.User;
import com.cmb.surveypark.service.SurveyService;
import com.cmb.surveypark.struts2.UserAware;

@Controller
@Scope("prototype")
public class SurveyAction extends BaseAction<Survey> implements UserAware, ServletContextAware {

	private static final long serialVersionUID = 1L;

	private List<Survey> mySurveys;

	@Resource
	private SurveyService surveyService;

	private User user;
	private Integer sid;
	
	private File logoPhoto;
	private String logoPhotoFileName;
	
	private String inputPage;

	private ServletContext sc;
	
	/**
	 * 我的调查
	 * 
	 * @return
	 */
	public String mySurveys() {
		this.mySurveys = surveyService.findMySurveys(user);
		return "mySurveyListPage";
	}

	/**
	 * 新建调查
	 * 
	 * @return
	 */
	public String newSurvey() {
		this.model = surveyService.newSurvey(user);
		return "designSurveyPage";
	}
	
	/**
	 * 设计调查
	 * @return
	 */
	public String designSurvey() {
		this.model = surveyService.getSurveyWithChildren(sid);
		return "designSurveyPage";
	}
	
	/**
	 * 编辑调查
	 * @return
	 */
	public String editSurvey() {
		this.model = surveyService.getSurvey(sid);
		return "editSurveyPage";
	}
	
	/**
	 * 保存编辑调查
	 * @return
	 */
	public String updateSurvey() {
		this.sid = model.getId();
		this.model.setUser(user);
		surveyService.updateSurvey(this.model);
		
		return "designSurveyAction";
	}
	
	public void prepareUpdateSurvey() {
		inputPage = "/editSurvey.jsp";
	}
	
	/**
	 * 删除调查
	 * @return
	 */
	public String deleteSurvey() {
		surveyService.deleteSurvey(sid);
		return "mySurveyAction";
	}
	
	/**
	 * 清空调查
	 * @return
	 */
	public String clearAnswers() {
		surveyService.clearAnswers(sid);
		return "mySurveyAction";
	}
	
	/**
	 * @return
	 */
	public String toggleStatus() {
		surveyService.toggleStatus(sid);
		return "mySurveyAction";
	}
	
	/**
	 * 增加logo
	 * @return
	 */
	public String toAddLogoPage() {
		return "addLogoPage";
	}
	
	public String doAddLogo() {
		if(!StringUtils.isBlank(logoPhotoFileName)) {
			String dir = sc.getRealPath("/upload");
			String ext = logoPhotoFileName.substring(logoPhotoFileName.lastIndexOf("."));
			long l = System.nanoTime();
			File newFile = new File(dir, l + ext);
			logoPhoto.renameTo(newFile);
			
			surveyService.updateLogoPhotoPath(sid, "/upload/" + l + ext);
		}
		
		return "designSurveyAction";
	}
	
	public void prepareDoAddLogo () {
		inputPage = "/addLogo.jsp";
	}
	
	public boolean photoExists() {
		String path = model.getLogoPhotoPath();
		if(StringUtils.isNoneBlank(path)) {
			String absPath = sc.getRealPath(path);
			File file = new File(absPath);
			return file.exists();
		}
		
		return false;
	}
	
	/**
	 * 分析调查
	 * @return
	 */
	public String analyzeSurvey() {
		this.model = surveyService.getSurveyWithChildren(sid);
		return "analyzeSurveyListPage";
	}
	
	
	public List<Survey> getMySurveys() {
		return mySurveys;
	}

	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public File getLogoPhoto() {
		return logoPhoto;
	}

	public void setLogoPhoto(File logoPhoto) {
		this.logoPhoto = logoPhoto;
	}

	public String getLogoPhotoFileName() {
		return logoPhotoFileName;
	}

	public void setLogoPhotoFileName(String logoPhotoFileName) {
		this.logoPhotoFileName = logoPhotoFileName;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.sc = context;
	}

	public String getInputPage() {
		return inputPage;
	}

	public void setInputPage(String inputPage) {
		this.inputPage = inputPage;
	}

	

}
