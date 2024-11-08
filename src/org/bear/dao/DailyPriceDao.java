package org.bear.dao;
import java.util.List;
import org.bear.entity.DailyPriceEntity;
public interface DailyPriceDao
{
	//每日交易資訊
	public void insertBatch(List<DailyPriceEntity> entity);
}
