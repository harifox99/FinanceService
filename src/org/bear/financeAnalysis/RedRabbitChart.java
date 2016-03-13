package org.bear.financeAnalysis;
import java.util.List;
import org.bear.dao.JdbcRevenueDao;
import org.bear.entity.RedRabbitChartEntity;
import org.bear.entity.RevenueEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedRabbitChart 
{

	JdbcRevenueDao jdbcRevenueDao;
	final int fiveYears = 60;
	final int latestMonth = 6;
	final int year = 12;
	public RedRabbitChartEntity[] getRabbit(String stockID)
	{
		int dataLength;
		//總年份
		int totalYear = 0;
		//最後一年剩餘月份
		int reminderMonth = 0;
		RedRabbitChartEntity[] chartArray = new RedRabbitChartEntity[year];
		for (int i = 0; i < year; i++)
		{
			chartArray[i] = new RedRabbitChartEntity();
		}
		//List<RedRabbitChartEntity> chartEntity = new ArrayList<RedRabbitChartEntity>();
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
			jdbcRevenueDao = (JdbcRevenueDao)context.getBean("revenueDao");						
			List<RevenueEntity> entityList = jdbcRevenueDao.findByLatestSize(fiveYears + latestMonth, stockID);
			//至少擷取66個月的資料
			if (entityList.size() > fiveYears + latestMonth)
			{
				dataLength = fiveYears + latestMonth;
				totalYear = 5;
				reminderMonth = 0;
			}
			else
			{
				dataLength = entityList.size();
				totalYear = (dataLength-latestMonth)/year;
				reminderMonth = (dataLength-latestMonth)%year;
			}
			//畫過去的舊資料
			for (int i = 0; i < totalYear; i++)
			{
				for (int j = 1; j <= year; j++)
				{
					/*
					 *  2015/9	2015/10	 2015/11  2015/12  2016/1  2016/2
					 *	2014/9	2014/10	 2014/11  2014/12  2015/1  2015/2  2015/3  2015/4  2015/5  2015/6  2015/7  2015/8
                     *  2013/9	2013/10	 2013/11  2013/12  2014/1  2014/2  2014/3  2014/4  2014/5  2014/6  2014/7  2014/8
					 *			                                                           2013/5  2013/6  2013/7  2013/8 
					 *  index = (i+1)*year + latestMonth - j
					 *  index = 17, 2014/9; index = 16, 2014/10; index = 15, 2014/11
					 */
					int index = (i+1)*year + latestMonth - j;
				    String date = entityList.get(index).getYearMonth().toString();
				    String[] dateArray = date.split("-");
				    String month = dateArray[1];
				    if (month.startsWith("0"))
				    	month = month.substring(1);
				    //第N個月分資料就放第N-1個陣列，1月放第0個，5月放第4個，以此類推
					int revenue = entityList.get(index).getRevenue();
					switch (i)
					{
						case 0:
							chartArray[j-1].setLastYear(revenue);
							break;
						case 1:
							chartArray[j-1].setBeforeLastYear(revenue);
							break;
						case 2:
							chartArray[j-1].setThreeYearsAgo(revenue);	
							break;
						case 3:
							chartArray[j-1].setFourYearsAgo(revenue);
							break;
						case 4:
							chartArray[j-1].setFiveYearsAgo(revenue);
					}
					chartArray[j-1].setMonth(month);
				}				
			}
			//最近半年資料
			for (int i = 0; i < latestMonth; i++)
			{
				int revenue = entityList.get(latestMonth-1-i).getRevenue();
				chartArray[i].setLatestYear(revenue);
			}
			//因為總共要分析月營收66個月，若資料不足一年，會發生ArrayIndexOutOfBoundsException，因此要另外寫程式畫未滿一年的資料
			for (int i = 0; i < reminderMonth; i++)
			{
				int revenue = entityList.get(totalYear * year + latestMonth + i).getRevenue();
				if (totalYear == 1)
					chartArray[year-1-i].setBeforeLastYear(revenue);
				else if (totalYear == 2)
					chartArray[year-1-i].setThreeYearsAgo(revenue);
				else if (totalYear == 3)
					chartArray[year-1-i].setFourYearsAgo(revenue);
				else if (totalYear == 4)
					chartArray[year-1-i].setFiveYearsAgo(revenue);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return chartArray;
	}
}
