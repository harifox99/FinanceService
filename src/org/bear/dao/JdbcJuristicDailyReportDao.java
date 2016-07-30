package org.bear.dao;
import java.util.List;
import org.bear.entity.JuristicDailyEntity;
import org.bear.entity.RetailInvestorsEntity;
import org.bear.entity.ThreeBigExchangeEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcJuristicDailyReportDao extends SimpleJdbcDaoSupport implements JuristicDailyReportDao {

	@Override
	public void insert(JuristicDailyEntity entity) {
		// TODO Auto-generated method stub
		try
		{
			String sql = "insert into JuristicDailyReport" + "" +
			"(Amount, ExchangeDate) values " +
			"(:amount, :exchangeDate)";
			//System.out.println("sql: " + sql);
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
			this.getSimpleJdbcTemplate().update(sql, parameterSource);
		}
		catch (Exception ex)
		{
			//ex.printStackTrace();
			System.out.println("Insert JuristicDailyEntity Error!");
		}
	}

	@Override
	public int update(String indexName, int indexValue, String date) {
		// TODO Auto-generated method stub
		String sql = "UPDATE JuristicDailyReport SET " + indexName + " = ? where Exchangedate = '" + date + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}

	@Override
	public List<JuristicDailyEntity> findLatestData(int size) {
		List <JuristicDailyEntity> entityList = null;
		String sql = "select top " + size + " * from JuristicDailyReport ORDER BY ExchangeDate desc";
		//System.out.println("SQL: " + sql);
		entityList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(JuristicDailyEntity.class));
		return entityList;
	}

	@Override
	public List<ThreeBigExchangeEntity> findStockBySize(String stockID, int size) {
		// TODO Auto-generated method stub
		List <ThreeBigExchangeEntity> entityList = null;
		String sql = "select top " + size + " * from ThreeBigExchange where stockID = ? ORDER BY ExchangeDate desc";
		entityList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class), stockID);
		return entityList;
	}

	@Override
	public List<ThreeBigExchangeEntity> findTopSingleStock(String date, int rank) 
	{
		List <ThreeBigExchangeEntity> entityList = null;
		try
		{			
			String sql = "select * from ThreeBigExchange where exchangeDate = ? and rank <= " +
			rank + " and rank > 0 ORDER BY rank";
			System.out.println(sql);
			entityList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class), date);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return entityList;
	}

	@Override
	public List<ThreeBigExchangeEntity> findStockByDate(String date, String stockID) 
	{
		List <ThreeBigExchangeEntity> entityList = null;
		try
		{			
			String sql = "select * from ThreeBigExchange where exchangeDate = ? and stockID = ?";			
			System.out.println(sql);
			entityList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class), date, stockID);
		}
		catch (Exception ex)
		{
			return null;
		}
		return entityList;
	}

	@Override
	public int update(String indexName, double indexValue, String date) {
		String sql = "UPDATE JuristicDailyReport SET " + indexName + " = ? where Exchangedate = '" + date + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}

	@Override
	public void insert(RetailInvestorsEntity entity) 
	{
		try
		{
			String sql = "insert into Retail_Mtx " +
			"(TotalMtx, ExchangeDate) values " +
			"(:totalMtx, :exchangeDate)";
			//System.out.println("sql: " + sql);
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
			this.getSimpleJdbcTemplate().update(sql, parameterSource);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Insert RetailInvestorsEntity Error!");
		}
	}

	@Override
	public int update(String tableName, String indexName, int indexValue, String date)
	{
		String sql = "UPDATE " + tableName + " SET " + indexName + " = ? where Exchangedate = '" + date + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;		
	}
	
}
