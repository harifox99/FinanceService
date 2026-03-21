package org.bear.main;
import java.util.Date;
import java.util.List;
import org.bear.constant.CbcIndexConstant;
import org.bear.dao.*;
import org.bear.datainput.ImportMacroEconomic;
import org.bear.entity.MacroEconomicEntity;
import org.bear.parser.statdb.GetLeadingIndex;
import org.bear.parser.taiwanMacro.TwseIndex;
import org.bear.util.ParseFile;
import org.bear.util.cbc.GetCbcMoney;
import org.bear.util.cbc.GetDemandDeposit;
import org.bear.util.cbc.GetExportOrder;
import org.bear.util.cbc.GetPbRatio;
import org.bear.util.cbc.GetStockValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildMacroEconomicData extends ParseFile
{
	/**
	 * 改startYear, startMonth, endYear, endMonth
	 * 改CbcIndexConstant.MONTH_HASH.get("201XMXX")
	 * 用經建會、中央銀行與證券期貨發展基金會的資料建立總經指標、貨幣資料與大盤指數
	 * 記得改初始日期
	 * @param args
	 */
	List <MacroEconomicEntity> list;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		MacroEconomicDao dao = (MacroEconomicDao)context.getBean("macroEconomicDao");
		//CEPD
		/* 經建會改名國發會，程式跟著改...2015/05/30...以後盡量去政府統計資料庫
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
		}*/
		
		String startDateValue = "2016M08";
		String endDateValue = "2016M09";
		//String date = "";
		//總經指標，政府統計資料網三不五時就在改程式，20160608，廢棄不用		
		//GetNdcData getNdcData = new GetNdcData(); 
		//getNdcData.setDao(dao);
		//getNdcData.getContent(CbcIndexConstant.STAT_DB_HASH.get(startDateValue), CbcIndexConstant.STAT_DB_HASH.get(endDateValue));
		//領先指標/領先指標不含趨勢/景氣燈號
		GetLeadingIndex getLeadingIndex = new GetLeadingIndex();
		getLeadingIndex.setDao(dao);
		getLeadingIndex.getContent(CbcIndexConstant.STAT_DB_HASH.get(startDateValue), CbcIndexConstant.STAT_DB_HASH.get(endDateValue));
		//景氣燈號
		//GetNdcSignalData getNdcSignalData = new GetNdcSignalData(); 
		//getNdcSignalData.setDao(dao);
		//getNdcSignalData.getContent(CbcIndexConstant.MACRO_ECONOMIC_SIGNAL.get(startDateValue), CbcIndexConstant.MACRO_ECONOMIC_SIGNAL.get(endDateValue));
		//台股市值
		GetStockValue getStockValue = new GetStockValue();
		getStockValue.setDao(dao);
		getStockValue.getContent(CbcIndexConstant.STAT_STOCK_VALUE_HASH.get(startDateValue),
								 CbcIndexConstant.STAT_STOCK_VALUE_HASH.get(endDateValue));
		//貨幣
		GetCbcMoney money = new GetCbcMoney();
		money.setDao(dao);
		money.getContent(CbcIndexConstant.MONTH_HASH.get(startDateValue), CbcIndexConstant.MONTH_HASH.get(endDateValue));		
		//活期儲蓄存款
		GetDemandDeposit deposit = new GetDemandDeposit();
		deposit.setDao(dao);
		deposit.getContent(CbcIndexConstant.MONTH_HASH.get(startDateValue), CbcIndexConstant.MONTH_HASH.get(endDateValue));			
		//台股股價淨值比
		GetPbRatio pbRatio = new GetPbRatio();
		pbRatio.setDao(dao);
		pbRatio.getContent(CbcIndexConstant.STOCK_PB_RATIO.get(startDateValue), CbcIndexConstant.STOCK_PB_RATIO.get(endDateValue));
		//領先指標不含趨勢
		//NdcParser parser = new NdcParser();
		//parser.setDao(dao);
		//parser.parse();
		//外銷訂單年增率
		GetExportOrder GetExportOrder = new GetExportOrder();
		GetExportOrder.setDao(dao);
		GetExportOrder.getContent(CbcIndexConstant.EXPORT_ORDER.get(startDateValue), CbcIndexConstant.EXPORT_ORDER.get(endDateValue));
		//TWSE，用Yahoo的
		TwseIndex twseIndex = new TwseIndex();
		twseIndex.setDao(dao);
		twseIndex.getContent(startDateValue);
		twseIndex.getContent("2016M08");
		twseIndex.getContent(endDateValue);
		
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
