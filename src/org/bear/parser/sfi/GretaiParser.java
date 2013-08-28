package org.bear.parser.sfi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.entity.RevenueEntity;
import org.bear.util.newRevenue.GetGretaiPrice;

public class GretaiParser extends ParserBase 
{
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 0)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String year = null;
			RevenueEntity entity = new RevenueEntity();
			for (int j = 0; j < tdList.size(); j++)
			{	
				resultElement = tdList.get(j);				
				String content = resultElement.getContent().toString().trim();				
				//琵计","ア
				content = content.replaceAll(",", "");
				try
				{
					if (j == 0)//
					{					
						year = org.bear.parser.ParserBase.convertYear(content);
						entity.setStockID(stockID);
					}
					else if (j == 1)//る
					{
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");						
						Date date = dateFormat.parse(year + "-" + content);
						entity.setYearMonth(date);
					}
					else if (j == 2)//程蔼基
					{
						entity.setHighIndex(content);
					}
					else if (j == 3)//程基
					{
						entity.setLowIndex(content);
					}
					else if (j == 4)//キАΜ絃基
					{
						entity.setAverageIndex(content);
					}
					else if (j == 8)//秅锣瞯
					{
						entity.setTurnoverRatio(content);
					}
					else
						continue;
				}			
				catch (Exception ex)
				{
					ex.printStackTrace();
				}			
				GetGretaiPrice getGretaiPrice = new GetGretaiPrice();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(entity.getYearMonth());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				String dateString = sdf.format(calendar.getTime());
				getGretaiPrice.getContent(stockID, dateString.substring(0, 4), 
						dateString.substring(5, dateString.length()), null, null);
			}
			dao.update(stockID, entity.getTurnoverRatio(), entity.getAverageIndex(), entity.getYearMonth());
		}
	}
}
