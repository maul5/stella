package kr.pe.stella.common;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 비지니스 공통 업무 - HTTP Request 불필요시
 * 
 * @author jtpark
 *
 */
@Component
public class CommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * DB에 Mybatis Interceptor로부터 쿼리정보를 받아 저장한다
	 * 
	 * @param query
	 * @param type
	 */
	public static void appendLog(String query, String type) {

		if (type != "query") {

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession();

			String menuId = "";// (String)
								// session.getAttribute("SESSION_CURRENT_URL");
			String userId = "";// (String)
								// session.getAttribute("SESSION_USER_ID");

			if ((String) session.getAttribute("SESSION_CURRENT_URL") != null)
				menuId = (String) session.getAttribute("SESSION_CURRENT_URL");
			if ((String) session.getAttribute("SESSION_USER_ID") != null)
				userId = (String) session.getAttribute("SESSION_USER_ID");

			if (!userId.equals("")) {
				if (query.toUpperCase().indexOf("INSERT") > -1)
					type = "INSERT";
				else if (query.toUpperCase().indexOf("UPDATE") > -1)
					type = "UPDATE";
				else if (query.toUpperCase().indexOf("DELETE") > -1)
					type = "DELETE";
				else
					type = "SELECT";

				String s = query.replaceAll("'", "");

				if (s.equals(""))
					s = "no Query";

				String sql = "INSERT INTO S_ACTION_LOG(LOG_ID, ACTION_TYPE, QUERY, USER_ID, MENU_ID, ACTION_DATE, ACTION_TIME) VALUES";
				sql += "(S_ACTION_LOG_SEQ.nextval,";
				sql += "'" + type + "',";
				sql += "?,";
				sql += "'" + userId + "',";
				sql += "'" + menuId + "',";
				sql += "TO_CHAR(sysDate, 'YYYYMMDD'),";
				sql += "TO_CHAR(sysDate, 'HH24MISS'))";
			}
		}
	}
	
	/**
	 * requset.getParameter()으로 받은 값 중에 스크립트 실행이 되는 기호를 일반 스트링으로 변환 
	 * 크로스사이트 스크립트 방지
	 * 
	 * @param param
	 * @return
	 */
	public static String replaceTagFromParam(String param) {
		String result = null;
		if (param != null && !"".equals(param)) {
			result = new String(param);
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("\"", "&quot;");
		}
		return result;
	}
	
	/**
	 * 파일 업로드시 경로 조작 경로조작 및 자원 삽입 방지를 위해 경로에 사용되는 기호 없애기
	 * 
	 * @param param
	 * @return
	 */
	public static String replaceULIFromPath(String fileName) {
		String result = null;
		if (fileName != null && !"".equals(fileName)) {
			result = new String(fileName);
			result = result.replaceAll("/", "");
			result = result.replaceAll("\\", "");
			result = result.replaceAll(".", "");
			result = result.replaceAll("&", "");
		}
		
		return result;
	}
}
