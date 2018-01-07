package org.bear.parser;
import java.util.List;
/**
 * 擷取證交所、集保的買賣超排名
 */
import org.bear.dao.ThreeBigExchangeDao;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
public class RankingParser extends EasyParserBase 
{
	ThreeBigExchangeDao threeBigExchangeDao;
	String date;
	String buyer;
	String sqlDate;
	final int rankSize = 100;
	public ThreeBigExchangeDao getThreeBigExchangeDao() {
		return threeBigExchangeDao;
	}

	public void setThreeBigExchangeDao(ThreeBigExchangeDao threeBigExchangeDao) {
		this.threeBigExchangeDao = threeBigExchangeDao;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
	public String getSqlDate() {
		return sqlDate;
	}

	public void setSqlDate(String sqlDate) {
		this.sqlDate = sqlDate;
	}

	@Override
	public void getTableContent(Element element) 
	{
		int rank = 0;
 		List<Element> trList = element.getAllElements(HTMLElementName.TR);		
		for (int i = 0; i < trList.size(); i++)
		{
			//只要前30名
			if (i < 2 || i > rankSize + 1)
				continue;
			rank++;
 			Element trElement = trList.get(i);
 			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
 			for (int j = 0; j < tdList.size(); j++)
			{	
				try
				{		
 					if (j == 1)
					{
 						resultElement = tdList.get(j);				
						String content = resultElement.getContent().toString().trim();	
						content = content.replace(",", "");
						threeBigExchangeDao.update(sqlDate, content, rank, buyer);
						break;
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}

		}
	}
	
}
