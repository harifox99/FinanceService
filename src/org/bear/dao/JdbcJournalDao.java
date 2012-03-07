/**
 * 
 */
package org.bear.dao;

import java.util.List;
import org.bear.journal.wrapper.JournalEntity;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author edward
 *
 */
public class JdbcJournalDao extends SimpleJdbcDaoSupport implements JournalDao {

	/* (non-Javadoc)
	 * @see org.bear.dao.JournalDao#findJournal(java.lang.String)
	 */
	public JournalEntity findJournal(String transactionID, int serialNo) {
		// TODO Auto-generated method stub
		String sql = "select * from journal where transactionID = '" + transactionID + "' and serialNo = '" + serialNo + "'";
		JournalEntity entity = this.getSimpleJdbcTemplate().queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(JournalEntity.class));
		return entity;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.JournalDao#findJournalList(java.lang.String)
	 */
	public List<JournalEntity> findJournalList(String status) {
		// TODO Auto-generated method stub
		List <JournalEntity> wrapperList = null;
		String sql = "select * from journal where status = ?";
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(JournalEntity.class), status);
		//Iterator <BasicStockWrapper> iterator = wrapperList.iterator();
		return wrapperList;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.JournalDao#insert(org.bear.journal.wrapper.JournalEntity)
	 */
	public void insert(JournalEntity entity) {
		// TODO Auto-generated method stub
		String sql = "insert into journal (TransactionID, SerialNo, Date, Investment, Category, Signal, Comment, Status) " +
					 "values (:transactionID, :serialNo, :date, :investment, :category, :signal, :comment, :status)";
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
		this.getSimpleJdbcTemplate().update(sql, parameterSource);
	}
	public void closeTransaction(String transactionID, int serialNo)
	{
		String sql = "update journal set status = 'C' where transactionID = '" + transactionID + "' and serialNo = '" + serialNo + "'";
		this.getSimpleJdbcTemplate().update(sql);
	}
}
