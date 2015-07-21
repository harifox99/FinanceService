package org.bear.dao;

import java.util.List;
import org.bear.entity.JuristicDailyEntity;
import org.bear.entity.ThreeBigExchangeEntity;

/**
 * 三大法人交易日報DAO
 * @author edward
 *
 */
public interface JuristicDailyReportDao {
	public void insert(JuristicDailyEntity entity); 
	public int update(String indexName, int indexValue, String date); 
	public List<JuristicDailyEntity> findLatestData(int size);
	public List<ThreeBigExchangeEntity> findSingleStock(String stockID, int size);
	public List<ThreeBigExchangeEntity> findTopSingleStock(String date, int rank);
	public List<ThreeBigExchangeEntity> findLastSingleStock(String date, int rank);
}
