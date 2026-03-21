package org.bear.dao;
import java.util.List;
import org.bear.entity.StockDistributionEntity;

public interface StockDistributionDao 
{
	/**
	 * 新增
	 * @param entity
	 */
	public void insert(StockDistributionEntity entity);
	public void insert(StockDistributionEntity entity, String tableName);
	/**
	 * 查詢最新資料
	 * @param stockID
	 * @param duration
	 * @return
	 */
	public List <StockDistributionEntity> latest(String stockID, int duration);
	/**
	 * 查詢單筆資料
	 * @param stockID
	 * @param dateString
	 * @return
	 */
	public StockDistributionEntity query(String stockID, String dateString);
	/**
	 * Update
	 * @param entity
	 * @param stockID
	 * @param yearMonth
	 */
	public void update(StockDistributionEntity entity, String stockID, String yearMonth);
}
