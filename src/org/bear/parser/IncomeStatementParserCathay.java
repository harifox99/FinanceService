/**
 * 
 */
package org.bear.parser;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.dao.IncomeStatementDao;
import org.bear.entity.IncomeStatementEntity;
import org.bear.util.AccountTitle;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author edward
 * Parse國泰網站的損益表，並將其存在資料庫
 */
public class IncomeStatementParserCathay extends BalanceSheetParserCathay 
{
	//損益DAO
	IncomeStatementDao dao;
	//將elementList轉成可以儲存至DB的資料
	public IncomeStatementEntity entity[];
	String year;
	String seasons;
	public IncomeStatementParserCathay(List<Element> elementList, String stockID, boolean isYear,
			String year, String seasons)
	{
		this.isYear = isYear;
		this.year = year;
		this.seasons = seasons;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		this.elementList = elementList;
		this.stockID = stockID;
		entity = new IncomeStatementEntity[dataLength];		
		for (int i = 0; i < dataLength; i++)
			entity[i] = new IncomeStatementEntity();
		dao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
	}
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
					if (content.equals("營業收入淨額"))
						title = AccountTitle.OperatingRevenue;
					else if (content.equals("營業成本"))
						title = AccountTitle.OperatingCost;
					else if (content.equals("營業毛利"))
						title = AccountTitle.GrossProfit;
					else if (content.equals("營業費用"))	
						title = AccountTitle.OperatingExpense;
					else if (content.equals("營業利益"))	
						title = AccountTitle.OperatingIncome;
					else if (content.contains("投資收入"))	
						title = AccountTitle.InvestmentIncome;
					else if (content.equals("營業外收入合計"))
						title = AccountTitle.NonOperatingRevenue;
					else if (content.equals("營業外支出合計"))
						title = AccountTitle.NonOperatingExpense;
					else if (content.equals("稅前淨利"))
						title = AccountTitle.PreTaxIncome;
					else if (content.equals("本期稅後淨利"))
						title = AccountTitle.NetIncome;
					else if (content.contains("每股盈餘"))
						title = AccountTitle.EPS;
					else if (content.equals("期別") || content.equals("年"))
						title = AccountTitle.SEASON;
					else if (content.contains("加權平均股本"))
						title = AccountTitle.WghtAvgStocks;
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
			//建立所有資料
			if (entity[i].year != null && entity[i].seasons != null && entity[i].stockID != null &&
				entity[i].year.equals(year)	&& entity[i].seasons.endsWith(seasons))
				dao.insert(entity[i]);
			else
				System.out.println("年資料可能不足，" + stockID);
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
				case OperatingRevenue:
				{
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setOperatingRevenue(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setOperatingRevenue(result);
					}
					break;
				}	
				case OperatingCost:
				{
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setOperatingCost(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setOperatingCost(result);
					}
					break;
				}	     
				case GrossProfit: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setGrossProfit(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setGrossProfit(result);
					}
					break;
				}	
				case OperatingExpense: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setOperatingExpense(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setOperatingExpense(result);
					}
					break;
				}	
				case OperatingIncome: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setOperatingIncome(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setOperatingIncome(result);
					}
					break;
				}	
				case InvestmentIncome: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setInvestmentIncome(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setInvestmentIncome(result);
					}
					break;	
				}
				case NonOperatingRevenue: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setNonOperatingRevenue(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setNonOperatingRevenue(result);
					}
					break;	
				}
				case NonOperatingExpense: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setNonOperatingExpense(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setNonOperatingExpense(result);
					}
					break;	
				}
				case PreTaxIncome: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setPreTaxIncome(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setPreTaxIncome(result);
					}
					break;	
				}
				case NetIncome: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setNetIncome(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setNetIncome(result);
					}					
					break;	
				}
				case EPS: 
				{
					try
					{
						double result = Double.parseDouble(rowData[k]);
						entity[k].setEps(result);
					}
					catch (NumberFormatException ex)
					{
						double result = Double.parseDouble(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setEps(result);
					}
					break;	
				}
				case WghtAvgStocks: 
				{
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setWghtAvgStocks(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setWghtAvgStocks(result);
					}
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
	}
}
