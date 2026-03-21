package org.bear.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.bear.entity.ThreeBigEntity;
import org.bear.util.DateTimeFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
	public List<ThreeBigEntity> latest(String stockID, int duration) 
	{
		duration++;
		String sql = "select top (" + duration + ") * from threeBig " +
				"where stockID = '" + stockID + "' order by YearMonth desc";
		System.out.println(sql);
		List <ThreeBigEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				BeanPropertyRowMapper.newInstance(ThreeBigEntity.class));
		return entityList;
	}

	@Override
	public int update(String indexName, String indexValue, String date, String stockID) {
		String sql = "UPDATE ThreeBig SET " + indexName + " = ? where yearMonth = '" + date + "' and stockID = '" + stockID + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}
	public long query(String stockID, Date date, String columnName, int addDay)
	{
		long data = 0;
		DateTimeFactory dateTimeFactory = new DateTimeFactory();
		date = dateTimeFactory.addMonth(date, addDay);
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date);
			String sql= "select " + columnName + " from threeBig " + 
			"where stockID = ? and yearMonth = ?";
			System.out.println(sql);
			data = (Long)getJdbcTemplate().queryForObject(
					sql, new Object[] {stockID, dateString}, Long.class);
		}
		catch (EmptyResultDataAccessException ex)
		{
			return 0;
		}
		catch (NullPointerException ex)
		{
			return 0;
		}
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }
		return data;
		
	}

}
