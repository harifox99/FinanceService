package org.bear.dao;
import java.util.List;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
/**
 * 查詢跨月營收資料
 * @author bear
 *
 */
public class CommonDao extends SimpleJdbcDaoSupport
{
	public List<String> connectSql(String sql)
	{
		System.out.println("SQL: " + sql);
		List<String> data = (List<String>)getJdbcTemplate().queryForList(sql, String.class);
		return data;
	}
}
