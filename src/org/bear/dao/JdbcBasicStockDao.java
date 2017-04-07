package org.bear.dao;

import java.util.ArrayList;
import java.util.List;
import org.bear.entity.BasicStockWrapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcBasicStockDao extends SimpleJdbcDaoSupport implements BasicStockDao {

	public List<BasicStockWrapper> findAllData() {
		// TODO Auto-generated method stub
		List <BasicStockWrapper> wrapperList = null;
		String sql = "select * from StockData where enabled <> 0";
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BasicStockWrapper.class));
		//Iterator <BasicStockWrapper> iterator = wrapperList.iterator();
		return wrapperList;
	}

	public void insertBatch(List<BasicStockWrapper> entity) {
		// TODO Auto-generated method stub
		String sql = "insert into StockData(StockID, StockName, StockType, StockBranch, Enabled) " + 
		"values (:stockID, :stockName, :stockType, :stockBranch, :enabled)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (BasicStockWrapper iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}

	@Override
	public List<BasicStockWrapper> findStockTypeData(String stockBranch) {
		// TODO Auto-generated method stub
		List <BasicStockWrapper> wrapperList = null;
		String sql = "select * from StockData where enabled <> 0 and StockType <> 17 and stockBranch = '" + stockBranch + "'";
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BasicStockWrapper.class));
		//Iterator <BasicStockWrapper> iterator = wrapperList.iterator();
		return wrapperList;
	}

	@Override
	public void updateCapital(String stockID, String capital) {
		// TODO Auto-generated method stub
		String sql = "update StockData set capital = '" + capital +
		"' where stockID = '" + stockID + "'";
		this.getSimpleJdbcTemplate().update(sql);
	}

	@Override
	public BasicStockWrapper findBasicData(String stockID) {
		// TODO Auto-generated method stub
		try
		{
			String sql = "select * from StockData where stockID = ?";
			BasicStockWrapper entity = this.getSimpleJdbcTemplate().queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(BasicStockWrapper.class), stockID);
			return entity;
		}
		catch (Exception ex)
		{
			return null;
		}
	}

}
