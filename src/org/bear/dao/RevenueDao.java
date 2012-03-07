package org.bear.dao;
import java.util.Date;
import java.util.List;
import org.bear.entity.LongTermRevenueWrapper;
import org.bear.entity.RevenueEntity;
import org.bear.entity.RevenueIncreaseWrapper;

public interface RevenueDao 
{
	//新增營收資料
	public void insertBatch(List <RevenueEntity> entity);
	//列舉個股營收
	public List<RevenueIncreaseWrapper> findAllRevenueIncrease(String stockID, Date startTime, Date endTime);
	//列舉特定期間營收資料
	public List<RevenueEntity> findByDate(Date startTime, Date endTime);
	//列舉長短期營收變動
	public List<LongTermRevenueWrapper> findLongTermByDate(String stockID, Date startTime, Date endTime);
	//傳回特定年份營收資料
	public List<RevenueEntity> findAllData(String stockID, String year);
}