package org.bear.dao;

import java.util.ArrayList;
import java.util.List;
import org.bear.entity.CashFlowsEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcCashFlowsDao extends SimpleJdbcDaoSupport implements CashFlowsDao {

	public List<CashFlowsEntity> findDataBySeason(String stockID, String year, String seasons)
	{
		// TODO Auto-generated method stub
		List <CashFlowsEntity> wrapperList = null;
		String sql = "select * from statementOfCashFlow where (seasons >= " + seasons + ") and (year >= " + year +
		") and (stockID = '" + stockID + "') and (seasons <> '00') " + 
		"or (year > " + year + ") and (stockID = '" + stockID + "') and (seasons <> '00') order by Year";
		System.out.println("findDataBySeason: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(CashFlowsEntity.class));
		//Iterator <BasicStockWrapper> iterator = wrapperList.iterator();
		return wrapperList;
	}

	public void insertBatch(List<CashFlowsEntity> entity) 
	{
		// TODO Auto-generated method stub
		String sql = "insert into StatementOfCashFlow(StockID, Year, Seasons, IncomeSummary, OperatingActivity, " + 
		"InvestingActivity, FinancingActivity, NetCashFlows, BeginningCash, EndingCash, FreeCashFlow) " +
		"values (:stockID, :year, :seasons, :incomeSummary, :operatingActivity, " + 
		":investingActivity, :financingActivity, :netCashFlows, :beginningCash, :endingCash, :freeCashFlow)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (CashFlowsEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}
	public void insert(CashFlowsEntity cashFlowsEntity)
	{
		String sql = "insert into StatementOfCashFlow(StockID, Year, Seasons, IncomeSummary, OperatingActivity, " + 
		"InvestingActivity, FinancingActivity, NetCashFlows, BeginningCash, EndingCash, FreeCashFlow, CapEx, CapRe) " +
		"values (:stockID, :year, :seasons, :incomeSummary, :operatingActivity, " + 
		":investingActivity, :financingActivity, :netCashFlows, :beginningCash, :endingCash, :freeCashFlow, :capEx, :capRe)";
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cashFlowsEntity);
		this.getSimpleJdbcTemplate().update(sql, parameterSource);
	}
	public List<CashFlowsEntity> findDataByYear(String stockID,
			String year) {
		// TODO Auto-generated method stub
		List <CashFlowsEntity> wrapperList = null;
		String sql = "select * from StatementOfCashFlow where seasons = '00' and year >= " + year +
		" and stockID = '" + stockID + "' order by Year";
		System.out.println("findDataByYear: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(CashFlowsEntity.class));
		return wrapperList;
	}

	@Override
	public void insertWithCheck(CashFlowsEntity cashFlowsEntity) {
		String sql = "select * from StatementOfCashFlow where stockid = '" + cashFlowsEntity.getStockID() +
		"' and year = '" + cashFlowsEntity.getYear() + "' and seasons = '" + cashFlowsEntity.getSeasons() + "'";
		System.out.println(sql);
		List <CashFlowsEntity> wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(CashFlowsEntity.class));
		if (wrapperList.size() <= 0)
		{
			sql = "insert into StatementOfCashFlow(StockID, Year, Seasons, IncomeSummary, OperatingActivity, " + 
			"InvestingActivity, FinancingActivity, NetCashFlows, BeginningCash, EndingCash, FreeCashFlow) " +
			"values (:stockID, :year, :seasons, :incomeSummary, :operatingActivity, " + 
			":investingActivity, :financingActivity, :netCashFlows, :beginningCash, :endingCash, :freeCashFlow)";
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cashFlowsEntity);
			this.getSimpleJdbcTemplate().update(sql, parameterSource);
		}		
	}

	@Override
	/**
	 * 查詢最近num季，自由現金流
	 * 
	 */
	public List<CashFlowsEntity> findLatest(String stockID, int num) 
	{
		// TODO Auto-generated method stub
		List <CashFlowsEntity> wrapperList = null;
		String sql = "select top (" + num + ") * from StatementOfCashFlow where stockid = '" + 
		stockID + "' and seasons <> '00' order by year desc, seasons desc";
		wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(CashFlowsEntity.class));
		return wrapperList;
	}
}
