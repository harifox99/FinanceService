package org.bear.dao;

import java.util.List;

import org.bear.entity.ThreeBigEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcThreeBigDao extends SimpleJdbcDaoSupport implements ThreeBigDao {

	@Override
	public void insert(ThreeBigEntity entity) {
		try
		{
			String sql = "insert into ThreeBig(StockID, YearMonth, Quantity) values " +
			"(:stockID, :yearMonth, :quantity)";
			//System.out.println("sql: " + sql);
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
			this.getSimpleJdbcTemplate().update(sql, parameterSource);
		}
		catch (Exception ex)
		{
			//ex.printStackTrace();
			System.out.println("Insert Error!");
			System.out.println(entity.getStockID() + "   " + entity.getYearMonth().toString() + "   " + entity.getQuantity());
			System.out.println("Insert Error!");
		}

	}

	@Override
	public List<ThreeBigEntity> latest(int duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(String indexName, String indexValue, String date, String stockID) {
		String sql = "UPDATE ThreeBig SET " + indexName + " = ? where yearMonth = '" + date + "' and stockID = '" + stockID + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}
	

}
