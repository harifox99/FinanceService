package org.bear.dao;
import java.util.List;
import org.bear.entity.StockType;
import org.bear.entity.StockTypeName;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcStockTypeDao extends SimpleJdbcDaoSupport implements StockTypeDao 
{	
	@Override
	public List<Integer> findAllData() {
		// TODO Auto-generated method stub
		String sql = "select typeId from stockType";
		List<Integer> data = (List<Integer>)getJdbcTemplate().queryForList(sql, Integer.class);
		return data;
	}

	@Override
	public List<StockType> findFullData() {
		String sql = "select * from stockType";
		List<StockType> data = 
		this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(StockType.class));
		return data;
	}

	@Override
	public List<StockTypeName> getStockTypeName() 
	{
		String sql = "select stockdata.StockID, StockType.Typename from stockdata inner join StockType on stockdata.StockType = StockType.Typeid";
		List<StockTypeName> listStockType = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(StockTypeName.class));
		return listStockType;
	}


}
