package org.bear.parser.sfi;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.bear.entity.RevenueEntity;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * Parse公開資訊觀測站的上市/上櫃公司的營收資料
 * @author edward
 *
 */
public class MopsRevenueParser extends RevenueParserBase {
	String year;
	String month;
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		RevenueEntity entity = new RevenueEntity();
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 0)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			
			for (int j = 0; j < tdList.size(); j++)
			{	
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();				
				//讓數字的","消失				
				content = content.replaceAll("&nbsp;", "");
				content = content.replaceAll(",", "");
				content = content.trim();
				try
				{
					if (j == 0)//資料
					{
						if (i == 1)		
						{
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");						
							Date date = dateFormat.parse(year + "-" + month);
							entity.setYearMonth(date);
							entity.setStockID(stockID);
							entity.setRevenue(Integer.parseInt(content));
						}
						else if (i == 2)
							entity.setLastRevenue(Integer.parseInt(content));
						else if (i == 5)
							entity.setAccumulation(Long.parseLong(content));
						else if (i == 6)
							entity.setLastAccumulation(Long.parseLong(content));
						else
							continue;
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
		entityList.add(entity);	
		dao.insertBatch(entityList);
	}
}
