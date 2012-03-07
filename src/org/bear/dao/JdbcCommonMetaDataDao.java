/**
 * 
 */
package org.bear.dao;

import java.util.List;
import org.bear.journal.wrapper.CommonJournalMetaWrapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author edward
 *
 */
public class JdbcCommonMetaDataDao extends SimpleJdbcDaoSupport implements CommonMetaDataDao {

	/* (non-Javadoc)
	 * @see org.bear.dao.CommonMetaDataDao#getAppoachList()
	 */
	public List<CommonJournalMetaWrapper> getAppoachList() {
		// TODO Auto-generated method stub
		List <CommonJournalMetaWrapper> wrapperList = null;
		String sql = "select * from approach";
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(CommonJournalMetaWrapper.class));
		return wrapperList;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.CommonMetaDataDao#getReasonList()
	 */
	public List<CommonJournalMetaWrapper> getReasonList() {
		// TODO Auto-generated method stub
		List <CommonJournalMetaWrapper> wrapperList = null;
		String sql = "select * from transaction_reason";
		wrapperList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(CommonJournalMetaWrapper.class));
		return wrapperList;
	}

}
