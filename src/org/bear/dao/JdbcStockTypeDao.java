package org.bear.dao;

import java.util.List;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcStockTypeDao extends SimpleJdbcDaoSupport implements StockTypeDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findAllData() {
		// TODO Auto-generated method stub
		String sql = "select typeId from stockType";
		List<Integer> data = (List<Integer>)getJdbcTemplate().queryForList(sql, Integer.class);
		return data;
	}

}
