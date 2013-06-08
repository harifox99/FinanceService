package org.bear.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bear.entity.AmericanMacroPantherEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcAmericanMacroPantherDao extends SimpleJdbcDaoSupport implements
		AmericanMacroPantherDao {

	public List<AmericanMacroPantherEntity> findAll() {
		List <AmericanMacroPantherEntity> entityList = null;
		try
		{
			// TODO Auto-generated method stub
			String sql = "select * from americanMacroPanther";
			entityList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(AmericanMacroPantherEntity.class));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return entityList;
	}

	public List<AmericanMacroPantherEntity> findByDate(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		String stringStart, stringEnd;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		stringStart = format.format(startTime);
		stringEnd = format.format(endTime);
		String sql = "select * from americanMacroPanther where YearMonth >= '" + stringStart +
		"' and YearMonth <= '" + stringEnd + "'";
		System.out.println("SQL: " + sql);
		List <AmericanMacroPantherEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				ParameterizedBeanPropertyRowMapper.newInstance(AmericanMacroPantherEntity.class));
		return entityList;
	}

	public void insertBatch(List<AmericanMacroPantherEntity> entity) {
		String sql = "insert into americanMacroPanther(Year, Date, YearMonth, " +
		"INDPRORate, RSAFS, HOUST, DGOrder, NEWORDER, PERMITNSA, " +
		"INDPRO, PAYEMS, UNRATE, ISRatio, AWOTMAN, UMCSENT, ICSA, " +
		"M1, MZM, TB3MS, GS10, BAA, CFP3M, CPI, CRB, " +
		"SP500, SPOpen, SPHigh, SPLow, SPClose) " +
		"values (:year, :date, :yearMonth, " +
		":indproRate, :rsafs, :houst, :dgOrder, :neworder, :permitnsa, " +
		":indpro, :payems, :unrate, :isRatio, :awotman, :umcsent, :icsa, " +
		":m1, :mzm, :tb3ms, :gs10, :baa, :cfp3m, :cpi, :crb, " +
		":sp500, :spOpen, :spHigh, :spLow, :spClose) ";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (AmericanMacroPantherEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}		
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));

	}

	@Override
	public int update(String indexName, String indexValue, String date) {
		// TODO Auto-generated method stub
		String sql = "UPDATE AmericanMacroPanther SET " + indexName + " = ? where date = '" + date + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}

}
