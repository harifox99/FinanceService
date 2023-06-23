package org.bear.dao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bear.converter.RevenueConveter;
import org.bear.entity.LongTermRevenueWrapper;
import org.bear.entity.RevenueEntity;
import org.bear.entity.RevenueIncreaseWrapper;
import org.bear.util.StringUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;


public class JdbcRevenueDao extends SimpleJdbcDaoSupport implements RevenueDao {

	public List<RevenueIncreaseWrapper> findAllRevenueIncrease(String stockID, Date startTime, Date endTime) 
	{
		//計算年度累計營收需要24個月
		final int accumulationMonth = 24;
		List <RevenueEntity> entityList = null;
		List <RevenueIncreaseWrapper> wrapperList = new ArrayList <RevenueIncreaseWrapper>();
		try
		{
			HashMap <String, Integer> mapLastMonthRevenue = new HashMap <String, Integer>();
			// TODO Auto-generated method stub
			String stringStart, stringEnd;
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			stringStart = format.format(startTime);
			stringEnd = format.format(endTime);
			stringStart = this.recombine(stringStart);
			String sql = "select * from operatingRevenue where YearMonth >= '" + stringStart +
			"' and YearMonth <= '" + stringEnd + "' and stockID = '" + stockID + "' order by YearMonth";
			System.out.println("Sql Cmd:" + sql);
			entityList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(RevenueEntity.class));
			Iterator <RevenueEntity> iterator = entityList.iterator();   
			int loopIndex = -1;
			List<Integer> listYearAccumu = new ArrayList<Integer>();
			
			while(iterator.hasNext())
			{
				System.out.println("loopIndex" + ++loopIndex);
				RevenueEntity entity = iterator.next();
				RevenueIncreaseWrapper wrapper = new RevenueConveter().converter(entity, mapLastMonthRevenue);
				//計算年度累計營收
				if (wrapper == null)
					continue;
				listYearAccumu.add(entity.getRevenue());
				if (loopIndex < accumulationMonth)
				{					
					double rate = (double)entity.getAccumulation()/entity.getLastAccumulation();
					rate = rate - 1;
					rate = rate * 100;
					rate = StringUtil.setPointLength(rate);
					wrapper.setAccumulationOneYear(rate);
				}
				else
				{
					int yearAccumu = 0;
					int lastYearAccumu = 0;
					for (int i = 0; i < listYearAccumu.size(); i++)
					{
						if (i < (accumulationMonth/2))
							lastYearAccumu += listYearAccumu.get(i);
						else
							yearAccumu += listYearAccumu.get(i);
					}
					double rate = (double)yearAccumu/lastYearAccumu;
					rate = rate - 1;
					rate = rate * 100;
					rate = StringUtil.setPointLength(rate);
					wrapper.setAccumulationOneYear(rate);
					listYearAccumu.remove(0);
				}
				if (wrapper != null)
					wrapperList.add(wrapper);
			}
			for (int i = 0; i < accumulationMonth; i++)
			{
				wrapperList.remove(0);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return wrapperList;
	}

	public List<RevenueEntity> findByDate(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		String stringStart, stringEnd;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		stringStart = format.format(startTime);
		stringEnd = format.format(endTime);
		String sql = "select * from operatingRevenue where YearMonth >= '" + stringStart +
		"' and YearMonth <= '" + stringEnd + "'";
		System.out.println("SQL: " + sql);
		List <RevenueEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				BeanPropertyRowMapper.newInstance(RevenueEntity.class));
		return entityList;
	}

	public void insertBatch(List<RevenueEntity> entity) 
	{
		String sql = "insert into operatingRevenue(StockID, YearMonth, OpenIndex, HighIndex, LowIndex, CloseIndex, AverageIndex, " +
		"Revenue, LastRevenue, RevenueMoM, Accumulation, LastAccumulation, TurnoverRatio) " + 
		"values (:stockID, :yearMonth, :openIndex, :highIndex, :lowIndex, :closeIndex, :averageIndex, " +
		":revenue, :lastRevenue, :revenueMoM, :accumulation, :lastAccumulation, :turnoverRatio)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (RevenueEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}
	public List<LongTermRevenueWrapper> findLongTermByDate(String stockID, Date startTime, Date endTime)
	{
		List <LongTermRevenueWrapper> list = new ArrayList <LongTermRevenueWrapper>();
		// TODO Auto-generated method stub
		String stringStart, stringEnd;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		stringStart = format.format(startTime);
		stringEnd = format.format(endTime);
		String sql = "select * from operatingRevenue where YearMonth >= '" + stringStart +
		"' and YearMonth <= '" + stringEnd + "' and stockID = '" + stockID + "' order by YearMonth";
		System.out.println("Sql Cmd:" + sql);
		List <RevenueEntity> entityList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(RevenueEntity.class));
		int revenueAvergae[] = new int[entityList.size()];
		int threeAverage;
		int twelveAverage;
		for (int i = 0; i < entityList.size(); i++)
		{
			LongTermRevenueWrapper wrapper = new LongTermRevenueWrapper();
			revenueAvergae[i] = entityList.get(i).getRevenue();
			if (i < 2)
			{
				wrapper.setThreeAverage(0);
				wrapper.setTwelveAverage(0);
				wrapper.setDifference(0);
			}
			else if (i >= 2 && i < 11)
			{
				threeAverage = revenueAvergae[i - 2] + revenueAvergae[i - 1] + revenueAvergae[i];
				threeAverage /= 3;
				wrapper.setTwelveAverage(0);
				wrapper.setThreeAverage(threeAverage);
				wrapper.setDifference(threeAverage);
			}
			else
			{
				threeAverage = revenueAvergae[i - 2] + revenueAvergae[i - 1] + revenueAvergae[i];
				threeAverage /= 3;
				wrapper.setThreeAverage(threeAverage);
				twelveAverage = 0;
				for (int j = 0; j < 12; j++)
					twelveAverage += revenueAvergae[i - j];
				twelveAverage /= 12;
				wrapper.setTwelveAverage(twelveAverage);
				wrapper.setDifference(threeAverage - twelveAverage);
			}
			wrapper.setCloseIndex(entityList.get(i).getCloseIndex());
			wrapper.setHighIndex(entityList.get(i).getHighIndex());
			wrapper.setOpenIndex(entityList.get(i).getOpenIndex());
			wrapper.setLowIndex(entityList.get(i).getLowIndex());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM"); 
			wrapper.setYearMonth(dateFormat.format(entityList.get(i).getYearMonth()));
			if (wrapper != null)
				list.add(wrapper);
		}
		return list;
	}

	public List<RevenueEntity> findAllData(String stockID, String year) {
		// TODO Auto-generated method stub
		String sql = "select * from operatingRevenue where stockID = '" + stockID + 
		"' and (DATEPART(year, YearMonth) = '" + year + "') order by YearMonth";
		//System.out.println("SQL: " + sql);
		List <RevenueEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				BeanPropertyRowMapper.newInstance(RevenueEntity.class));
		return entityList;
	}
	/**
	 * 起始 年份減兩年
	 * @param startYear
	 * @return
	 */
	private String recombine(String startYear)
	{
		String dateUnit[] = startYear.split("/");
		String year = dateUnit[0];
		int intYear = Integer.parseInt(year);
		intYear = intYear - 2;
		startYear = String.valueOf(intYear) + "/" + dateUnit[1] + "/" + dateUnit[2];
		return startYear;		
	}
	/**
	 * 請參考Interface的說明
	 */
	@Override
	public List<RevenueEntity> findByLatestSize(int size, String stockID) {
		String sql = "select top " + size + " * from operatingRevenue where stockID = '" +
		stockID + "' order by yearMonth desc";
		//System.out.println("SQL: " + sql);
		List <RevenueEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				BeanPropertyRowMapper.newInstance(RevenueEntity.class));
		return entityList;
	}
	public void update(String stockID, RevenueEntity entity)
	{
		Date date = entity.getYearMonth();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		String dateString = dateFormat.format(date);
		String sql = "update OperatingRevenue set revenue = '" + entity.getRevenue() + "', lastRevenue = '" + entity.getLastRevenue() +
		"', accumulation = '" + entity.getAccumulation() + "', lastAccumulation = '" + entity.getLastAccumulation() +
		"' where stockID = '" + stockID + "' and yearMonth = '" + dateString + "'";
		this.getSimpleJdbcTemplate().update(sql);
	}
	public void update(String stockID, String turnoverRatio, String averageIndex, Date date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormat.format(date);
		String sql = "update OperatingRevenue set turnoverRatio = '" + turnoverRatio + "', averageIndex = '" +
					averageIndex + "' where stockID = '" + stockID + "' and yearMonth = '" + dateString + "'";
		this.getSimpleJdbcTemplate().update(sql);
	}
	public int update(String indexName, String indexValue, Date date, String stockID) {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormat.format(date);
		String sql = "update OperatingRevenue SET " + indexName + " = ? where yearMonth = '" + dateString + "' and StockID = '" + stockID + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}

	@Override
	public void updatePrice(String stockID, RevenueEntity entity) {
		Date date = entity.getYearMonth();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		String dateString = dateFormat.format(date);
		String sql = "update OperatingRevenue set openIndex = '" + entity.getOpenIndex() + "', highIndex = '" + entity.getHighIndex() +
		"', lowIndex = '" + entity.getLowIndex() + "', closeIndex = '" + entity.getCloseIndex() + "', averageIndex = '" + entity.getAverageIndex() +
		"', turnoverRatio = '" + entity.getTurnoverRatio() +
		"' where stockID = '" + stockID + "' and yearMonth = '" + dateString + "'";
		this.getSimpleJdbcTemplate().update(sql);
		
	}

	@Override
	public int update(String sql) {
		// TODO Auto-generated method stub
		int result = this.getSimpleJdbcTemplate().update(sql);
		return result;
	}

	@Override
	public List<RevenueEntity> findByLatestMergeSize(int size, String stockID) {
		String sql = "select top " + (size+1) + " * from operatingRevenue where stockID = '" +
		stockID + "' order by yearMonth desc";
		List <RevenueEntity> entityMergeList = new ArrayList<RevenueEntity>();
		List <RevenueEntity> newList = new ArrayList<RevenueEntity>();
		List <RevenueEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				BeanPropertyRowMapper.newInstance(RevenueEntity.class));
		for (int i = 0; i < entityList.size(); i++)
		{
			String dateString = entityList.get(i).getYearMonth().toString().substring(5, 7);
			//把二月營收取代成累計營收
			if (dateString.contains("02"))
			{
				RevenueEntity entity = entityList.get(i);
				entity.setRevenue((int)entityList.get(i).getAccumulation());
				entity.setLastRevenue((int)entityList.get(i).getLastAccumulation());
				entityMergeList.add(entity);
			}
			//忽略一月營收 (還是要顯示1月營收)
			/*
			else if (dateString.contains("01"))
			{
				continue;
			}*/
			//除此之外，保持原資料
			else
			{
				entityMergeList.add(entityList.get(i));
			}
		}
		//若完全沒有一二月資料，則刪去最舊一筆
		for (int i = 0; i < size; i++)
		{
			newList.add(entityMergeList.get(i));
		}
		return newList;
	}
	/**
	 * 僅篩選特定日期之營收，通常是因為在8號或是9號，資料不足，又想早一步篩選資料來分析（天下武功，無堅不摧，唯快不破）
	 */
	@Override
	public List<RevenueEntity> findBySpecificDate(String stockID, String year, String month) 
	{
		String sql = "select * from operatingRevenue where stockID = '" +
		stockID + "' and (DATEPART(year, YearMonth) = '" + year + "') AND (DATEPART(month, YearMonth) = '" + month + "')" + 
				" order by yearMonth desc";
		System.out.println("SQL: " + sql);
		List <RevenueEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				BeanPropertyRowMapper.newInstance(RevenueEntity.class));
		return entityList;
	}
	/**
	 * 僅篩選特定日期之營收，通常是因為在8號或是9號，資料不足，又想早一步篩選資料來分析（天下武功，無堅不摧，唯快不破）
	 */
	@Override
	public List<String> findBySpecificDate(String year, String month) 
	{
		String sql = "select stockid from operatingRevenue where " +
	    " (DATEPART(year, YearMonth) = '" + year + "') AND (DATEPART(month, YearMonth) = '" + month + "')" + 
				" order by yearMonth desc";
		System.out.println("SQL: " + sql);
		List<String> data = (List<String>)getJdbcTemplate().queryForList(sql, String.class);
		return data;
	}

	@Override
	public List<String> findBySpecificReport(String year, String seasons) 
	{
		//String sql = "select stockid from balanceSheet where year = '" + year + "'" + " and seasons = '" + seasons + "'";
		String sql = "select stockid from balanceSheet where year = ? and seasons = ?";
		//System.out.println(sql);
		List<String> data = (List<String>)getJdbcTemplate().queryForList(sql, String.class, year, seasons);
		return data;
	}
}
