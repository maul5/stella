package kr.pe.stella.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {
	
	private static final Logger logger = LoggerFactory.getLogger(TestDao.class);

	@Autowired
	private SqlSession sql;
	
	@SuppressWarnings("unchecked")
	public ArrayList<Map<String, Object>> queryTest() throws SQLException {
		return (ArrayList) sql.selectList("test.queryTest");
	}
}
