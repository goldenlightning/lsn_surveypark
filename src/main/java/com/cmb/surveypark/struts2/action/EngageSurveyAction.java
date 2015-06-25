package com.cmb.surveypark.struts2.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cmb.surveypark.model.Answer;
import com.cmb.surveypark.model.Page;
import com.cmb.surveypark.model.Survey;
import com.cmb.surveypark.service.SurveyService;
import com.google.common.primitives.Ints;

@Controller
@Scope("prototype")
public class EngageSurveyAction extends BaseAction<Survey> implements ServletContextAware, SessionAware, ParameterAware {

	private static final String ALL_PARAMS = "all_params";

	private static final String CURRENT_SURVEY = "current_survey";

	private static final long serialVersionUID = 410445770614131729L;

	private List<Survey> surveys;
	
	private Integer sid;
	//当前页面id
	private Integer currPid;
	//当前页面
	private Page currPage;
	
	@Resource
	private SurveyService surveyService;

	private ServletContext sc;

	private Map<String, Object> session;

	private Map<String, String[]> paramsMap;
	
	public String findAllAvailableSurveys() {
		surveys = surveyService.finAllAvailableSurveys();
		return "engageSurveyListPage";
	}
	
	/**
	 * 首次进入参与调查
	 * @return
	 */
	public String entry() {
		currPage = surveyService.getFirstPage(sid);
		session.put(CURRENT_SURVEY, currPage.getSurvey());
		session.put(ALL_PARAMS, new HashMap<Integer, Map<String, String[]>>());
		return "engageSurveyPage";
	}
	
	/**
	 * 处理参与调查
	 * @return
	 */
	public String doEngageSurvey() {
		String submitName = getSubmitName();
		//上一步
		if(StringUtils.endsWith(submitName, "pre")) {
			mergeParamsIntoSession();
			currPage = surveyService.getPrePage(currPid);
			return "engageSurveyPage";
		}
		//下一步
		else if(StringUtils.endsWith(submitName, "next")) {
			mergeParamsIntoSession();
			currPage = surveyService.getNextPage(currPid);
			return "engageSurveyPage";
		}
		//完成
		else if(StringUtils.endsWith(submitName, "done")) {
			mergeParamsIntoSession();
			surveyService.saveAnswers(processAnswers());
			clearParamsFromSession();
			return "engageSurveyAction";
		}
		//退出
		else if(StringUtils.endsWith(submitName, "exit")) {
			clearParamsFromSession();
			return "engageSurveyAction";
		}
		
		return null;
	}
	
	
	private List<Answer> processAnswers() {
		Map<Integer, Map<String, String[]>> allParamsMap = getAllParamsMap();
		Map<Integer, String> matrixRadioMap = new HashMap<Integer, String>();
		List<Answer> answers = new ArrayList<Answer>();
		String key = null;
		String value = null;
		Answer answer = null;
		for(Map<String, String[]> map : allParamsMap.values()) {
			for(Entry<String, String[]> entry : map.entrySet()) {
				key = entry.getKey();
				value = StringUtils.join(entry.getValue(), ",");
				if(StringUtils.startsWith(key, "q")) {
					//常规参数
					if(!StringUtils.contains(key, "other") && !StringUtils.contains(key, "_")) {
						answer = new Answer();
						answer.setAnswerIds(value);
						answer.setQuestionId(getQid(key));
						answer.setSurveyId(getCurrentSurveyId());
						answer.setOtherAnswer(StringUtils.join(map.get(key + "other"), ","));
						answers.add(answer);
					} 
					//矩阵式单选按钮
					else if(StringUtils.contains(key, "_")) {
						Integer radioQid = getMatrixRadioQid(key);
						if(matrixRadioMap.get(radioQid) == null) {
							matrixRadioMap.put(radioQid, value);
						} else {
							matrixRadioMap.put(radioQid, matrixRadioMap.get(radioQid) + "," + value);
						}
						
					}
				}
			}
		}
		processMatrixRadioMap(matrixRadioMap, answers);
		
		return answers;
	}

	private void processMatrixRadioMap(Map<Integer, String> matrixRadioMap, List<Answer> answers) {
		Integer key = null;
		String value = null;
		Answer answer = null;
		
		for(Entry<Integer, String> entry : matrixRadioMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			answer = new Answer();
			answer.setAnswerIds(value);
			answer.setQuestionId(key);
			answer.setSurveyId(getCurrentSurveyId());
			answers.add(answer);
		}
	}

	private Integer getMatrixRadioQid(String str) {
		return Ints.tryParse(StringUtils.substring(str, 1, StringUtils.lastIndexOf(str, "_")));
	}

	private Integer getCurrentSurveyId() {
		return ((Survey)session.get(CURRENT_SURVEY)).getId();
	}

	private Integer getQid(String str) {
		return Ints.tryParse(StringUtils.substring(str, 1));
	}

	private void clearParamsFromSession() {
		session.remove(CURRENT_SURVEY);
		session.remove(ALL_PARAMS);
	}

	private void mergeParamsIntoSession() {
		Map<Integer, Map<String, String[]>> allParamsMap = getAllParamsMap();
		allParamsMap.put(currPid, paramsMap);
	}

	@SuppressWarnings("unchecked")
	private Map<Integer, Map<String, String[]>> getAllParamsMap() {
		return (Map<Integer, Map<String, String[]>>) session.get(ALL_PARAMS);
	}

	private String getSubmitName() {
		for(String key : paramsMap.keySet()) {
			if(StringUtils.startsWith(key, "submit")) {
				return key;
			}
		}
		return null;
	}

	public String getImageUrl(String path) {
		if(!StringUtils.isBlank(path)) {
			String absPath = sc.getRealPath(path);
			File f = new File(absPath);
			if(f.exists()) {
				return sc.getContextPath() + path;
			}
		}
		return sc.getContextPath() + "/question.bmp";
	}
	
	/**
	 * 用于回显非文本框数据
	 */
	public String setTag(String key, String value, String tag) {
		Map<String, String[]> params = getAllParamsMap().get(currPage.getId());
		String[] values = params.get(key);
		if(ArrayUtils.contains(values, value)) {
			return tag;
		}
		return "";
	}
	
	/**
	 * 用于回显文本框数据
	 */
	public String setText(String key) {
		Map<String, String[]> params = getAllParamsMap().get(currPage.getId());
		String[] values = params.get(key);
		return "value='" + values[0] + "'";
	}


	public List<Survey> getSurveys() {
		return surveys;
	}


	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.sc = context;
		
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Page getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Page currPage) {
		this.currPage = currPage;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.paramsMap = parameters;
		
	}

	public Integer getCurrPid() {
		return currPid;
	}

	public void setCurrPid(Integer currPid) {
		this.currPid = currPid;
	}
	
	
}
