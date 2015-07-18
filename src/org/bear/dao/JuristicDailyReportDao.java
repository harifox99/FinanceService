package org.bear.dao;

import java.util.List;
import org.bear.entity.JuristicDailyEntity;

/**
 * 三大法人交易日報DAO
 * @author edward
 *
 */
public interface JuristicDailyReportDao {
	public void insert(JuristicDailyEntity entity); 
	public int update(String indexName, int indexValue, String date); 
	public List<JuristicDailyEntity> findLatestData(int size);
}
