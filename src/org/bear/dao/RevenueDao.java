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
	//列舉過去N年的累計營收
	public List<RevenueEntity> findByLatestSize(int size, String stockID);
	//列舉長短期營收變動
	public List<LongTermRevenueWrapper> findLongTermByDate(String stockID, Date startTime, Date endTime);
	//傳回特定年份營收資料
	public List<RevenueEntity> findAllData(String stockID, String year);
	public void update(String stockID, RevenueEntity entity);
	public void updatePrice(String stockID, RevenueEntity entity);
	public void update(String stockID, String turnoverRatio, String averageIndex, Date date);
	public int update(String indexName, String indexValue, Date date, String stockID);
	public int update(String sql);
	//一二月營收合併顯示
	public List<RevenueEntity> findByLatestMergeSize(int size, String stockID);
	//僅篩選符合特殊日期之營收
	public List<RevenueEntity> findBySpecificDate(String stockID, String year, String month);
	//僅篩選符合特殊日期之營收
	public List<String> findBySpecificDate(String year, String month);
}