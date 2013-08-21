package org.bear.parser.sfi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.entity.RevenueEntity;

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
				//讓數字的","消失
				content = content.replaceAll(",", "");
				try
				{
					if (j == 0)//年
					{					
						year = org.bear.parser.ParserBase.convertYear(content);
						entity.setStockID(stockID);
					}
					else if (j == 1)//月
					{
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");						
						Date date = dateFormat.parse(year + "-" + content);
						entity.setYearMonth(date);
					}
					else if (j == 4)//平均收盤價
					{
						entity.setAverageIndex(content);
					}
					else if (j == 8)//週轉率
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
			}
			dao.update(stockID, entity.getTurnoverRatio(), entity.getAverageIndex(), entity.getYearMonth());
		}
	}
}
