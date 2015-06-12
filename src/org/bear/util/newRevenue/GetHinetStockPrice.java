package org.bear.util.newRevenue;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.dao.RevenueDao;
import org.bear.datainput.GetSFIContent;
import org.bear.entity.RevenueEntity;
import org.bear.util.GetURLContentBase;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Hinet更るユ戈
 * @author edward
 *
 */
public class GetHinetStockPrice extends GetURLContentBase implements GetSFIContent 
{
	RevenueDao dao;
	public GetHinetStockPrice()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (RevenueDao)context.getBean("revenueDao");
	}
	private void setUrlString(String stockID, String startYear, String startMonth, String endYear, String endMonth)
	{
		urlHeader = "http://money.hinet.net/z/z0/z00/z00a_";
		urlString = urlHeader + stockID + "_" + startYear + "-" + startMonth + "-1_" + endYear + "-" + endMonth + "-30_M.djhtm";
		System.out.println(urlString);
	}
	@Override
	public void getContent(String stockID, String stockName, String startYear,
			String startMonth, String endYear, String endMonth) {
		// TODO Auto-generated method stub
		this.setUrlString(stockID, startYear, startMonth, endYear, endMonth);
		List<Element> elementList = super.getContent();
		if (elementList == null)
			return;
		List<Element> trList = elementList.get(1).getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 0 || i == 1 || i == trList.size() - 1)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;			
			RevenueEntity entity = new RevenueEntity();
			for (int j = 0; j < tdList.size(); j++)
			{	
				try
				{											
					resultElement = tdList.get(j);				
					String content = resultElement.getContent().toString().trim();								
					if (j == 0)//ら戳
					{
						String[] dateArray = content.split("/");
						String year = StringUtil.convertYear(dateArray[0]);
						String month = dateArray[1];
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");						
						Date date = dateFormat.parse(year + "-" + month);
						entity.setYearMonth(date);
						entity.setStockID(stockID);
					}
					else if (j == 1)//秨絃基
					{
						entity.setOpenIndex(content);
					}
					else if (j == 2)//程蔼基
					{
						entity.setHighIndex(content);
					}
					else if (j == 3)//程基
					{
						entity.setLowIndex(content);
					}
					else if (j == 4)//Μ絃基
					{
						entity.setCloseIndex(content);
					}
					else
						continue;
				}			
				catch (Exception ex)
				{
					ex.printStackTrace();
				}				
			}
			dao.updatePrice(stockID, entity);
		}
	}		

}
