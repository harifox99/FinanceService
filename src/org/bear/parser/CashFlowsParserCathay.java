package org.bear.parser;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.entity.CashFlowsEntity;
import org.bear.util.*;
/**
 * @author edward
 * Parse國泰網站的現金流量表
 */
public class CashFlowsParserCathay extends CashFlowsParserYam
{
	//期初現金
	int beginningCash[] = new int[dataLength];
	//期末現金
	int endingCash[] = new int[dataLength];
	//營業活動現金
	int operatingActivity[] = new int[dataLength];
	//投資活動現金
	int investingActivity[] = new int[dataLength];
	public CashFlowsParserCathay(List<Element> elementList, String stockID)
	{
		super(elementList, stockID);
	}
	/*
	private Element getTableContent(Element element, int rows, int cols)
	{
		Element resultElement = null;
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		if (rows < trList.size())
		{
			Element trElement = trList.get(rows);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			if(cols < tdList.size())
			{
				resultElement = tdList.get(cols);
			}
		}
		return resultElement;
	}*/
	public void getTableContent(Element element)
	{
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
					if (content.equals("稅後淨利"))
						title = AccountTitle.INCOME_SUMMARY;
					else if (content.equals("來自營運之現金流量"))
						title = AccountTitle.OPERATING_ACTIVITY;
					else if (content.equals("投資活動之現金流量"))
						title = AccountTitle.INVESTING_ACTIVITY;
					else if (content.equals("理財活動之現金流量"))	
						title = AccountTitle.FINANCING_ACTIVITY;
					else if (content.equals("期初現金約當現金"))	
						title = AccountTitle.BEGINNING_CASH;
					else if (content.equals("期末現金及約當現金"))	
						title = AccountTitle.ENDING_CASH;
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
			basicCashFlowsDao.insert(entity[i]);
		}
	}
	public void setStockData(String rowData[])
	{
		for (int k = 0; k < dataLength; k++)
		{
			switch (title) 
			{
				case INCOME_SUMMARY:
				{
					int result = Integer.parseInt(rowData[k]);
					entity[k].setIncomeSummary(result);
					break;
				}	
				case OPERATING_ACTIVITY:
				{
					int result = Integer.parseInt(rowData[k]);
					entity[k].setOperatingActivity(result);
					operatingActivity[k] = result;
					break;
				}	     
				case INVESTING_ACTIVITY: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setInvestingActivity(result);
					investingActivity[k] = result;
					break;
				}	
				case FINANCING_ACTIVITY: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setFinancingActivity(result);
					break;
				}	
				case BEGINNING_CASH: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setBeginningCash(result);
					beginningCash[k] = result;
					break;
				}	
				case ENDING_CASH: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setEndingCash(result);
					endingCash[k] = result;
					break;	
				}
				case SEASON:
				{
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
			entity[l].setNetCashFlows(endingCash[l] - beginningCash[l]);
		}		
	}
	public void setYearAndSeason(CashFlowsEntity entity, String rowData)
	{
		String yearAndSeason[] = rowData.split("\\.");
		entity.setYear(this.convertYear(yearAndSeason[0]));
		entity.setSeasons(this.convertMonth(yearAndSeason[1]));
		entity.setStockID(stockID);
	}
}

