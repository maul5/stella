package kr.pe.stella.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * 기술적 관점의 유틸리티 클래스
 * 
 * @author jtpark
 *
 */
@Component
public class Util {

	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	/**
	 * Json 형태의 문자열을 키,값 쌍의 리스트 형식으로 변환
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> JsonToMap(String param) {
		Gson gson = new Gson();

		// 테스트 코드
		/*
		Map<String, Object> map = gson.fromJson(param, new HashMap<String,Object>().getClass());
		Set<Entry<String, Object>> ms = map.entrySet();
		for (Entry<String, Object> e : ms) {
			logger.debug(e.getKey() + " : " + e.getValue());
		}
		*/

		return gson.fromJson(param, new HashMap<String, Object>().getClass());
	}

	/**
	 * 키,값 쌍의 리스트 형식을 Json 형태의 문자열로 변환
	 * @param res
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String ListToJson(List<Map<String, Object>> res) {
		Gson gson = new Gson();

		// 테스트 코드
		/*
		 * Map<String, Object> param = gson.fromJson(param, new
		 * HashMap<String,Object>().getClass());
		 * Set<Entry<String, Object>> ms = param.entrySet();
		 * for (Entry<String, Object> e:ms) { System.out.println(e.getKey() +
		 * " : " + e.getValue()); }
		 */

		return gson.toJson(res);
	}

	/**
	 * 스트링 자료를 Json 형태의 문자열로 변환
	 * @param sData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String OneStringToJson(String sData) {
		Gson gson = new Gson();
		return gson.toJson(sData);
	}

}
