package org.bear.parser;
import java.io.*;
import java.util.List;
import org.bear.util.RegEx;
import org.bear.util.StringUtil;
import org.bear.dao.CashFlowsDao;
import org.bear.entity.CashFlowsEntity;
/**
 * @author edward
 * Parse證交所的年度現金流量表
 */
public class CashFlowsParserYear 
{
	BufferedReader htmlContent;
	CashFlowsEntity entity;
	String yearseason;
	List <CashFlowsEntity> list;
	String year;
	String season;
	String stockID;
	CashFlowsDao basicCashFlowsDao;
	BufferedWriter writer;
	public CashFlowsParserYear(BufferedReader htmlContent, String yearseason, List <CashFlowsEntity> list, 
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
		int intYear = Integer.parseInt(this.year);
		entity.setYear(String.valueOf(--intYear));
		entity.setSeasons("00");
		entity.setStockID(stockID);	
		//營業活動現金
		int operatingActivity = 0;
		//投資活動現金
		int investingActivity = 0;
		//期初現金
		int beginningCash = 0;
		//期末現金
		int endingCash = 0;
		boolean isSuccessful = true;
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
				thisLine.contains("本期(損)益") || thisLine.contains("本期淨(損)") || thisLine.contains("本期純益") ||
				thisLine.contains("本期淨益"))
			{				
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				entity.setIncomeSummary(result);
				System.out.println("本期淨利：" + result);
				writer.write("本期淨利：" + result + "\n");
			}
			else if (thisLine.contains("營") && thisLine.contains("業") && thisLine.contains("活") &&
			         thisLine.contains("動") && thisLine.contains("現") && thisLine.contains("金") &&
			         thisLine.indexOf("營") < thisLine.indexOf("金"))
			{
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				entity.setOperatingActivity(result);
				operatingActivity = result;		
			}
			else if (thisLine.contains("投") && thisLine.contains("資") && thisLine.contains("活") &&
				     thisLine.contains("動") && thisLine.contains("現") &&
			         thisLine.indexOf("投") < thisLine.indexOf("現"))
			{
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				entity.setInvestingActivity(result);
				investingActivity = result;
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
				entity.setFinancingActivity(result);
			}
			else if (thisLine.contains("期初現金") || thisLine.contains("年初現金") || 
					 thisLine.contains("現金及約當現金期初餘額") || thisLine.contains("現金及約當現金年初餘額 "))
			{
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				entity.setBeginningCash(result);
				beginningCash = result;
			}
			else if (thisLine.contains("期末現金") || thisLine.contains("年底現金") || 
					 thisLine.contains("現金及約當現金年底餘額") || thisLine.contains("現金及約當現金期末餘額"))
			{
				int result = this.getFinancialData(thisLine);
				if (result == 0)
					continue;
				entity.setEndingCash(result);
				endingCash = result;
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
		int columnCount = 0;
		for (int i = 0; i < splitData.length; i++)
		{
			if (splitData[i].length() >= 2 && new RegEx("[0-9]", splitData[i]).isMatch() == true && columnCount++ == 1)
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
}

