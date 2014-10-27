package kr.pe.stella.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface TestDao {
	public ArrayList<Map<String, Object>> queryTest() throws SQLException;
}
