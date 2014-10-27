package kr.pe.stella.common;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 비지니스 공통 업무 - 요청 불필요시
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
	
}
