/**
 * 
 */
package org.bear.parser;

import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.bear.dao.BalanceSheetDao;
import org.bear.entity.BalanceSheetEntity;
import org.bear.entity.BasicEntity;
import org.bear.util.AccountTitle;
import org.bear.util.GetURLCathayBalanceSheetSingle;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author edward
 * Parse國泰網站的資產負債表，並將其存在資料庫
 */
public class BalanceSheetParserCathay extends ParserBase implements Parser
{
	//資產負債DAO
	BalanceSheetDao dao;
	//將elementList轉成可以儲存至DB的資料
	public BalanceSheetEntity entity[];
	//判斷季資料或年資料
	boolean isYear;
	//判斷合併報表資料是否足夠
	int expectedNum;
	//擷取合併報表=true, 否則=false
	boolean isCombined;
	String[] years;
	String[] seasons;
	public BalanceSheetParserCathay(){}
	public BalanceSheetParserCathay(List<Element> elementList, String stockID, boolean isYear,
			String[] years, String[] seasons, int expectedNum, boolean isCombined)
	{
		this.isYear = isYear;
		this.years = years;
		this.seasons = seasons;
		this.isCombined = isCombined;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		this.elementList = elementList;
		this.stockID = stockID;
		this.expectedNum = expectedNum;
		entity = new BalanceSheetEntity[dataLength];	
		
		for (int i = 0; i < dataLength; i++)
			entity[i] = new BalanceSheetEntity();
		dao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
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
					if (content.equals("現金及約當現金"))
						title = AccountTitle.Cash;
					else if (content.equals("短期投資"))
						title = AccountTitle.ShortTermInvestment;
					else if (content.equals("應收帳款及票據"))
						title = AccountTitle.Receivable;
					else if (content.equals("其他應收款"))	
						title = AccountTitle.OtherReceivable;
					else if (content.equals("短期借支"))	
						title = AccountTitle.ShortTermBorrowing;
					else if (content.equals("存貨"))	
						title = AccountTitle.Inventory;
					else if (content.equals("預付費用及預付款"))
						title = AccountTitle.PrepaidExpense;
					else if (content.equals("其他流動資產"))
						title = AccountTitle.OtherCurrentAssets;
					else if (content.equals("流動資產"))
						title = AccountTitle.CurrentAssets;
					else if (content.equals("長期投資"))
						title = AccountTitle.LongTermInvestment;
					else if (content.equals("固定資產"))
						title = AccountTitle.FixedAssets;
					else if (content.equals("其他資產"))
						title = AccountTitle.OtherAssets;
					else if (content.equals("資產總額"))
						title = AccountTitle.TotalAssets;
					else if (content.equals("流動負債"))
						title = AccountTitle.CurrentLiability;
					else if (content.equals("長期負債"))
						title = AccountTitle.LongTermLiability;
					else if (content.equals("其他負債及準備"))
						title = AccountTitle.OtherLiability;
					else if (content.equals("負債總額"))
						title = AccountTitle.TotalLiability;
					else if (content.equals("股東權益總額"))
						title = AccountTitle.StockholdersEquity;					
					else if (content.equals("一年內到期長期負債"))
						title = AccountTitle.LongTermOneYear;
					else if (content.equals("應付帳款及票據"))
						title = AccountTitle.AccountsPayable;
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
						dao.insert(entity[i]);
				}
			}
		}
		if (this.checkExpectedNum() == true && this.isCombined == true)
		{
			GetURLCathayBalanceSheetSingle urlContent = new GetURLCathayBalanceSheetSingle(stockID, this.isYear);
			BalanceSheetParserCathay balanceSheetYear = new BalanceSheetParserCathay(urlContent.getContent(), stockID, this.isYear, years, seasons, expectedNum, false);
			balanceSheetYear.parse(2);
		}
	}
	/**
	 * 檢查合併報表資料是否足夠，若不足，則擷取單一報表
	 * @return
	 */
	private boolean checkExpectedNum()
	{
		int counter = 0;
		for (int i = 0; i < dataLength; i++)
		{
			if (entity[i].year == null && entity[i].seasons == null && entity[i].stockID == null)
				counter++;
		}
		if ( (dataLength - counter) >= this.expectedNum)
			return false;
		else
			return true;
		
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
				case Cash:
				{
					int result = Integer.parseInt(rowData[k]);
					entity[k].setCash(result);
					break;
				}	
				case ShortTermInvestment:
				{
					int result = Integer.parseInt(rowData[k]);
					entity[k].setShortTermInvestment(result);
					break;
				}	     
				case Receivable: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setReceivable(result);
					break;
				}	
				case OtherReceivable: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setOtherReceivable(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setOtherReceivable(result);
					}
					break;
				}	
				case ShortTermBorrowing: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setShortTermBorrow(result);
					break;
				}	
				case Inventory: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setInventory(result);
					break;	
				}
				case PrepaidExpense: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setPrepaidExpense(result);
					break;	
				}
				case OtherCurrentAssets: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setOtherCurrentAssets(result);
					break;	
				}
				case CurrentAssets: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setCurrentAssets(result);
					break;	
				}
				case LongTermInvestment: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setLongTermInvestment(result);
					break;	
				}
				case FixedAssets: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setFixedAssets(result);
					break;	
				}
				case OtherAssets: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setOtherAssets(result);
					break;	
				}
				case TotalAssets: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setTotalAssets(result);
					break;	
				}
				case LongTermOneYear: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setLongTermOneYear(result);
					break;	
				}
				case CurrentLiability: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setCurrentLiability(result);
					break;	
				}
				case LongTermLiability: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setLongTermLiability(result);
					break;	
				}
				case OtherLiability: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setOtherLiability(result);
					break;	
				}
				case TotalLiability: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setTotalLiability(result);
					break;	
				}
				case StockholdersEquity: 
				{	
					try
					{
						int result = Integer.parseInt(rowData[k]);
						entity[k].setStockholdersEquity(result);
					}
					catch (NumberFormatException ex)
					{
						int result = Integer.parseInt(rowData[k].substring(rowData[k].indexOf(">")+1));
						entity[k].setStockholdersEquity(result);
					}
					break;	
				}
				case AccountsPayable: 
				{	
					int result = Integer.parseInt(rowData[k]);
					entity[k].setAccountsPayable(result);
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
	public void setYearAndSeason(BasicEntity entity, String rowData)
	{
		if (this.isYear == false)
		{
			String yearAndSeason[] = rowData.split("\\.");
			entity.setYear(convertYear(yearAndSeason[0]));
			entity.setSeasons(this.convertMonth(yearAndSeason[1]));
			entity.setStockID(stockID);
		}
		else
		{
			entity.setYear(convertYear(rowData));
			entity.setSeasons("00");
			entity.setStockID(stockID);
		}
	}
	public void parse(int tableIndex) {
		// TODO Auto-generated method stub
		this.getTableContent(elementList.get(tableIndex));
	}
}
