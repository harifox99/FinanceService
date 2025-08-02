package org.bear.dao;

import org.bear.entity.PyramidDistributionEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcPyramidDistributionDao extends SimpleJdbcDaoSupport implements PyramidDistributionDao {

	@Override
	public void insert(PyramidDistributionEntity entity) 
	{
		String sql = "insert into PyramidDistribution(StockID, YearMonth, " +
				"P1, P1000, P5000, P10000, P15000, P20000, P30000, P40000, P50000, P100000, " +
				"P200000, P400000, P600000, P800000, P1000000) values " +
				"(:stockID, :yearMonth, " +
				":p1, :p1000, :p5000, :p10000, :p15000, :p20000, :p30000, :p40000, :p50000, :p100000, " +
				":p200000, :p400000, :p600000, :p800000, :p1000000)";
				//System.out.println("sql: " + sql);
				SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
				this.getSimpleJdbcTemplate().update(sql, parameterSource);
	}

}
