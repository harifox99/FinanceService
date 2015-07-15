package org.bear.parser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bear.constant.FinancialReport;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.entity.*;
import org.bear.util.StringUtil;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 證交所三大法人買賣超資料Parser
 * @author edward
 *
 */
public class ThreeBigExchangeParser extends EasyParserBase
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
		
		int rankNumber = 0;
		//是否開始讀取賣超資料
		boolean isMinusData = false;
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
				try
				{											
					resultElement = tdList.get(j);				
					String content = resultElement.getContent().toString().trim();	
					content = content.replace(",", "");
					if (j == 1)//Stock ID
					{
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						//民國轉換成西元
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
						if (FinancialReport.maxNumber < rankNumber)
						{
                            if (quantity < 0 && isMinusData == false)
							{
								rankNumber = 1;
								isMinusData = true;
							}
							else
							{
								break;
							}
						}
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
}
