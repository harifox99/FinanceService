package org.bear.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bear.entity.AmericanMacroEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcAmericanMacroDao extends SimpleJdbcDaoSupport implements
		AmericanMacroDao {

	public List<AmericanMacroEntity> findAll() {
		List <AmericanMacroEntity> entityList = null;
		try
		{
			// TODO Auto-generated method stub
			String sql = "select * from americanMacro";
			entityList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(AmericanMacroEntity.class));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return entityList;
	}

	public List<AmericanMacroEntity> findByDate(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		String stringStart, stringEnd;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		stringStart = format.format(startTime);
		stringEnd = format.format(endTime);
		String sql = "select * from americanMacro where YearMonth >= '" + stringStart +
		"' and YearMonth <= '" + stringEnd + "'";
		System.out.println("SQL: " + sql);
		List <AmericanMacroEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				ParameterizedBeanPropertyRowMapper.newInstance(AmericanMacroEntity.class));
		return entityList;
	}

	public void insertBatch(List<AmericanMacroEntity> entity) {
		String sql = "insert into americanMacro(Year, Date, YearMonth, SPOpen, SPHigh, SPLow, SPClose, " +
		"NFP, INDPRO, INDPRORate, DGOrder, TCU, TCURate, ISRatio, AWHMAN, AWOTMAN, UNRATE, UMCSENT, ICSA, HOUST, " +
		"CPIAUCSL, CRB, PERMITNSA, NEWORDER, GS10, GS3M, BAA, M2, MZM, SP500, RSXFS) " +
		"values (:year, :date, :yearMonth, :spOpen, :spHigh, :spLow, :spClose, " +
		":nfp, :indpro, :indproRate, :dgOrder, :tcu, :tcuRate, :isRatio, :awhman, :awotman, :unrate, :umcsent, :icsa, :houst, " +
		":cpiaucsl, :crb, :permitnsa, :neworder, :gs10, :gs3m, :baa, :m2, :mzm, :sp500, :rsxfs) ";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (AmericanMacroEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}		
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}

}
