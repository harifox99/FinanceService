package org.bear.parser;
import java.text.SimpleDateFormat;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.entity.ThreeBigExchangeEntity;
/**
 * 擷取櫃買三大法人買賣超個股資料
 * @author edward
 *
 */
public class TpexThreeBigExchangeParser extends TwseThreeBigExchangeParser 
{
	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);		
		for (int i = 0; i < trList.size(); i++)
		{
			if (i < 2)
				continue;
			//外資
			ThreeBigExchangeEntity foreigner = new ThreeBigExchangeEntity();
			//投信
			ThreeBigExchangeEntity mutualFund  = new ThreeBigExchangeEntity();
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			for (int j = 0; j < tdList.size(); j++)
			{	
				try
				{											
					resultElement = tdList.get(j);				
					String content = resultElement.getContent().toString().trim();	
					content = content.replace(",", "");
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					if (j == 0)//Stock ID
					{
						foreigner.setStockID(content);
						foreigner.setStockBranch(this.getStockBranch());
						foreigner.setRank(0);						
						foreigner.setExchangeDate(dateFormat.parse(date));
						mutualFund.setStockID(content);
						mutualFund.setStockBranch(this.getStockBranch());
						mutualFund.setRank(0);
						mutualFund.setExchangeDate(dateFormat.parse(date));
					}
					else if (j == 4)//外資買賣超
					{
						//股數->張數
						int quantity = Integer.parseInt(content);
						foreigner.setQuantity(quantity/1000);
						foreigner.setExchanger("外資");
						dao.insert(foreigner);
					}
					else if (j == 7)//投信買賣超
					{
						//股數->張數
						int quantity = Integer.parseInt(content);
						mutualFund.setQuantity(quantity/1000);
						mutualFund.setExchanger("投信");
						dao.insert(mutualFund);
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
