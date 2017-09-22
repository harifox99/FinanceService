/**
 * 
 */
package org.bear.dao;

import java.util.ArrayList;
import java.util.List;

import org.bear.entity.IncomeStatementEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author edward
 *
 */
public class JdbcIncomeStatementDao extends SimpleJdbcDaoSupport implements
		IncomeStatementDao {

	/* (non-Javadoc)
	 * @see org.bear.dao.IncomeStatementDao#findDataBySeason(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<IncomeStatementEntity> findDataBySeason(String stockID, String year, String seasons) 
	{
		// TODO Auto-generated method stub
		List <IncomeStatementEntity> wrapperList = null;
		String sql = "select * from incomeStatement where (seasons >= " + seasons + ") and (year >= " + year +
		") and (stockID = '" + stockID + "') and (seasons <> '00') " + 
		"or (year > " + year + ") and (stockID = '" + stockID + "') and (seasons <> '00') order by Year";
		System.out.println("findDataBySeason: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		return wrapperList;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.IncomeStatementDao#insert(org.bear.entity.IncomeStatementEntity)
	 */
	public void insert(IncomeStatementEntity incomeStatementEntity) {
		// TODO Auto-generated method stub
		String sql = "insert into incomeStatement(StockID, Year, Seasons, OperatingRevenue, OperatingCost, " + 
		"GrossProfit, OperatingExpense, OperatingIncome, InvestmentIncome, NonOperatingRevenue, " +
		"NonOperatingExpense, PreTaxIncome, NetIncome, EPS, WghtAvgStocks) " +
		"values (:stockID, :year, :seasons, :operatingRevenue, :operatingCost, :grossProfit, " + 
		":operatingExpense, :operatingIncome, :investmentIncome, :nonOperatingRevenue, " +
		":nonOperatingExpense, :preTaxIncome, :netIncome, :eps, :wghtAvgStocks)";
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(incomeStatementEntity);
		this.getSimpleJdbcTemplate().update(sql, parameterSource);
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.IncomeStatementDao#insertBatch(java.util.List)
	 */
	public void insertBatch(List<IncomeStatementEntity> entity) {
		// TODO Auto-generated method stub
		String sql = "insert into incomeStatement(StockID, Year, Seasons, OperatingRevenue, OperatingCost, " + 
		"GrossProfit, OperatingExpense, OperatingIncome, InvestmentIncome, NonOperatingRevenue, " +
		"NonOperatingExpense, PreTaxIncome, NetIncome, EPS, WghtAvgStocks) " +
		"values (:stockID, :year, :seasons, :operatingRevenue, :operatingCost, :grossProfit, " + 
		":operatingExpense, :operatingIncome, :investmentIncome, :nonOperatingRevenue, " +
		":nonOperatingExpense, :preTaxIncome, :netIncome, :eps, :wghtAvgStocks)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (IncomeStatementEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}

	public List<IncomeStatementEntity> findDataByYear(String stockID, String year) 
	{
		// TODO Auto-generated method stub
		List <IncomeStatementEntity> wrapperList = null;
		String sql = "select * from incomeStatement where seasons = '00' and year >= " + year +
		" and stockID = '" + stockID + "' order by Year";
		System.out.println("findDataByYear: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		return wrapperList;
	}

	@Override
	public List<IncomeStatementEntity> findDataByYear(String stockID) {
		// TODO Auto-generated method stub
		List <IncomeStatementEntity> wrapperList = null;
		String sql = "select * from incomeStatement where seasons = '00'" + 
		" and stockID = '" + stockID + "' order by Year";
		System.out.println("findDataByYear: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		return wrapperList;
	}

	@Override
	public void insertWithCheck(IncomeStatementEntity incomeStatementEntity) {
		String sql = "select * from incomeStatement where stockid = '" + incomeStatementEntity.getStockID() +
		"' and year = '" + incomeStatementEntity.getYear() + "' and seasons = '" + incomeStatementEntity.getSeasons() + "'";
		System.out.println(sql);
		List <IncomeStatementEntity> wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		if (wrapperList.size() <= 0)
		{
			sql = "insert into incomeStatement(StockID, Year, Seasons, OperatingRevenue, OperatingCost, " + 
			"GrossProfit, OperatingExpense, OperatingIncome, InvestmentIncome, NonOperatingRevenue, " +
			"NonOperatingExpense, PreTaxIncome, NetIncome, EPS, WghtAvgStocks) " +
			"values (:stockID, :year, :seasons, :operatingRevenue, :operatingCost, :grossProfit, " + 
			":operatingExpense, :operatingIncome, :investmentIncome, :nonOperatingRevenue, " +
			":nonOperatingExpense, :preTaxIncome, :netIncome, :eps, :wghtAvgStocks)";
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(incomeStatementEntity);
			this.getSimpleJdbcTemplate().update(sql, parameterSource);
		}
		
	}

	@Override
	public List<IncomeStatementEntity> findDataByLatest(int size, String stockID) {
		// TODO Auto-generated method stub
		List <IncomeStatementEntity> wrapperList = null;
		String sql = "select top " + size + " * from incomeStatement where stockID = '" +
		stockID + "' ORDER BY Year DESC, Seasons DESC";
		//System.out.println("SQL: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		return wrapperList;
	}

	@Override
	public IncomeStatementEntity findSingleDataBySeason(String stockID,
			String year, String seasons) {
		// TODO Auto-generated method stub
		String sql = "select * from incomeStatement where stockID = '" + stockID + "' and year = '" + year + "' and seasons = '" + seasons + "'";
		IncomeStatementEntity entity = this.getSimpleJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		return entity;
	}

	@Override
	/**
	 * 查詢最近num年，損益資料
	 */
	public List<IncomeStatementEntity> findDataByLatestYear(int size,
			String stockID) {
		List <IncomeStatementEntity> wrapperList = null;
		String sql = "select top " + size + " * from incomeStatement where stockID = '" +
		stockID + "' and seasons = '00' order by YEAR desc";
		//System.out.println("SQL: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		return wrapperList;
	}

}
