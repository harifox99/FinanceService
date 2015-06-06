package org.bear.parser.sfi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.entity.RevenueEntity;
/**
 * Parse證券期貨發展基金會的上市公司成交價格資訊
 * @author edward
 *
 */
public class PriceParser extends RevenueParserBase
{
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		String openIndex = "";
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
					else if (j == 5)//最高
					{
						entity.setHighIndex(content);
					}
					else if (j == 7)//最低
					{
						entity.setLowIndex(content);
					}
					else if (j == 10)//收盤
					{
						entity.setCloseIndex(content);						
						entity.setOpenIndex(openIndex);
						openIndex = content;
						//第一筆不紀錄，僅將本月收盤價當作下一個月開盤價	
						if (i != 1)
						{
							//entityList.add(entity);
							dao.updatePrice(stockID, entity);
						}
					}
					else
						continue;
				}			
				catch (Exception ex)
				{
					ex.printStackTrace();
				}				
			}			
		}
		//dao.insertBatch(entityList);
	}
}
