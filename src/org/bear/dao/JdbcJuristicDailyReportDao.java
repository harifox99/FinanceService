package org.bear.dao;
import java.util.List;
import org.bear.entity.JuristicDailyEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcJuristicDailyReportDao extends SimpleJdbcDaoSupport implements JuristicDailyReportDao {

	@Override
	public void insert(JuristicDailyEntity entity) {
		// TODO Auto-generated method stub
		try
		{
			String sql = "insert into JuristicDailyReport" + "" +
			"(Amount, ExchangeDate) values " +
			"(:amount, :exchangeDate)";
			//System.out.println("sql: " + sql);
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
			this.getSimpleJdbcTemplate().update(sql, parameterSource);
		}
		catch (Exception ex)
		{
			//ex.printStackTrace();
			System.out.println("Insert Error!");
		}
	}

	@Override
	public int update(String indexName, int indexValue, String date) {
		// TODO Auto-generated method stub
		String sql = "UPDATE JuristicDailyReport SET " + indexName + " = ? where Exchangedate = '" + date + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}

	@Override
	public List<JuristicDailyEntity> findLatestData(int size) {
		List <JuristicDailyEntity> entityList = null;
		String sql = "select top " + size + " * from JuristicDailyReport ORDER BY ExchangeDate desc";
		//System.out.println("SQL: " + sql);
		entityList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(JuristicDailyEntity.class));
		return entityList;
	}
	
	

}
