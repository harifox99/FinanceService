package org.bear.util.newRevenue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.dao.RevenueDao;
import org.bear.datainput.GetSFIContent;
import org.bear.entity.RevenueEntity;
import org.bear.parser.ParserBase;
import org.bear.util.GetURLContentBase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetTwseIndividualIndex extends GetURLContentBase implements GetSFIContent 
{	
	RevenueDao dao;
	public GetTwseIndividualIndex()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (RevenueDao)context.getBean("revenueDao");
	}
	private void setUrlString(String stockID, String year)
	{
		urlHeader = "http://www.twse.com.tw/ch/trading/exchange/FMSRFK/genpage/Report";
		String timeStamp = year + new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
		urlString = urlHeader + timeStamp + "/";
		urlString = urlString + year + "_F3_1_10_" + stockID + ".php?STK_NO=" + stockID + "&myear=" + year;
	}
	@Override
	public void getContent(String stockID, String startYear, String startMonth,
			String endYear, String endMonth) 
	{
		this.setUrlString(stockID, startYear);
		List<Element> elementList = super.getContent();
		if (elementList == null)
			return;
		List<Element> trList = elementList.get(7).getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 0 || i == 1)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String year = null;
			RevenueEntity entity = new RevenueEntity();
			for (int j = 0; j < tdList.size(); j++)
			{	
				try
				{						
					resultElement = tdList.get(j);		
					String content = null;													
					if (j == 0)//年
					{
						resultElement = tdList.get(j).getFirstElement(HTMLElementName.DIV);
						content = resultElement.getContent().toString().trim();						
						year = ParserBase.convertYear(content);
						entity.setStockID(stockID);
					}
					else if (j == 1)//月
					{
						resultElement = tdList.get(j).getFirstElement(HTMLElementName.DIV);
						content = resultElement.getContent().toString().trim();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");						
						Date date = dateFormat.parse(year + "-" + content);
						entity.setYearMonth(date);
					}
					else if (j == 4)//平均收盤價
					{
						content = resultElement.getContent().toString().trim();		
						//讓數字的","消失
						content = content.replaceAll(",", "");	
						entity.setAverageIndex(content);
					}
					else if (j == 8)//週轉率
					{
						content = resultElement.getContent().toString().trim();		
						//讓數字的","消失
						content = content.replaceAll(",", "");	
						entity.setTurnoverRatio(content);
					}
					else
						continue;
				}			
				catch (Exception ex)
				{
					ex.printStackTrace();
				}				
			}
			dao.update(stockID, entity.getTurnoverRatio(), entity.getAverageIndex(), entity.getYearMonth());
		}
	}

}
