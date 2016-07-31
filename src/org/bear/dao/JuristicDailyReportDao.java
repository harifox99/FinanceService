package org.bear.dao;

import java.util.Date;
import java.util.List;
import org.bear.entity.JuristicDailyEntity;
import org.bear.entity.RetailInvestorsEntity;
import org.bear.entity.ThreeBigExchangeEntity;

/**
 * 三大法人交易日報DAO
 * @author edward
 *
 */
public interface JuristicDailyReportDao {
	public void insert(JuristicDailyEntity entity); 
	public int update(String indexName, int indexValue, String date); 
	public int update(String indexName, double indexValue, String date); 
	public List<JuristicDailyEntity> findLatestData(int size);
	public JuristicDailyEntity findByDate(Date exchangeDate);
	public List<ThreeBigExchangeEntity> findStockBySize(String stockID, int size);
	public List<ThreeBigExchangeEntity> findTopSingleStock(String date, int rank);
	public List<ThreeBigExchangeEntity> findStockByDate(String date, String stockID);
	public void insert(RetailInvestorsEntity entity);
	public int update(String tableName, String indexName, int indexValue, String date);
	public List<RetailInvestorsEntity> findRetailInvestors(int size);
}
