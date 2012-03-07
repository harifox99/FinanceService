package org.bear.parser;
import java.io.*;
import java.util.List;
import org.bear.util.RegEx;
import org.bear.util.StringUtil;
import org.bear.dao.CashFlowsDao;
import org.bear.entity.CashFlowsEntity;
/**
 * @author edward
 * Parse證交所的現金流量表
 */
public class CashFlowsParser 
{
	BufferedReader htmlContent;
	CashFlowsEntity entity, previousEntity, fullEntity;
	String yearseason;
	List <CashFlowsEntity> list;
	String year;
	String season;
	String stockID;
	CashFlowsDao basicCashFlowsDao;
	BufferedWriter writer;
	public CashFlowsParser(BufferedReader htmlContent, String yearseason, List <CashFlowsEntity> list, 
			String stockID, CashFlowsDao basicCashFlowsDao, BufferedWriter writer)
	{
		this.htmlContent = htmlContent;
		this.yearseason = yearseason;
		this.list = list;
		this.year = yearseason.substring(0, 2);
		this.season = yearseason.substring(2, 4);
		this.stockID = stockID;
		this.basicCashFlowsDao = basicCashFlowsDao;
		this.writer = writer;
		entity = new CashFlowsEntity();		
	}
	public boolean parse() throws Exception
	{
		String thisLine;
		entity.setYear(year);
		entity.setSeasons(season);
		entity.setStockID(stockID);	
		//營業活動現金
		int operatingActivity = 0;
		//投資活動現金
		int investingActivity = 0;
		//期初現金
		int beginningCash = 0;
		//期末現金
		int endingCash = 0;
		//年度營業活動現金
		int operatingYearActivity = 0;
		//年度投資活動現金
		int investingYearActivity = 0;
		//年度期初現金
		int beginningYearCash = 0;
		//年度期末現金
		int endingYearCash = 0;
		boolean isSuccessful = true;
		if (season.equals("02"))
		{
			previousEntity = this.calculateSeason(stockID, year, "01");
		}
		else if (season.equals("03"))
		{
			previousEntity = this.calculateSeason(stockID, year, "01"); 
			previousEntity.add(this.calculateSeason(stockID, year, "02"));
		}
		else if (season.equals("04"))
		{
			previousEntity = this.calculateSeason(stockID, year, "01"); 
			previousEntity.add(this.calculateSeason(stockID, year, "02")); 
			previousEntity.add(this.calculateSeason(stockID, year, "03"));
			//儲存整年度資料
			fullEntity = new CashFlowsEntity();
			fullEntity.setYear(year);
			fullEntity.setSeasons("00");
			fullEntity.setStockID(stockID);	
		}
		while ((thisLine = htmlContent.readLine()) != null)
		{
			//本期損益			
			//System.out.println(thisLine);
			if ((thisLine.contains("純") && thisLine.contains("益") && !thisLine.contains("投資") && !thisLine.contains("利益") && !thisLine.contains("資產") && !thisLine.contains("成本") && !thisLine.contains("金融") && !thisLine.contains("公司") && !thisLine.contains("存貨") && thisLine.indexOf("純") < thisLine.indexOf("益")) ||
				(thisLine.contains("淨") && thisLine.contains("利") && !thisLine.contains("投資") && !thisLine.contains("利益") && !thisLine.contains("資產") && !thisLine.contains("成本") && !thisLine.contains("金融") && !thisLine.contains("公司") && !thisLine.contains("存貨") && thisLine.indexOf("淨") < thisLine.indexOf("利")) || 
				(thisLine.contains("純") && thisLine.contains("損") && !thisLine.contains("投資") && !thisLine.contains("利益") && !thisLine.contains("資產") && !thisLine.contains("成本") && !thisLine.contains("金融") && !thisLine.contains("公司") && !thisLine.contains("存貨") && thisLine.indexOf("純") < thisLine.indexOf("損")) || 	
				(thisLine.contains("淨") && thisLine.contains("損") && !thisLine.contains("投資") && !thisLine.contains("利益") && !thisLine.contains("資產") && !thisLine.contains("成本") && !thisLine.contains("金融") && !thisLine.contains("公司") && !thisLine.contains("存貨") && thisLine.indexOf("淨") < thisLine.indexOf("損")) || 	
				thisLine.contains("本期淨利") || thisLine.contains("本期損益") || thisLine.contains("本期稅後淨利") ||
				thisLine.contains("本期稅後淨損") || thisLine.contains("本期稅後淨益") || thisLine.contains("本期淨損") ||
				thisLine.contains("本期(損)益") || thisLine.contains("本期淨(損)") || thisLine.contains("本期純益"))
			{				
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				if (season.equals("01"))
				{
					entity.setIncomeSummary(result);
					System.out.println("本期淨利：" + result);
					writer.write("本期淨利：" + result + "\n");
				}
				else if (season.equals("04"))
				{
					entity.setIncomeSummary(result - previousEntity.getIncomeSummary());
					//儲存年度淨利
					fullEntity.setIncomeSummary(result);
				}
				else
					entity.setIncomeSummary(result - previousEntity.getIncomeSummary());
			}
			else if (thisLine.contains("營") && thisLine.contains("業") && thisLine.contains("活") &&
			         thisLine.contains("動") && thisLine.contains("現") && thisLine.contains("金") &&
			         thisLine.indexOf("營") < thisLine.indexOf("金"))
			{
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				if (season.equals("01"))
				{
					entity.setOperatingActivity(result);
					operatingActivity = result;
				}				
				else if (season.equals("04"))
				{
					//儲存年度營業活動之淨現金
					fullEntity.setOperatingActivity(result);
					operatingYearActivity = result;
					/****/
					result = result - previousEntity.getOperatingActivity();
					entity.setOperatingActivity(result);
					operatingActivity = result;					
				}
				else
				{
					result = result - previousEntity.getOperatingActivity();
					entity.setOperatingActivity(result);
					operatingActivity = result;
				}
			}
			else if (thisLine.contains("投") && thisLine.contains("資") && thisLine.contains("活") &&
				     thisLine.contains("動") && thisLine.contains("現") && thisLine.contains("金") &&
			         thisLine.indexOf("投") < thisLine.indexOf("金"))
			{
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				if (season.equals("01"))
				{
					entity.setInvestingActivity(result);
					investingActivity = result;
				}
				else if (season.equals("04"))
				{
					//儲存年度投資活動之淨現金
					fullEntity.setInvestingActivity(result);
					investingYearActivity = result;
					/****/
					result = result - previousEntity.getInvestingActivity();
					entity.setInvestingActivity(result);
					investingActivity = result;					
				}
				else
				{
					result = result - previousEntity.getInvestingActivity();
					entity.setInvestingActivity(result);
					investingActivity = result;
				}
			}
			else if ((thisLine.contains("融") && thisLine.contains("資") && thisLine.contains("活") &&
				      thisLine.contains("動") && thisLine.contains("現") && thisLine.contains("金") &&
				      thisLine.indexOf("融") < thisLine.indexOf("金")) ||
				     (thisLine.contains("理") && thisLine.contains("財") && thisLine.contains("活") &&
				      thisLine.contains("動") && thisLine.contains("現") && thisLine.contains("金") &&
				      thisLine.indexOf("理") < thisLine.indexOf("金")))
			{
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				if (season.equals("01"))
					entity.setFinancingActivity(result);
				else if (season.equals("04"))
				{
					entity.setFinancingActivity(result - previousEntity.getFinancingActivity());
					//儲存年度融資活動之淨現金
					fullEntity.setFinancingActivity(result);
				}
				else
					entity.setFinancingActivity(result - previousEntity.getFinancingActivity());
			}
			else if (thisLine.contains("期初現金") || thisLine.contains("年初現金") || thisLine.contains("現金及約當現金期初餘額"))
			{
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				if (season.equals("01"))
				{
					entity.setBeginningCash(result);
					beginningCash = result;
				}
				else if (season.equals("02"))
				{					
					previousEntity = this.calculateSeason(stockID, year, "01"); 
					entity.setBeginningCash(previousEntity.getEndingCash());
					beginningCash = previousEntity.getEndingCash();
				}
				else if (season.equals("03"))
				{					
					previousEntity = this.calculateSeason(stockID, year, "02"); 
					entity.setBeginningCash(previousEntity.getEndingCash());
					beginningCash = previousEntity.getEndingCash();
				}
				else if (season.equals("04"))
				{					
					previousEntity = this.calculateSeason(stockID, year, "03"); 
					entity.setBeginningCash(previousEntity.getEndingCash());
					beginningCash = previousEntity.getEndingCash();
					//儲存期初現金及約當現金餘額
					fullEntity.setBeginningCash(result);
					beginningYearCash = result;
				}
			}
			else if (thisLine.contains("期末現金") || thisLine.contains("年底現金") || 
					 thisLine.contains("期未現金") || thisLine.contains("現金及約當現金期末餘額"))
			{
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				entity.setEndingCash(result);
				endingCash = result;
				//儲存期末現金及約當現金餘額
				if (season.equals("04"))
				{
					fullEntity.setEndingCash(result);
					endingYearCash = result;
				}
			}
			else if (thisLine.contains("之公司不存在"))
			{
				System.out.println(stockID + "之公司不存在！");
				break;
			}
			else if (thisLine.contains("系統流量大，請稍後再查詢"))
			{
				System.out.println(stockID + "系統流量大，請稍後再查詢！");
				writer.write(stockID + "系統流量大，請稍後再查詢！\n");
				Thread.sleep(1000 * 10);
				isSuccessful = false;
				break;
			}
		}
		if (isSuccessful == true)
		{
			entity.setNetCashFlows(endingCash - beginningCash);
			entity.setFreeCashFlow(operatingActivity + investingActivity);
			if (season.equals("04"))
			{
				//儲存本期現金及約當現金增加數
				fullEntity.setNetCashFlows(endingYearCash - beginningYearCash);
				//儲存年度自由現金
				fullEntity.setFreeCashFlow(operatingYearActivity + investingYearActivity);
				list.add(fullEntity);
			}
			list.add(entity);
		}
		return isSuccessful;
	}
	private int getFinancialData(String thisLine)
	{
		//切割現金流量表資料

		thisLine = thisLine.replace("\t", " ");
		thisLine = thisLine.replace("　", " ");
		thisLine = thisLine.replace("=", " ");
		String splitData[] = thisLine.split(" ");
		int result = 0;
		for (int i = 0; i < splitData.length; i++)
		{
			//略過空白資料，[^一二三四五六七八九]*[0-9][^一二三四五六七八九]*
			if (splitData[i].length() > 2 && new RegEx("([0-9])([^一二三四五六七八九])", splitData[i]).isMatch() == true)
			{
				boolean isPositive;
				String incomeSummary = StringUtil.eraseSpecialChar(splitData[i]);
				if (incomeSummary.contains("-"))
					isPositive = false;
				else
					isPositive = true;
				incomeSummary = incomeSummary.replaceAll("\\D", "");
				if (isPositive == false)
					incomeSummary = "-" + incomeSummary;
				result = Integer.parseInt(incomeSummary);
				
				break;
			}
		}
		return result;
	}
	private CashFlowsEntity calculateSeason(String stockID, String year, String season)
	{
		CashFlowsEntity entity = basicCashFlowsDao.findDataBySeason(stockID, year, season).get(0);
		return entity;
	}
}

