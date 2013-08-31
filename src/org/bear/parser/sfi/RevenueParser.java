package org.bear.parser.sfi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.entity.RevenueEntity;

public class RevenueParser extends ParserBase
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
				resultElement = tdList.get(j).getFirstElement(HTMLElementName.SPAN);				
				String content = resultElement.getContent().toString().trim();				
				//讓數字的","消失
				content = content.replaceAll(",", "");
				try
				{
					if (j == 0)//年
					{
						year = content;
						entity.setStockID(stockID);
					}
					else if (j == 1)//月
					{
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");						
						Date date = dateFormat.parse(year + "-" + content);
						entity.setYearMonth(date);
					}
					else if (j == 2)//盈餘
					{
						if (content.equals("0"))
							entity.setRevenue(0);
						else
							entity.setRevenue(Integer.parseInt(content.substring(0, content.length()-3)));
					}
					else if (j == 3)//上期盈餘
					{
						if (content.equals("0"))
							entity.setLastRevenue(0);
						else
							entity.setLastRevenue(Integer.parseInt(content.substring(0, content.length()-3)));
					}
					else if (j == 6)//累計營收
					{
						if (content.equals("0"))
							entity.setAccumulation(0);
						else
							entity.setAccumulation(Long.parseLong(content.substring(0, content.length()-3)));
					}
					else if (j == 7)//上期累計營收
					{
						if (content.equals("0"))
							entity.setLastAccumulation(0);
						else
							entity.setLastAccumulation(Long.parseLong(content.substring(0, content.length()-3)));
					}
					else
						continue;
				}			
				catch (Exception ex)
				{
					ex.printStackTrace();
				}				
			}
			dao.update(stockID, entity);
		}
	}
}
