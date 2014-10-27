package kr.pe.stella.common;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

public interface BaseDao {

	/**
	 * 목록 가져오기
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public <T> List<T> selectList(String sqlid, Map<String, Object> param);
	
	/**
	 * 단건 가져오기
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public String selectOne(String sqlid, Map<String, Object> param);
	
	/**
	 * 데이터 입력
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public int insert(String sqlid, Map<String, Object> param);
	
	/**
	 * 키 검색해서 데이터 입력
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public String selectKeyToInsert(String sqlid, Map<String, Object> param);
	
	/**
	 * 데이터 수정
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public int update(String sqlid, Map<String, Object> param);
	
	/**
	 * 데이터 삭제
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public int delete(String sqlid, Map<String, Object> param);
	
	/**
	 * 그리드 저장
	 * 
	 * @param sqlCid
	 *            : INSERT SQL ID
	 * @param sqlUid
	 *            : UPDATE SQL ID
	 * @param sqlDid
	 *            : DELETE SQL ID
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridSave(String sqlCid, String sqlUid, String sqlDid,
			JSONArray jsonArray) throws Exception;
	
	/**
	 * 그리드 데이터 입력
	 * 
	 * @param sqlid
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridInsert(String sqlid, JSONArray jsonArray) throws Exception;
	
	/**
	 * 그리드 데이터 수정
	 * 
	 * @param sqlid
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridUpdate(String sqlid, JSONArray jsonArray) throws Exception;
	
	/**
	 * 그리드 삭제
	 * 
	 * @param sqlid
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridDelete(String sqlid, JSONArray jsonArray) throws Exception;
	
	/**
	 * 그리드 삭제 (Master/Detail)
	 * 
	 * @param sqlMid
	 *            : 마스터삭제 SQL ID
	 * @param sqlDid
	 *            : 디테일삭제 SQL ID
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridMasterDetailDelete(String sqlMid, String sqlDid,
			JSONArray jsonArray) throws Exception;
	
	/**
	 * 그리드 저장 (DELETE/INSERT)
	 * 
	 * @param sqlDid
	 *            : DELETE SQL ID
	 * @param sqlCid
	 *            : INSERT SQL ID
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridDeleteInsert(String sqlDid, String sqlCid,
			JSONArray jsonArray) throws Exception;
	
	
}
