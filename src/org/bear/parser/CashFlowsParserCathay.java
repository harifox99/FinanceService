package org.bear.parser;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.entity.BasicEntity;
import org.bear.util.*;
/**
 * @author edward
 * Parse國泰網站的現金流量表
 */
public class CashFlowsParserCathay extends CashFlowsParserYam implements Parser
{
	//期初現金
	int beginningCash[] = new int[dataLength];
	//期末現金
	int endingCash[] = new int[dataLength];
	//營業活動現金
	int operatingActivity[] = new int[dataLength];
	//投資活動現金
	int investingActivity[] = new int[dataLength];
	String[] years;
	String[] seasons;
	//判斷季資料或年資料
	boolean isYear;
	public CashFlowsParserCathay(List<Element> elementList, String stockID, String[] years, String[] seasons, boolean isYear, int expectedNum, boolean isCombined)
	{		
		super(elementList, stockID);
		this.years = years;
		this.seasons = seasons;
		this.isYear = isYear;
		this.isCombined = isCombined;
		this.expectedNum = expectedNum;
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
					content = StringUtil.eraseSpecialChar(content);
					content = content.trim();
					if (content.contains("稅前淨利"))
						title = AccountTitle.INCOME_SUMMARY;
					else if (content.equals("來自營運之現金流量"))
						title = AccountTitle.OPERATING_ACTIVITY;
					else if (content.equals("投資活動之現金流量"))
						title = AccountTitle.INVESTING_ACTIVITY;
					else if (content.contains("資活動之現金流量"))//融資/籌資活動	
						title = AccountTitle.FINANCING_ACTIVITY;
					else if (content.equals("期初現金及約當現金"))	
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
			for (int j = 0; j < seasons.length; j++)
			{
				for (int k = 0; k < years.length; k++)
				{							
					if (entity[i].year != null && entity[i].seasons != null && entity[i].stockID != null &&
						entity[i].year.equals(years[k])	&& entity[i].seasons.equals(seasons[j]))
						if (this.isCombined == true)
							basicCashFlowsDao.insert(entity[i]);
						else
							basicCashFlowsDao.insertWithCheck(entity[i]);
				}
			}
		}
		if (this.checkExpectedNum(entity, expectedNum, years, seasons) == true && this.isCombined == true)
		{
			GetURLCathayCashFlowSingle urlContent = new GetURLCathayCashFlowSingle(stockID, this.isYear);
			CashFlowsParserCathay cashFlowsParser = new CashFlowsParserCathay(urlContent.getContent(), stockID, years, seasons, this.isYear, expectedNum, false);
			cashFlowsParser.parse(2);
		}
	}
	public void setStockData(String rowData[])
	{
		for (int k = 0; k < dataLength; k++)
		{
			if (rowData[k] == null)
				continue;
			if (rowData[k].equals("N/A"))
		    {
				rowData[k] = "0";
		    }
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
	public void setYearAndSeason(BasicEntity entity, String rowData)
	{
		if (this.isYear == false)
		{
			String yearAndSeason[] = rowData.split("\\.");
			entity.setYear(yearAndSeason[0]);
			entity.setSeasons(this.convertMonth(yearAndSeason[1]));
			entity.setStockID(stockID);
		}
		else
		{
			entity.setYear(StringUtil.convertYear(rowData));
			entity.setSeasons("00");
			entity.setStockID(stockID);
		}
	}
}

