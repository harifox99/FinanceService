/**
 * 
 */
package org.bear.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bear.entity.PMIndexEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author edward
 *
 */
public class JdbcPMIndexDao extends SimpleJdbcDaoSupport implements PMIndexDao {

	/* (non-Javadoc)
	 * @see org.bear.dao.PMIndexDao#findAll()
	 */
	public List<PMIndexEntity> findAll() {
		List <PMIndexEntity> entityList = null;
		try
		{
			// TODO Auto-generated method stub
			String sql = "select * from pmIndex";
			entityList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(PMIndexEntity.class));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return entityList;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.PMIndexDao#findByDate(java.util.Date, java.util.Date)
	 */
	public List<PMIndexEntity> findByDate(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		String stringStart, stringEnd;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		stringStart = format.format(startTime);
		stringEnd = format.format(endTime);
		String sql = "select * from pmIndex where YearMonth >= '" + stringStart +
		"' and YearMonth <= '" + stringEnd + "'";
		System.out.println("SQL: " + sql);
		List <PMIndexEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				BeanPropertyRowMapper.newInstance(PMIndexEntity.class));
		return entityList;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.PMIndexDao#insertBatch(java.util.List)
	 */
	public void insertBatch(List<PMIndexEntity> entity) {
		// TODO Auto-generated method stub
		String sql = "insert into pmIndex(Year, Date, YearMonth, SPOpen, SPHigh, SPLow, SPClose, " +
		"NewOrders, Production, Employment, Deliveries, Inventories, PMI, Baseline) " +
		"values (:year, :date, :yearMonth, :spOpen, :spHigh, :spLow, :spClose, " +
		":newOrders, :production, :employment, :deliveries, :inventories, :pmi, :baseline)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (PMIndexEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}		
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));

	}

	@Override
	public int update(String indexName, String indexValue, String date) {
		// TODO Auto-generated method stub
		String sql = "UPDATE PMIndex SET " + indexName + " = ? where date = '" + date + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}
}
