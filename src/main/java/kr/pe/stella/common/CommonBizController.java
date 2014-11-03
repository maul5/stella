package kr.pe.stella.common;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * 비지니스 공통 업무 - HTTP Request 필요시
 * 
 * @author jtpark
 *
 */
@Controller
public class CommonBizController {

	private static final Logger logger = LoggerFactory.getLogger(CommonBizController.class);

	@Autowired
	private BaseDao baseDao;
	
	@RequestMapping(value = "login/{sqlid}.do", method = RequestMethod.POST)
	public String login (@PathVariable("sqlid") String sqlid,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) throws JSONException {
		logger.info("login.do");
		
		response.setCharacterEncoding("utf-8");
		
		//TODO 로그인이 완료되면 이전 페이지로 자동으로 이동

		PrintWriter out = null; 
		Map<String, Object> param = null;
		
		if (request.getParameter("param").trim().equals("") == true) {
			param = new HashMap<String, Object>();
		} else {
			JSONObject jsonObject = new JSONObject(request.getParameter("param"));
			param = Util.JsonToMap(jsonObject.toString());
		}
		
		List<Map<String, Object>> resultList = null;
		
		try {
			resultList = baseDao.selectList(sqlid, param);
			
			JSONObject resultJsonObject = new JSONObject();
			out = response.getWriter();
			
			if (resultList.size() >= 1) {
				WebUtils.setSessionAttribute(request, "임시", resultList.get(0));
				
				resultJsonObject = new JSONObject(resultList.get(0));
				resultJsonObject.put("result", "SUCCESS");
				
				out.write(resultJsonObject.toString());
			} else {
				resultJsonObject = new JSONObject();
				resultJsonObject.put("result",  "FAIL");
				resultJsonObject.put("message", "아이디 또는 비밀번호가 다릅니다.");
				
				out.write(resultJsonObject.toString());
			}

		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		return null;
	}
}
