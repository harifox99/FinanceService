package org.bear.dao;
import java.util.ArrayList;
import java.util.List;
import org.bear.entity.DailyPriceEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcDailyPriceDao extends SimpleJdbcDaoSupport implements DailyPriceDao
{
	@Override
	public void insertBatch(List<DailyPriceEntity> entity) 
	{
		String sql = "insert into DailyPrice(ExchangeDate, StockId, OpenPrice, ClosePrice, HighPrice, LowPrice, Volume, ExchangeNum, TurnoverRate) " + 
		"values (:exchangeDate, :stockId, :openPrice, :closePrice, :highPrice, :lowPrice, :volume, :exchangeNum, :turnoverRate)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (DailyPriceEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}

}
