package org.bear.util;

import org.bear.dao.RevenueDao;
import org.bear.datainput.ImportStockID;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 把2013年的去年同期營收貼到2012年的當期營收
 * @author edward
 *
 */
public class UpdateRevenue extends ImportStockID
{
	String date[] = {"01-01", "02-01", "03-01", "04-01", "05-01", "06-01",
					 "07-01", "08-01", "09-01", "10-01", "11-01", "12-01"};
	RevenueDao dao;
	public static void main(String args[])
	{
		new UpdateRevenue().update();
	}
	public void update()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (RevenueDao)context.getBean("revenueDao");
		for (int j = 1495; j < wrapperList.size(); j++)
		{
			for (int k = 0; k < date.length; k++)
			{				
				String stockID = wrapperList.get(j).getStockID();
				String sql = "Update OperatingRevenue set Revenue = " +
                             "(Select LastRevenue from operatingRevenue " +
                             "WHERE StockID = " + stockID + " AND YearMonth = '2013-" + date[k] + "') " +
                             "WHERE (StockID = '" + stockID + "') AND (YearMonth = '2012-" + date[k] + "')";
				System.out.println(sql);
				dao.update(sql);
			}			
		}
	}
}
