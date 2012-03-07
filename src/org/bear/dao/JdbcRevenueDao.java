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
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcRevenueDao extends SimpleJdbcDaoSupport implements RevenueDao {

	public List<RevenueIncreaseWrapper> findAllRevenueIncrease(String stockID, Date startTime, Date endTime) 
	{
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
			String sql = "select * from operatingRevenue where YearMonth >= '" + stringStart +
			"' and YearMonth <= '" + stringEnd + "' and stockID = '" + stockID + "' order by YearMonth";
			System.out.println("Sql Cmd:" + sql);
			entityList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(RevenueEntity.class));
			Iterator <RevenueEntity> iterator = entityList.iterator();     
			while(iterator.hasNext())
			{
				RevenueIncreaseWrapper wrapper = new RevenueConveter().converter(iterator.next(), mapLastMonthRevenue);
				if (wrapper != null)
					wrapperList.add(wrapper);
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
				ParameterizedBeanPropertyRowMapper.newInstance(RevenueEntity.class));
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
		List <RevenueEntity> entityList = this.getSimpleJdbcTemplate().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(RevenueEntity.class));
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
		System.out.println("SQL: " + sql);
		List <RevenueEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				ParameterizedBeanPropertyRowMapper.newInstance(RevenueEntity.class));
		return entityList;
	}
}
