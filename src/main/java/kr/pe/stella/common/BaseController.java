package kr.pe.stella.common;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 기본적인 컨트롤 처리 패턴
 * 
 * @author jtpark
 *
 */
@Controller
public class BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	@Autowired
	private BaseDao baseDao;

	/**
	 * 인터셉트를 거친 후 path에 지정된 페이지로 이동
	 * 
	 * @param request
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "direct_view.do") //method = {RequestMethod.POST, RequestMethod.GET}
	public String directView(HttpServletRequest request, Locale locale,
			Model model) {
		String path = request.getParameter("path");
		logger.info("direct_view.do, path=" + path); 
		
		return path;
	}

	/**
	 * 목록 조회 sqlid 수행
	 * 
	 * @param sqlid
	 * @param request
	 * @param response
	 * @param locale
	 * @param model
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value = "select/{sqlid}.do", method = RequestMethod.POST)
	public String selectList(@PathVariable("sqlid") String sqlid,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) throws JSONException {
		logger.info("select/" + sqlid + ".do");

		response.setCharacterEncoding("UTF-8");

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
			model.addAttribute("resultList", resultList);

			JSONArray jsonList = new JSONArray(Util.ListToJson(resultList));
			model.addAttribute("jsonList", jsonList); // Retun Json String

			out = response.getWriter();
			out.write(Util.ListToJson(resultList)); // Ajax Retun Json String
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return request.getParameter("path");
	}

	/**
	 * § 문자를 구분으로 하여 목록 조회 여러 sqlid 수행
	 * 
	 * @param request
	 * @param response
	 * @param locale
	 * @param model
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value = "multiSelect.do", method = RequestMethod.POST)
	public String multiSelectList(HttpServletRequest request,
			HttpServletResponse response, Locale locale, Model model) throws JSONException {
		logger.info("multiSelect.do");

		response.setCharacterEncoding("UTF-8");

		PrintWriter out = null;
		Map<String, Object> param = null;
		String[] sqls = null;

		if (request.getParameter("param").trim().equals("") == true) {
			param = new HashMap<String, Object>();
		} else {
			JSONObject jsonObject = new JSONObject(request.getParameter("param"));
			param = Util.JsonToMap(jsonObject.toString());
			sqls = String.valueOf(param.get("sqls")).split("§"); // § 문자로 분리
		}
		ArrayList list = new ArrayList();
		List<Map<String, Object>> resultList = null;

		try {
			if (sqls != null && sqls.length != 0) {
				String sqlid = "";
				for (int i = 0; i < sqls.length; i++) {
					logger.info("SQL ID = [" + sqlid + "]");
					sqlid = sqls[i];
					resultList = baseDao.selectList(sqlid, param);
					list.add(i, resultList);
				}
			}

			model.addAttribute("resultList", list);

			out = response.getWriter();
			out.write(Util.ListToJson(resultList)); // Ajax Retun Json String
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return request.getParameter("path");
	}

	/**
	 * 입력 sqlid 수행
	 * 
	 * @param sqlid
	 * @param request
	 * @param response
	 * @param locale
	 * @param model
	 * @return
	 * @throws JSONException 
	 */
	@Transactional
	@RequestMapping(value = "insert/{sqlid}", method = RequestMethod.POST)
	public String insert(@PathVariable("sqlid") String sqlid,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) throws JSONException {
		logger.info("insert/" + sqlid + ".do");

		response.setCharacterEncoding("UTF-8");

		int iResult = 0;
		PrintWriter out = null;

		JSONObject jsonObject = new JSONObject(request.getParameter("param"));
		Map<String, Object> param = Util.JsonToMap(jsonObject.toString());

		try {
			iResult = baseDao.insert(sqlid, param);
			logger.info("INSERT 처리 건수 = " + iResult);

			if (iResult == 1) {
				model.addAttribute("result", "SUCCESS");
				model.addAttribute("path", request.getParameter("path"));
				model.addAttribute("action", request.getParameter("action"));
				model.addAttribute("message", "정상적으로 등록 되었습니다.");

				out = response.getWriter();
				out.write(Util.OneStringToJson("SUCCESS"));
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return "/views/result";
	}

	/**
	 * 새로운 KEY로 입력 sqlid 수행
	 * 
	 * @param sqlid
	 * @param request
	 * @param response
	 * @param locale
	 * @param model
	 * @throws JSONException 
	 */
	@Transactional
	@RequestMapping(value = "select_key_to_insert/{sqlid}", method = RequestMethod.POST)
	public String selectKeyToInsert(@PathVariable("sqlid") String sqlid,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) throws JSONException {
		logger.info("selectKeyToInsert/" + sqlid + ".do");

		response.setCharacterEncoding("UTF-8");

		String sResult = "";
		PrintWriter out = null;

		JSONObject jsonObject = new JSONObject(request.getParameter("param"));
		Map<String, Object> param = Util.JsonToMap(jsonObject.toString());

		try {
			// Insert
			sResult = baseDao.selectKeyToInsert(sqlid, param);
			logger.info("새롭게 INSERT한 KEY = " + sResult);

			if (sResult != "") {
				model.addAttribute("result", "SUCCESS");
				model.addAttribute("path", request.getParameter("path"));
				model.addAttribute("action", request.getParameter("action"));
				model.addAttribute("message", "정상적으로 등록 되었습니다.");

				out = response.getWriter();
				out.write(Util.OneStringToJson(sResult));
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		
		return "/views/result";
	}

	/**
	 * 수정 sqlid 수행
	 * 
	 * @param sqlid
	 * @param request
	 * @param response
	 * @param locale
	 * @param model
	 * @return
	 * @throws JSONException 
	 */
	@Transactional
	@RequestMapping(value = "update/{sqlid}", method = RequestMethod.POST)
	public String update(@PathVariable("sqlid") String sqlid,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) throws JSONException {
		logger.info("update/" + sqlid + ".do");

		response.setCharacterEncoding("UTF-8");

		int iResult = 0;
		PrintWriter out = null;

		JSONObject jsonObject = new JSONObject(request.getParameter("param"));
		Map<String, Object> param = Util.JsonToMap(jsonObject.toString());

		try {
			iResult = baseDao.update(sqlid, param);
			logger.info("UPDATE 처리 건수 = " + iResult);

			if (iResult == 1) {
				model.addAttribute("result", "SUCCESS");
				model.addAttribute("path", request.getParameter("path"));
				model.addAttribute("action", request.getParameter("action"));
				model.addAttribute("message", "정상적으로 수정 되었습니다.");

				out = response.getWriter();
				out.write(Util.OneStringToJson("SUCCESS"));
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return "/views/result";
	}

	/**
	 * 삭제 sqlid 수행
	 * 
	 * <화면단 코딩 예제1> var obj = {}; obj.itemId = frm.itemId.value; obj.itemName =
	 * frm.itemName.vaule;
	 * 
	 * $("input[name=param]").val(JSON.stringify(obj)); <!-- 1 -->
	 * $("input[name=action]").val("/select/test.queryTest.do"); <!-- 2 -->
	 * $("input[name=path]").val("/views/test/queryTest"); <!-- 3 -->
	 * 
	 * $("#frm").attr({ action : "/delete/test.deleteTest.do", target : "_self",
	 * method : "POST" }); <!-- 1 --> $("#frm").submit();
	 * 
	 * <화면단 코딩 예제2>$.ajax({ type : "POST", url :
	 * "/delete/test.deleteTest.do", dataType :
	 * "json", data : {"param" : JSON.stringify(obj)}, async : false,
	 * beforeSend : function(xhr) { // 전송 전 Code }, success : function(result) {
	 * alert("정상적으로 삭제되었습니다."); }, error : function() { // Error 발생 Code
	 * alert("Error 발생"); } });
	 * 
	 * @param sqlid
	 * @param request
	 * @param locale
	 * @param model
	 * @return
	 * @throws JSONException 
	 */
	@Transactional
	@RequestMapping(value = "delete/{sqlid}", method = RequestMethod.POST)
	public String delete(@PathVariable("sqlid") String sqlid,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) throws JSONException {
		logger.info("delete/" + sqlid + ".do");

		response.setCharacterEncoding("UTF-8");

		int iResult = 0;
		PrintWriter out = null;

		JSONObject jsonObject = new JSONObject(request.getParameter("param"));
		Map<String, Object> param = Util.JsonToMap(jsonObject.toString());

		try {
			iResult = baseDao.delete(sqlid, param);
			logger.info("DELETE 처리 건수 = " + iResult);

			if (iResult > -1) {
				model.addAttribute("result", "SUCCESS");
				model.addAttribute("path", request.getParameter("path"));
				model.addAttribute("action", request.getParameter("action"));
				model.addAttribute("message", "정상적으로 삭제 되었습니다.");

				out = response.getWriter();
				out.write(Util.OneStringToJson("SUCCESS"));
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return "/views/result";
	}
	
	/**
	 * 그리드 저장
	 * 
	 * @param sqlCid
	 *            : INSERT SQL ID
	 * @param sqlUid
	 *            : UPDATE SQL ID
	 * @param sqlDid
	 *            : DELETE SQL ID
	 * @param request
	 * @param locale
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "grid_save/{sqlCid}/{sqlUid}/{sqlDid}", method = RequestMethod.POST)
	public String gridSave(@PathVariable("sqlCid") String sqlCid,
			@PathVariable("sqlUid") String sqlUid,
			@PathVariable("sqlDid") String sqlDid, HttpServletRequest request,
			Locale locale, Model model) {
		logger.info("grid_save/" + sqlCid + "/" + sqlUid + "/" + sqlDid
				+ ".do");

		int iResult = 0;

		try {
			if (request.getParameter("param").trim().equals("") != true) {
				JSONArray jsonArray = new JSONArray(request.getParameter(
						"param").toString());

				iResult = baseDao.gridSave(sqlCid, sqlUid, sqlDid, jsonArray);

				if (iResult == 1) {
					model.addAttribute("result", "SUCCESS");
					model.addAttribute("path", request.getParameter("path"));
					model.addAttribute("action", request.getParameter("action"));
					model.addAttribute("message", "정상적으로 저장 되었습니다.");
				}
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return "/views/result";
	}

	/**
	 * 그리드 데이터 입력
	 * 
	 * @param sqlid
	 * @param request
	 * @param locale
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "grid_insert/{sqlid}", method = RequestMethod.POST)
	public String gridInsert(@PathVariable("sqlid") String sqlid,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) {
		logger.info("grid_insert/" + sqlid + ".do");

		response.setCharacterEncoding("UTF-8");

		int iResult = 0;
		PrintWriter out = null;

		try {
			if (request.getParameter("param").trim().equals("") != true) {
				JSONArray jsonArray = new JSONArray(request.getParameter(
						"param").toString());

				iResult = baseDao.gridInsert(sqlid, jsonArray);
				logger.info("INSERT 처리 건수 = " + iResult);

				if (iResult > 0) {
					model.addAttribute("result", "SUCCESS");
					model.addAttribute("path", request.getParameter("path"));
					model.addAttribute("action", request.getParameter("action"));
					model.addAttribute("message", "정상적으로 등록 되었습니다.");

					out = response.getWriter();
					out.write(Util.OneStringToJson("SUCCESS"));
				}
			}
		} catch (Exception ex) {
			iResult = 0;
			logger.error(ex.toString());
		}

		return "/views/result";
	}

	/**
	 * 그리드 데이터 삭제
	 * 
	 * @param sqlid
	 * @param request
	 * @param response
	 * @param locale
	 * @param model
	 */
	@Transactional
	@RequestMapping(value = "grid_delete/{sqlid}", method = RequestMethod.POST)
	public String gridDelete(@PathVariable("sqlid") String sqlid,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) {
		logger.info("grid_delete/" + sqlid + ".do");

		response.setCharacterEncoding("UTF-8");

		int iResult = 0;
		PrintWriter out = null;

		try {
			if (request.getParameter("param").trim().equals("") != true) {
				JSONArray jsonArray = new JSONArray(request.getParameter(
						"param").toString());

				iResult = baseDao.gridDelete(sqlid, jsonArray);
				logger.info("DELETE 처리 건수 = " + iResult);

				if (iResult > 0) {
					model.addAttribute("result", "SUCCESS");
					model.addAttribute("path", request.getParameter("path"));
					model.addAttribute("action", request.getParameter("action"));
					model.addAttribute("message", "정상적으로 삭제 되었습니다.");

					out = response.getWriter();
					out.write(Util.OneStringToJson("SUCCESS"));
				}
			}
		} catch (Exception ex) {
			iResult = 0;
			logger.error(ex.toString());
		}
		
		return "/views/result";
	}

	/**
	 * 그리드 삭제 (Master/Detail)
	 * 
	 * @param sqlMid
	 *            : 마스터삭제 SQL ID
	 * @param sqlDid
	 *            : 디테일삭제 SQL ID
	 * @param request
	 * @param response
	 * @param locale
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "grid_master_detail_delete/{sqlMid}/{sqlDid}", method = RequestMethod.POST)
	public String gridMasterDetailDelete(@PathVariable("sqlMid") String sqlMid,
			@PathVariable("sqlDid") String sqlDid, HttpServletRequest request,
			HttpServletResponse response, Locale locale, Model model) {
		logger.info("grid_master_detail_delete/" + sqlMid + "/" + sqlDid + ".do");

		response.setCharacterEncoding("UTF-8");

		int iResult = 0;
		PrintWriter out = null;

		try {
			if (request.getParameter("param").trim().equals("") != true) {
				JSONArray jsonArray = new JSONArray(request.getParameter(
						"param").toString());

				iResult = baseDao.gridMasterDetailDelete(sqlMid, sqlDid,
						jsonArray);

				if (iResult > 0) {
					model.addAttribute("result", "SUCCESS");
					model.addAttribute("path", request.getParameter("path"));
					model.addAttribute("action", request.getParameter("action"));
					model.addAttribute("message", "정상적으로 삭제 되었습니다.");

					out = response.getWriter();
					out.write(Util.OneStringToJson("SUCCESS"));
				}
			}
		} catch (Exception ex) {
			iResult = 0;
			logger.error(ex.toString());
		}

		return "/views/result";
	}

	/**
	 * 그리드 저장 (DELETE/INSERT)
	 * 
	 * @param sqlDid
	 *            : DELETE SQL ID
	 * @param sqlCid
	 *            : INSERT SQL ID
	 * @param request
	 * @param response
	 * @param locale
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "grid_delete_insert/{sqlDid}/{sqlCid}", method = RequestMethod.POST)
	public String gridDeleteInsert(@PathVariable("sqlDid") String sqlDid,
			@PathVariable("sqlCid") String sqlCid, HttpServletRequest request,
			HttpServletResponse response, Locale locale, Model model) {
		logger.info("grid_delete_insert/" + sqlDid + "/" + sqlCid + ".do");

		response.setCharacterEncoding("UTF-8");

		int iResult = 0;
		PrintWriter out = null;

		try {
			if (request.getParameter("param").trim().equals("") != true) {
				JSONArray jsonArray = new JSONArray(request.getParameter(
						"param").toString());

				iResult = baseDao.gridDeleteInsert(sqlDid, sqlCid,
						jsonArray);

				if (iResult == 1) {
					model.addAttribute("result", "SUCCESS");
					model.addAttribute("path", request.getParameter("path"));
					model.addAttribute("action", request.getParameter("action"));
					model.addAttribute("message", "정상적으로 저장 되었습니다.");

					out = response.getWriter();
					out.write(Util.OneStringToJson("SUCCESS"));
				}
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return "/views/result";
	}

}
