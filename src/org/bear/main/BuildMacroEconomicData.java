package org.bear.main;
import java.util.Date;
import java.util.List;

import org.bear.constant.CbcIndexConstant;
import org.bear.constant.CepdIndexConstant;
import org.bear.dao.*;
import org.bear.datainput.ImportMacroEconomic;
import org.bear.entity.MacroEconomicEntity;
import org.bear.parser.taiwanMacro.CepdParser;
import org.bear.parser.taiwanMacro.TwseIndex;
import org.bear.util.ParseFile;
import org.bear.util.cbc.GetCbcMoney;
import org.bear.util.cbc.GetDemandDeposit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildMacroEconomicData extends ParseFile
{
	/**
	 * 用經建會、中央銀行與證券期貨發展基金會的資料建立總經指標、貨幣資料與大盤指數
	 * @param args
	 */
	List <MacroEconomicEntity> list;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String startYear = "2013";
		String startMonth = "7";
		String endYear = "2013";
		String endMonth = "7";
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		JdbcMacroEconomicDao dao = (JdbcMacroEconomicDao)context.getBean("macroEconomicDao");
		//CEPD
		CepdParser parser = new CepdParser();
		parser.setStartDate(startYear + "," + startMonth);
		parser.setEndDate(endYear + "," + endMonth);
		parser.setDao(dao);
		for (int i = 0; i < CepdIndexConstant.CEPD_LIST.length; i++)
		{
			parser.setIndex(i);
			parser.setUrl(CepdIndexConstant.CEPD_LIST[i], CepdIndexConstant.CEPD_MAP[i]);
			parser.getConnection();
			parser.parse(10);
		}
		//貨幣
		GetCbcMoney money = new GetCbcMoney();
		money.setDao(dao);
		money.getContent(CbcIndexConstant.MONTH_HASH.get("2013M04"), CbcIndexConstant.MONTH_HASH.get("2013M07"));
		
		//活期儲蓄存款
		GetDemandDeposit deposit = new GetDemandDeposit();
		deposit.setDao(dao);
		deposit.getContent(CbcIndexConstant.MONTH_HASH.get("2013M04"), CbcIndexConstant.MONTH_HASH.get("2013M07"));
		
		//TWSE
		TwseIndex twseIndex = new TwseIndex();
		twseIndex.setDao(dao);
		twseIndex.getContent(startYear, startMonth, endYear, endMonth);
	}
	public void insertBatch()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		MacroEconomicDao dao = (MacroEconomicDao)context.getBean("macroEconomicDao");
		list = new ImportMacroEconomic().getMacroEconomicList();
		dao.insertBatch(list);
	}
	public List<MacroEconomicEntity> findAllList()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		MacroEconomicDao dao = (MacroEconomicDao)context.getBean("macroEconomicDao");	
		list = dao.findAll();		
		return list;
	}
	public List<MacroEconomicEntity> findListByDate(Date startTime, Date endTime)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		MacroEconomicDao dao = (MacroEconomicDao)context.getBean("macroEconomicDao");	
		list = dao.findByDate(startTime, endTime);		
		return list;
	}
}
