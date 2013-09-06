/**
 * 
 */
package org.bear.dao;

import java.util.ArrayList;
import java.util.List;
import org.bear.entity.FinancialDataEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author edward
 *
 */
public class JdbcFinancialDataDao extends SimpleJdbcDaoSupport implements FinancialDataDao {

	/* (non-Javadoc)
	 * @see org.bear.dao.FinancialDataDao#findDataBySeason(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<FinancialDataEntity> findDataBySeason(String stockID,
			String year, String seasons) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.FinancialDataDao#findDataByYear(java.lang.String, java.lang.String)
	 */
	public List<FinancialDataEntity> findDataByYear(String stockID, String year) {
		// TODO Auto-generated method stub
		List <FinancialDataEntity> wrapperList = null;
		String sql = "select * from financialData where seasons = '00' and year >= " + year +
		" and stockID = '" + stockID + "' order by Year";
		System.out.println("findDataByYear: " + sql);
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(FinancialDataEntity.class));
		return wrapperList;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.FinancialDataDao#insert(org.bear.entity.FinancialDataEntity)
	 */
	public void insert(FinancialDataEntity financialEntity) {
		String sql = "insert into financialData(StockID, Year, Seasons, NAV, CashDiv) " +
		"values (:stockID, :year, :seasons, :nav, :cashDiv)";
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(financialEntity);
		this.getSimpleJdbcTemplate().update(sql, parameterSource);
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.FinancialDataDao#insertBatch(java.util.List)
	 */
	public void insertBatch(List<FinancialDataEntity> entity) {
		// TODO Auto-generated method stub
		String sql = "insert into financialData(StockID, Year, Seasons, NAV, CashDiv) " +
		"values (:stockID, :year, :seasons, :nav, :cashDiv)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (FinancialDataEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}

	@Override
	public void insertWithCheck(FinancialDataEntity financialEntity) {
		String sql = "select * from financialData where stockid = '" + financialEntity.getStockID() +
		"' and year = '" + financialEntity.getYear() + "' and seasons = '" + financialEntity.getSeasons() + "'";
		System.out.println(sql);
		List <FinancialDataEntity> wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(FinancialDataEntity.class));
		if (wrapperList.size() <= 0)
		{
			sql = "insert into financialData(StockID, Year, Seasons, NAV, CashDiv) " +
			"values (:stockID, :year, :seasons, :nav, :cashDiv)";
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(financialEntity);
			this.getSimpleJdbcTemplate().update(sql, parameterSource);
		}
	}

}
