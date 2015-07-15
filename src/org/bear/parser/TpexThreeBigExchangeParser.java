package org.bear.parser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.constant.FinancialReport;
import org.bear.entity.ThreeBigExchangeEntity;
import org.bear.util.StringUtil;
/**
 * 擷取櫃買三大法人買賣超資料
 * @author edward
 *
 */
public class TpexThreeBigExchangeParser extends TwseThreeBigExchangeParser 
{
	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);		
		int rankNumber = 0;		
		for (int i = 0; i < trList.size(); i++)
		{
			if (i < 2)
				continue;
			ThreeBigExchangeEntity entity = new ThreeBigExchangeEntity();
			rankNumber++;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			for (int j = 0; j < tdList.size(); j++)
			{	
				if (FinancialReport.maxNumber < rankNumber || tdList.size() < 5)			                    
					continue;										
				try
				{											
					resultElement = tdList.get(j);				
					String content = resultElement.getContent().toString().trim();	
					content = content.replace(",", "");
					if (j == 0)//Rank
					{
						entity.setRank(Integer.parseInt(content));
					}
					else if (j == 1)//Stock ID
					{
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						//把民國轉換成西元
						String[] dateArray = date.split("/");
						String year = StringUtil.convertYear(dateArray[0]);
						year = year + "/" + dateArray[1] + "/" + dateArray[2];
						Date dateClass = dateFormat.parse(year);
						entity.setExchangeDate(dateClass);
						entity.setStockID(content);
						entity.setExchanger(exchanger);						
						entity.setStockBranch(stockBranch);
					}
					else if (j == 5)//Quantity
					{
						int quantity = Integer.parseInt(content);						
						entity.setQuantity(quantity);
						entity.setRank(rankNumber);
						dao.insert(entity);						
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
		}			
	}
	public void parse()
	{		
		this.getTableContent(elementList.get(0));
	}
	
}
