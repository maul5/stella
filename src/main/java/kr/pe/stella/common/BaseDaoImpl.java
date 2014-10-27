package kr.pe.stella.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 기본적인 쿼리 처리 패턴
 * 
 * @author jtpark
 *
 */
@Repository
public class BaseDaoImpl implements BaseDao {

	private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Autowired
	private SqlSession sqlSession;

//	@Autowired
//	protected PlatformTransactionManager transactionManager;

	/**
	 * 목록 가져오기
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public <T> List<T> selectList(String sqlid, Map<String, Object> param) {
		logger.info("SQL ID = [" + sqlid + "]");
		return sqlSession.selectList(sqlid, param);
	}

	/**
	 * 단건 가져오기
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public String selectOne(String sqlid, Map<String, Object> param) {
		logger.info("SQL ID = [" + sqlid + "]");
		return sqlSession.selectOne(sqlid, param);
	}

	/**
	 * 데이터 입력
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public int insert(String sqlid, Map<String, Object> param) {
		logger.info("SQL ID = [" + sqlid + "]");
		return sqlSession.insert(sqlid, param);
	}

	/**
	 * 키 검색해서 데이터 입력
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public String selectKeyToInsert(String sqlid, Map<String, Object> param) {
		logger.info("SQL ID = [" + sqlid + "]");
		sqlSession.insert(sqlid, param);
		return param.get("newKey").toString();
	}

	/**
	 * 데이터 수정
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public int update(String sqlid, Map<String, Object> param) {
		logger.info("SQL ID = [" + sqlid + "]");
		return sqlSession.update(sqlid, param);
	}

	/**
	 * 데이터 삭제
	 * 
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public int delete(String sqlid, Map<String, Object> param) {
		logger.info("SQL ID = [" + sqlid + "]");
		return sqlSession.delete(sqlid, param);
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
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridSave(String sqlCid, String sqlUid, String sqlDid,
			JSONArray jsonArray) throws Exception {
		logger.info("SQL ID = [{sqlCid=" + sqlCid + ", sqlUid=" + sqlUid + ", sqlDid=" + sqlDid + "}]");

		int iResult = 0;

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				Map<String, Object> param = Util.JsonToMap(jsonObject
						.toString());

				String rowStatus = param.get("rowStatus").toString();
				logger.info("C/U/D rowStatus = " + rowStatus);

				if (rowStatus.equals("C") == true) {
					iResult = sqlSession.insert(sqlCid, param);
				} else if (rowStatus.equals("U") == true) {
					iResult = sqlSession.update(sqlUid, param);
				} else if (rowStatus.equals("D") == true) {
					iResult = sqlSession.delete(sqlDid, param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			iResult = -1;
		}

		return iResult;
	}

	/**
	 * 그리드 데이터 입력
	 * 
	 * @param sqlid
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridInsert(String sqlid, JSONArray jsonArray) throws Exception {
		logger.info("SQL ID = [" + sqlid + "]");

		int iResult = 0;

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				Map<String, Object> param = Util.JsonToMap(jsonObject
						.toString());

				iResult = sqlSession.insert(sqlid, param);
			}

		} catch (Exception e) {
			e.printStackTrace();

			iResult = -1;
		}

		return iResult;
	}

	/**
	 * 그리드 데이터 수정
	 * 
	 * @param sqlid
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridUpdate(String sqlid, JSONArray jsonArray) throws Exception {
		logger.info("SQL ID = [" + sqlid + "]");

		int iResult = 0;

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				Map<String, Object> param = Util.JsonToMap(jsonObject
						.toString());

				iResult = sqlSession.update(sqlid, param);
			}

		} catch (Exception e) {
			e.printStackTrace();

			iResult = -1;
		}

		return iResult;
	}

	/**
	 * 그리드 삭제
	 * 
	 * @param sqlid
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public int gridDelete(String sqlid, JSONArray jsonArray) throws Exception {
		logger.info("SQL ID = [" + sqlid + "]");

		int iResult = 0;

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				Map<String, Object> param = Util.JsonToMap(jsonObject
						.toString());

				iResult = sqlSession.delete(sqlid, param);
			}

		} catch (Exception e) {
			e.printStackTrace();

			iResult = -1;
		}

		return iResult;
	}

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
			JSONArray jsonArray) throws Exception {
		logger.info("SQL ID = [{sqlMid=" + sqlMid + ", sqlDid=" + sqlDid + "}]");

		int iResult = 0;

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				Map<String, Object> param = Util.JsonToMap(jsonObject
						.toString());

				iResult = sqlSession.delete(sqlDid, param);

				iResult = sqlSession.delete(sqlMid, param);
			}

		} catch (Exception e) {
			e.printStackTrace();

			iResult = -1;
		}

		return iResult;
	}

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
			JSONArray jsonArray) throws Exception {
		logger.info("SQL ID = [{sqlDid=" + sqlDid + ", sqlCid=" + sqlCid + "}]");

		int iResult = 0;

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				Map<String, Object> param = Util.JsonToMap(jsonObject
						.toString());

				if (i == 0) { // 최초 한번 Delete
					iResult = sqlSession.delete(sqlDid, param);
				}

				iResult = sqlSession.insert(sqlCid, param);
			}

		} catch (Exception e) {
			e.printStackTrace();

			iResult = -1;
		}

		return iResult;
	}
}
