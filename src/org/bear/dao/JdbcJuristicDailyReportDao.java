package org.bear.dao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.bear.entity.JuristicDailyEntity;
import org.bear.entity.RetailInvestorsEntity;
import org.bear.entity.ThreeBigExchangeEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
	public List<JuristicDailyEntity> findLatestData(int size) 
	{
		List <JuristicDailyEntity> entityList = null;
		try
		{			
			String sql = "select top " + size + " * from JuristicDailyReport ORDER BY ExchangeDate desc";
			System.out.println("SQL: " + sql);
			entityList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(JuristicDailyEntity.class));			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return entityList;
	}

	@Override
	public List<ThreeBigExchangeEntity> findStockBySize(String stockID, int size) {
		// TODO Auto-generated method stub
		List <ThreeBigExchangeEntity> entityList = null;
		String sql = "select top " + size + " * from ThreeBigExchange where stockID = ? ORDER BY ExchangeDate desc";
		entityList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class), stockID);
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
			entityList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class), date);
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
			entityList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class), date, stockID);
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
			"(InstitutionalMtx, ExchangeDate) values " +
			"(:institutionalMtx, :exchangeDate)";
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

	/**
	 * 查詢散戶指標
	 */
	@Override
	public List<RetailInvestorsEntity> findRetailInvestors(int size) {
		List <RetailInvestorsEntity> entityList = null;
		String sql = "select top " + size + " * from Retail_Mtx order by exchangeDate desc";
		//System.out.println("SQL: " + sql);
		entityList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(RetailInvestorsEntity.class));
		Collections.reverse(entityList);
		return entityList;
	}
	/**
	 * 用日期查詢法人日報
	 */
	@Override
	public JuristicDailyEntity findByDate(Date exchangeDate) 
	{
		String sql = "select * from JuristicDailyReport where exchangeDate = '" + exchangeDate + "'";
		JuristicDailyEntity entity = this.getSimpleJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(JuristicDailyEntity.class));
		return entity;
	}
	/**
	 * 得到某支股票的交易資料 (誰買的, 交易日期, 哪支股票, 成交量)
	 * stockID 股票代碼
	 * size 要查詢幾天的交易日
	 * buyer 交易人
	 */
	@Override
	public List<ThreeBigExchangeEntity> findStockBySize(String stockID, int size, String buyer) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List <ThreeBigExchangeEntity> entityList = new ArrayList<ThreeBigExchangeEntity>();
		// TODO Auto-generated method stub	
		//TSMC一定會有交易資料，透過TSMC取得過去的交易日期
		String sql = "select top (" + size + ")"  + " * from ThreeBigExchange where StockID = '2330' and exchanger = '外資' order by ExchangeDate desc";		
		List<ThreeBigExchangeEntity> exchangeDateList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class));
		for (int i = 0; i < exchangeDateList.size(); i++)
		{
			try
			{	
				String dateString = dateFormat.format(exchangeDateList.get(i).getExchangeDate());
				sql = "select * from ThreeBigExchange where stockID = ? and exchanger = ? and exchangeDate = ? ORDER BY ExchangeDate desc";
				ThreeBigExchangeEntity entity = this.getSimpleJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class), stockID, buyer, dateString);
				entityList.add(entity);
			}
			catch (EmptyResultDataAccessException ex)
			{
				ThreeBigExchangeEntity emptyDate = new ThreeBigExchangeEntity();
				emptyDate.setStockID(stockID);
				emptyDate.setExchanger(buyer);	
				emptyDate.setExchangeDate(exchangeDateList.get(i).getExchangeDate());
				emptyDate.setQuantity(0);
				entityList.add(emptyDate);						
			}
		}
		return entityList;
	}

	@Override
	public ThreeBigExchangeEntity findStockByDateAndBuyer(
			String stockID, String date, String buyer) 
	{
		try
		{
			ThreeBigExchangeEntity entity;		
			String sql = "select * from ThreeBigExchange where exchangeDate = ? and stockID = ? and exchanger = ?";			
			//System.out.println(sql);
			entity = this.getSimpleJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class), date, stockID, buyer);
			return entity;
		}
		catch (Exception ex)
		{
			//ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 將買賣超資料+排名
	 */
	public void updateRank(String buyer, String exchangeDate)
	{
		String sql = "select * from ThreeBigExchange" +
        " where (Exchanger = ?) AND (ExchangeDate = ?)" +
        " order by Quantity desc";		
		List<ThreeBigExchangeEntity> exchangeDateList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(ThreeBigExchangeEntity.class), buyer, exchangeDate);
		int rank = 1;
		for (int i = 0; i < exchangeDateList.size(); i++)
		{
			ThreeBigExchangeEntity entity = exchangeDateList.get(i);
			sql = "update ThreeBigExchange set rank = '" + rank++ +
			"' where stockID = '" + entity.getStockID() + "' and exchangeDate = '" + entity.getExchangeDate() + "' and" + 
			" exchanger = '" + entity.getExchanger() + "'";
			this.getSimpleJdbcTemplate().update(sql);
		}
	}
}
