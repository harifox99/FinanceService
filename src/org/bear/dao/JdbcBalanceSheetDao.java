/**
 * 
 */
package org.bear.dao;

import java.util.ArrayList;
import java.util.List;

import org.bear.entity.BalanceSheetEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author edward
 *
 */
public class JdbcBalanceSheetDao extends SimpleJdbcDaoSupport implements
		BalanceSheetDao {

	/* (non-Javadoc)
	 * @see org.bear.dao.BalanceSheetDao#insert(org.bear.entity.BalanceSheetEntity)
	 */
	public void insert(BalanceSheetEntity balanceSheetEntity) {
		// TODO Auto-generated method stub
		String sql = "insert into BalanceSheet(StockID, Year, Seasons, Cash, ShortTermInvestment, " + 
		"Receivable, OtherReceivable, ShortTermBorrowing, Inventory, PrepaidExpense, OtherCurrentAssets, " +
		"CurrentAssets, LongTermInvestment, FixedAssets, OtherAssets, TotalAssets, LongTermOneYear, " +
		"CurrentLiability, LongTermLiability, OtherLiability, TotalLiability, StockholdersEquity, AccountsPayable) " +
		"values (:stockID, :year, :seasons, :cash, :shortTermInvestment, :receivable, :otherReceivable, " + 
		":shortTermBorrow, :inventory, :prepaidExpense, :otherCurrentAssets, :currentAssets, :longTermInvestment, " +
		":fixedAssets, :otherAssets, :totalAssets, :longTermOneYear, :currentLiability, " +
		":longTermLiability, :otherLiability, :totalLiability, :stockholdersEquity, :accountsPayable)";
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(balanceSheetEntity);
		this.getSimpleJdbcTemplate().update(sql, parameterSource);
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.BalanceSheetDao#insertBatch(java.util.List)
	 */
	public void insertBatch(List<BalanceSheetEntity> entity) {
		// TODO Auto-generated method stub
		String sql = "insert into BalanceSheet(StockID, Year, Seasons, Cash, ShortTermInvestment, " + 
		"Receivable, OtherReceivable, ShortTermBorrowing, Inventory, PrepaidExpense, OtherCurrentAssets, " +
		"CurrentAssets, LongTermInvestment, FixedAssets, OtherAssets, TotalAssets, LongTermOneYear, " +
		"CurrentLiability, LongTermLiability, OtherLiability, TotalLiability, StockholdersEquity) " +
		"values (:stockID, :year, :seasons, :cash, :shortTermInvestment, :receivable, :otherReceivable, " + 
		":shortTermBorrow, :inventory, :prepaidExpense, :otherCurrentAssets, :currentAssets, " +
		":longTermInvestment, :fixedAssets, :otherAssets, :totalAssets, :longTermOneYear, " +
		":currentLiability, :longTermLiability, :otherLiability, :totalLiability, :stockholdersEquity)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (BalanceSheetEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}

	public List<BalanceSheetEntity> findDataBySeason(String stockID,
			String year, String seasons) {
		// TODO Auto-generated method stub
		List <BalanceSheetEntity> wrapperList = null;
		String sql = "select * from balanceSheet where (seasons >= " + seasons + ") and (year >= " + year +
		") and (stockID = '" + stockID + "') and (seasons <> '00') " + 
		"or (year > " + year + ") and (stockID = '" + stockID + "') and (seasons <> '00') order by Year";
		System.out.println("findDataBySeason: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BalanceSheetEntity.class));
		return wrapperList;
	}

	public List<BalanceSheetEntity> findDataByYear(String stockID,
			String year) {
		// TODO Auto-generated method stub
		List <BalanceSheetEntity> wrapperList = null;
		String sql = "select * from BalanceSheet where seasons = '00' and year >= " + year +
		" and stockID = '" + stockID + "' order by Year";
		System.out.println("findDataByYear: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BalanceSheetEntity.class));
		return wrapperList;
	}

	public List<BalanceSheetEntity> findDataByYear(String stockID) 
	{
		List <BalanceSheetEntity> wrapperList = null;
		String sql = "select * from BalanceSheet where seasons = '00'" + 
		" and stockID = '" + stockID + "' order by Year";
		System.out.println("findDataByYear: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BalanceSheetEntity.class));
		return wrapperList;
	}

	@Override
	public void insertWithCheck(BalanceSheetEntity balanceSheetEntity) {
		String sql = "select * from BalanceSheet where stockid = '" + balanceSheetEntity.getStockID() +
		"' and year = '" + balanceSheetEntity.getYear() + "' and seasons = '" + balanceSheetEntity.getSeasons() + "'";
		System.out.println(sql);
		List <BalanceSheetEntity> wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BalanceSheetEntity.class));
		if (wrapperList.size() <= 0)
		{
			sql = "insert into BalanceSheet(StockID, Year, Seasons, Cash, ShortTermInvestment, " + 
			"Receivable, OtherReceivable, ShortTermBorrowing, Inventory, PrepaidExpense, OtherCurrentAssets, " +
			"CurrentAssets, LongTermInvestment, FixedAssets, OtherAssets, TotalAssets, LongTermOneYear, " +
			"CurrentLiability, LongTermLiability, OtherLiability, TotalLiability, StockholdersEquity, AccountsPayable) " +
			"values (:stockID, :year, :seasons, :cash, :shortTermInvestment, :receivable, :otherReceivable, " + 
			":shortTermBorrow, :inventory, :prepaidExpense, :otherCurrentAssets, :currentAssets, :longTermInvestment, " +
			":fixedAssets, :otherAssets, :totalAssets, :longTermOneYear, :currentLiability, " +
			":longTermLiability, :otherLiability, :totalLiability, :stockholdersEquity, :accountsPayable)";
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(balanceSheetEntity);
			this.getSimpleJdbcTemplate().update(sql, parameterSource);
		}		
	}

}
