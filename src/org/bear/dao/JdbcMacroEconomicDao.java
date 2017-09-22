/**
 * 
 */
package org.bear.dao;
import java.text.SimpleDateFormat;
import java.util.*;
import org.bear.entity.MacroEconomicEntity;
//import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
/**
 * @author edward
 *
 */
public class JdbcMacroEconomicDao extends SimpleJdbcDaoSupport implements MacroEconomicDao {

	/* (non-Javadoc)
	 * @see org.bear.dao.MacroEconomicDao#findAll()
	 */
	public List<MacroEconomicEntity> findAll() 
	{
		List <MacroEconomicEntity> entityList = null;
		try
		{
			// TODO Auto-generated method stub
			String sql = "select * from macroEconomics";
			entityList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(MacroEconomicEntity.class));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return entityList;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.MacroEconomicDao#findByDate(java.sql.Date, java.sql.Date)
	 */
	public List<MacroEconomicEntity> findByDate(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		String stringStart, stringEnd;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		stringStart = format.format(startTime);
		stringEnd = format.format(endTime);
		String sql = "select * from macroEconomics where YearMonth >= '" + stringStart +
		"' and YearMonth <= '" + stringEnd + "'";
		System.out.println("SQL: " + sql);
		List <MacroEconomicEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				BeanPropertyRowMapper.newInstance(MacroEconomicEntity.class));
		for (int i = 0; i < entityList.size(); i++)
		{
			//將日期格式Class Date轉成Class String
			MacroEconomicEntity element = entityList.get(i);
			String newDate = element.getYear() + "-" + element.getMonth();
			element.setYear(newDate);
			//將半導體指數 * 10
			double newSeminIndex = Double.parseDouble(element.getSemiIndex()) * 10;
			element.setSemiIndex(String.valueOf(newSeminIndex));
			/*
			//劃撥餘額月增率
			if (i != 0)
			{
				double stockMoneyMoM = (element.getStockMoneyIndex()/lastStockMoneyIndex - 1) * 100;
				NumberFormat formatter = new DecimalFormat("##.##");
				element.setStockMoneyMoM(Double.parseDouble(formatter.format(stockMoneyMoM)));				
			}
			else
				element.setStockMoneyMoM(0);
				*/
			//證券劃撥餘額指數
			/* 暫時移除
			int stockMoneyNumber = (int) (element.getStockMoneyIndex()/100);
			element.setStockMoneyNumber(stockMoneyNumber);
			//劃撥餘額年增率
			double stockMoneyYoY = getStockMoneyYoY(element);
			element.setStockMoneyYoY(stockMoneyYoY);
			entityList.set(i, element);*/
			//用證券劃撥餘額來儲存(台股市值/m1b)
			int value = Integer.parseInt(element.getStockValue())/Integer.parseInt(element.getM1bTotalEnd());
			element.setStockMoneyNumber(value);
			element.setPbRatio(element.getPbRatio()*100);
		}
		return entityList;
	}
	/*
	private double getStockMoneyYoY(MacroEconomicEntity element)
	{
		String year = element.getYear().substring(0, 4);
		int intYear = Integer.parseInt(year);
		intYear -= 1;
		year = String.valueOf(intYear);
		String month = element.getMonth();
		String sql = "select * from macroEconomics where Year= '" + year +
		"' and month = '" + month + "'";
		List <MacroEconomicEntity> stockMoney = this.getSimpleJdbcTemplate().query(sql, 
				ParameterizedBeanPropertyRowMapper.newInstance(MacroEconomicEntity.class));
		double stockMoneyYoY = (element.getStockMoneyIndex()/stockMoney.get(0).getStockMoneyIndex() - 1) * 100;
		NumberFormat formatter = new DecimalFormat("##.##");
		stockMoneyYoY = Double.parseDouble(formatter.format(stockMoneyYoY));
		return stockMoneyYoY;
		
	}
	/* (non-Javadoc)
	 * @see org.bear.dao.MacroEconomicDao#insert(org.bear.entity.MacroEconomicEntity)
	 */
	public void insertBatch(List <MacroEconomicEntity> entity) {
		// TODO Auto-generated method stub
		String sql = "insert into macroEconomics(Year, Month, YearMonth, TwseOpen, TwseHigh, TwseLow, TwseClose, " +
				"M1bAverage, M1bEnd, M2Average, M2End, StockMoneyIndex, SixMonthLeadIndex, " +
				"GeneralIndex, InventoryIndex, OverworkTime, SemiIndex, LightSignal, DemandDeposits) " + 
				"values (:year, :month, :yearMonth, :twseOpen, :twseHigh, :twseLow, :twseClose, " +
				":m1bAverage, :m1bEnd, :m2Average, :m2End, :stockMoneyIndex, :sixMonthLeadIndex, " +
				":generalIndex, :inventoryIndex, :overworkTime, :semiIndex, :lightSignal, :demandDeposits)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (MacroEconomicEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}
		try
		{
			this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
		}
		catch (DataAccessException ex)
		{
			ex.printStackTrace();
		}
	}
	public String getMessage()
	{
		return "Successful;";
	}
	@Override
	public int update(String indexName, String indexValue, String date, String split) {
		// TODO Auto-generated method stub
		String[] dateArr = date.split(split);
		String sql = "UPDATE MacroEconomics SET " + indexName + " = ? where year = '" + dateArr[0] + 
		"' and month = '" + dateArr[1] + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}
	@Override
	public int update(String indexName, String indexValue, String date) {
		// TODO Auto-generated method stub
		String sql = "UPDATE MacroEconomics SET " + indexName + " = ? where yearMonth = '" + date + "'";
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}
}
