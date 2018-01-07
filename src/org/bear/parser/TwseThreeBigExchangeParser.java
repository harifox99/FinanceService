package org.bear.parser;
import java.text.SimpleDateFormat;
import java.util.List;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.entity.*;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 擷取三大法人買賣超個股資料Parser
 * @author edward
 *
 */
public class TwseThreeBigExchangeParser extends EasyParserBase
{	
	ThreeBigExchangeDao dao;	
	String date;
	int stockBranch;
	String exchanger;	
	public ThreeBigExchangeDao getDao() {
		return dao;
	}

	public void setDao(ThreeBigExchangeDao dao) {
		this.dao = dao;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStockBranch() {
		return stockBranch;
	}

	public void setStockBranch(int stockBranch) {
		this.stockBranch = stockBranch;
	}

	public String getExchanger() {
		return exchanger;
	}

	public void setExchanger(String exchanger) {
		this.exchanger = exchanger;
	}

	@Override
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
			//兩大=外資+投信
			ThreeBigExchangeEntity twoBig  = new ThreeBigExchangeEntity();
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			int twoBigQuantity = 0;
			for (int j = 0; j < tdList.size(); j++)
			{	
				try
				{											
					resultElement = tdList.get(j);				
					String content = resultElement.getContent().toString().trim();	
					content = content.replace(",", "");
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");				
					if (j == 0)//Stock ID
					{
						foreigner.setStockID(content);
						foreigner.setStockBranch(1);
						foreigner.setRank(0);						
						foreigner.setExchangeDate(dateFormat.parse(date));
						mutualFund.setStockID(content);
						mutualFund.setStockBranch(1);
						mutualFund.setRank(0);
						mutualFund.setExchangeDate(dateFormat.parse(date));
						twoBig.setStockID(content);
						twoBig.setStockBranch(1);
						twoBig.setRank(0);						
						twoBig.setExchangeDate(dateFormat.parse(date));						
					}
					else if (j == 4)//外資買賣超
					{
						//股數->張數
						int quantity = Integer.parseInt(content);
						foreigner.setQuantity(quantity/1000);
						foreigner.setExchanger("外資");						
						dao.insert(foreigner);
						twoBigQuantity = quantity/1000;
					}
					else if (j == 10)//投信買賣超
					{
						//股數->張數
						int quantity = Integer.parseInt(content);
						mutualFund.setQuantity(quantity/1000);
						mutualFund.setExchanger("投信");
						dao.insert(mutualFund);
						//兩大
						twoBig.setQuantity(quantity/1000 + twoBigQuantity);
						twoBig.setExchanger("兩大");
						dao.insert(twoBig);
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
		}			
	}
	public void parse(int index)
	{		
		this.getTableContent(elementList.get(index));
	}
}
