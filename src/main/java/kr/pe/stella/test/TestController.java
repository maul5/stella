package kr.pe.stella.test;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 서버 접속, DB 접속 등 기능 테스트의 역활을 맞은 클래스
 */
@Controller
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestDao testDao;
	
	/**
	 * Request 테스트
	 */
	@RequestMapping(value = "/requestTest.do", method = RequestMethod.GET)
	public String requestTest(Locale locale, Model model) {
		logger.info("Welcome site! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "test/requestTest";
	}
	
	
	/**
	 * DB접속 테스트
	 */
	@RequestMapping(value = "/queryTest.do", method = RequestMethod.GET)
	public String queryTest(Model model) {
		logger.info("queryTest 접속");
		
		try {
			ArrayList<Map<String, Object>> listResult = testDao.queryTest();
			
			if ( (listResult != null) && (listResult.size() > 0) ) {
				for (Map<String, Object> mapRow : listResult) {
					Set<String> keySet = mapRow.keySet();
					for (Iterator key = keySet.iterator(); key.hasNext(); ) {
						String keyName = (String)key.next();
						String valueName = (String)mapRow.get(keyName);
						
						System.out.println(keyName + " = " + valueName);
					}
				}
			}
			
			model.addAllAttributes(listResult);
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return "test/queryTest";
	}
	
}
