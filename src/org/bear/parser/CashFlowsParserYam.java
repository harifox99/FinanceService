package org.bear.parser;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.dao.CashFlowsDao;
import org.bear.entity.BasicEntity;
import org.bear.entity.CashFlowsEntity;
import org.bear.util.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author edward
 * Parse蕃薯藤的現金流量表
 */
public class CashFlowsParserYam extends ParserBase implements Parser
{
	//營業活動現金
	int operatingActivity[] = new int[dataLength];
	//投資活動現金
	int investingActivity[] = new int[dataLength];
	//現金流量DAO
	CashFlowsDao basicCashFlowsDao;
	//將elementList轉成可以儲存至DB的資料
	String lastYear;
	String year;
	public CashFlowsEntity entity[];
	public CashFlowsParserYam(List<Element> elementList, String stockID, String year)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		this.elementList = elementList;
		this.stockID = stockID;
		entity = new CashFlowsEntity[dataLength];	
		this.year = year;
		for (int i = 0; i < dataLength; i++)
			entity[i] = new CashFlowsEntity();
		basicCashFlowsDao = (CashFlowsDao)context.getBean("basicCashFlowsDao");
	}
	public void parse(int tableIndex)
	{
		this.getTableContent(elementList.get(tableIndex));
	}
	/*
	 * (non-Javadoc)
	 * @see org.bear.parser.Parser#getTableContent(net.htmlparser.jericho.Element)
	 * 拆解Element
	 */
	public void getTableContent(Element element)
	{
		//儲存一筆Row資料
		String rowData[] = new String[dataLength];
		Element resultElement = null;
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			for (int j = 0; j < tdList.size(); j++)
			{
				if (j == 0)
				{
					resultElement = tdList.get(j);
					String content = resultElement.getContent().toString();
					if (content.equals("來自營運之現金流量"))
						title = AccountTitle.OPERATING_ACTIVITY;
					else if (content.equals("投資活動之現金流量"))
						title = AccountTitle.INVESTING_ACTIVITY;
					else if (content.equals("理財活動之現金流量"))	
						title = AccountTitle.FINANCING_ACTIVITY;
					else if (content.equals("本期產生現金流量"))	
						title = AccountTitle.NET_CASH_FLOWS;
					else if (content.equals("期別"))
						title = AccountTitle.SEASON;
					else
					{
						title = AccountTitle.EMPTY;
						break;
					}
				}
				else
				{
					resultElement = tdList.get(j);
					String content = resultElement.getContent().toString();
					content = content.replaceAll(",", "");
					rowData[j-1] = content;
					if (j == tdList.size() - 1)
					{
						setStockData(rowData);
					}
				}
			}			
		}
		for (int i = 0; i < dataLength; i++)
		{
			if (entity[i].getYear().equals(year))
				basicCashFlowsDao.insert(entity[i]);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.bear.parser.Parser#setYearAndSeason(org.bear.entity.CashFlowsEntity, int)
	 * 儲存Year and Seasons
	 */
	public void setYearAndSeason(BasicEntity entity, String rowData)
	{
		if (rowData.length() < 4)
		{
			int year = Integer.parseInt(lastYear);
			entity.setYear(String.valueOf(--year));
			lastYear = String.valueOf(year);
		}
		else
		{
			entity.setYear(rowData.substring(0, 4));
			lastYear = rowData.substring(0, 4);
		}
		entity.setSeasons("00");
		entity.setStockID(stockID);
		
	}
	/*
	 * (non-Javadoc)
	 * @see org.bear.parser.Parser#setStockData(java.lang.String[])
	 * 將這筆Row資料儲存至Entity，共八筆
	 */
	public void setStockData(String rowData[])
	{
		for (int k = 0; k < dataLength; k++)
		{
			switch (title) 
			{
				case OPERATING_ACTIVITY:
				{
					if (rowData[k].equals(""))
					{
						entity[k].setOperatingActivity(0);
						operatingActivity[k] = 0;
					}
					else
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setOperatingActivity(result);
						operatingActivity[k] = result;
					}
					break;
				}	     
				case INVESTING_ACTIVITY:
				{	
					if (rowData[k].equals(""))
					{
						entity[k].setInvestingActivity(0);
						investingActivity[k] = 0;
					}
					else
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setInvestingActivity(result);
						investingActivity[k] = result;
					}
					break;
				}	
				case FINANCING_ACTIVITY: 
				{	
					if (rowData[k].equals(""))
					{
						entity[k].setFinancingActivity(0);
					}
					else
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setFinancingActivity(result);
					}					
					//this.setYearAndSeason(entity[k], k);
					break;
				}	
				case NET_CASH_FLOWS: 
				{	
					if (rowData[k].equals(""))
					{
						entity[k].setNetCashFlows(0);
					}
					else
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setNetCashFlows(result);
					}
					//this.setYearAndSeason(entity[k], k);
					break;
				}	     
				case SEASON:
				{
					/*
					entity[k].setYear(rowData[k].substring(0, 4));
					entity[k].setSeasons("00");
					entity[k].setStockID(stockID);*/
					this.setYearAndSeason(entity[k], rowData[k]);
					break;
				}
				default:
					break;
			}
		}
		for (int l = 0; l < dataLength; l++)
		{
			entity[l].setFreeCashFlow(operatingActivity[l] + investingActivity[l]);
		}		
	}
}

