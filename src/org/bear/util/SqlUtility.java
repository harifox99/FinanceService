package org.bear.util;
import java.util.List;
import org.bear.dao.CommonDao;
import org.bear.datainput.GetSFIContent;
import org.bear.datainput.ImportPriceSFI;
import org.bear.util.newRevenue.GetMopsRevenue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 查詢跨月營收資料
 * @author bear
 *
 */
public class SqlUtility
{
	String sql;
	public String getSql() 
	{
		return sql;
	}
	public void setSql(String date) 
	{
		this.sql = "(select stockid from StockData where Enabled = 1) except " + 
				   "(select stockid from OperatingRevenue where YearMonth = '" + date + "')";
	}
	public List<String> saveAllRevenue(String sql)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		CommonDao dao = (CommonDao)context.getBean("commomDao");
		List<String> dataList = dao.connectSql(sql);
		return dataList;
	}
	
	public static void main(String[] args)
	{
		String[] dateList = {"2018-07-01"};
		/*
		String[] dateList = {
				 "2022-01-01", "2022-02-01", "2022-03-01", "2022-04-01", "2022-05-01", "2022-06-01", 
				 "2022-07-01", "2022-08-01", "2022-09-01", "2022-10-01", "2022-11-01", "2022-12-01"};*/
		for (int i = 0; i < dateList.length; i++)
		{
			SqlUtility utility = new SqlUtility();
			utility.setSql(dateList[i]);
			List<String> dataList = utility.saveAllRevenue(utility.getSql());
			GetSFIContent getContent;
			ImportPriceSFI sfi = new ImportPriceSFI();
			/* 上市/上櫃營收資訊 (公開資訊觀測站)，僅用startYear and startMonth */
			getContent = new GetMopsRevenue();
			String[] yearMonth = dateList[i].split("-");
			sfi.insertBatchList(yearMonth[0], StringUtil.addZeroMonth(yearMonth[1]), yearMonth[0], yearMonth[1], getContent, dataList);
		}
	}
}
