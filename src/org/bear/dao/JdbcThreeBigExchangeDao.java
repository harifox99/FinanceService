package org.bear.dao;

import java.util.List;
import org.bear.entity.ThreeBigExchangeEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcThreeBigExchangeDao extends SimpleJdbcDaoSupport implements ThreeBigExchangeDao{

	@Override
	public void insert(ThreeBigExchangeEntity entity) {
		try
		{
			String sql = "insert into ThreeBigExchange" + "" +
			"(StockID, ExchangeDate, Quantity, StockBranch, Rank, Exchanger) values " +
			"(:stockID, :exchangeDate, :quantity, :stockBranch, :rank, :exchanger)";
			//System.out.println("sql: " + sql);
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
			this.getSimpleJdbcTemplate().update(sql, parameterSource);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Insert ThreeBigExchangeEntity Error!");
		}

	}

	@Override
	public List<ThreeBigExchangeEntity> latest(String stockID, int duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ThreeBigExchangeEntity> latest() {
		// TODO Auto-generated method stub
		return null;
	}

}
