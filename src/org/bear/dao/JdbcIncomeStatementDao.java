/**
 * 
 */
package org.bear.dao;

import java.util.ArrayList;
import java.util.List;
import org.bear.entity.IncomeStatementEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
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
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		return wrapperList;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.IncomeStatementDao#insert(org.bear.entity.IncomeStatementEntity)
	 */
	public void insert(IncomeStatementEntity balanceSheetEntity) {
		// TODO Auto-generated method stub
		String sql = "insert into incomeStatement(StockID, Year, Seasons, OperatingRevenue, OperatingCost, " + 
		"GrossProfit, OperatingExpense, OperatingIncome, InvestmentIncome, NonOperatingRevenue, " +
		"NonOperatingExpense, PreTaxIncome, NetIncome, EPS, WghtAvgStocks) " +
		"values (:stockID, :year, :seasons, :operatingRevenue, :operatingCost, :grossProfit, " + 
		":operatingExpense, :operatingIncome, :investmentIncome, :nonOperatingRevenue, " +
		":nonOperatingExpense, :preTaxIncome, :netIncome, :eps, :wghtAvgStocks)";
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(balanceSheetEntity);
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
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		return wrapperList;
	}

	@Override
	public List<IncomeStatementEntity> findDataByYear(String stockID) {
		// TODO Auto-generated method stub
		List <IncomeStatementEntity> wrapperList = null;
		String sql = "select * from incomeStatement where seasons = '00'" + 
		" and stockID = '" + stockID + "' order by Year";
		System.out.println("findDataByYear: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(IncomeStatementEntity.class));
		return wrapperList;
	}

}
