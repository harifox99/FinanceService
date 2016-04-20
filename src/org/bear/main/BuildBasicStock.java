package org.bear.main;

import java.util.List;

import org.bear.dao.BasicStockDao;
import org.bear.datainput.ImportBasicStock;
import org.bear.entity.BasicStockWrapper;
import org.bear.parser.BasicDataParserCathay;
import org.bear.util.GetURLCathayBasicData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildBasicStock {

	/**
	 * @param args
	 */
	List <BasicStockWrapper> list;
	public static void main(String[] args) throws InterruptedException
	{
		// TODO Auto-generated method stub
		new BuildBasicStock().insertBatch();
		/************
		 * 1. 先執行BuildBasicStock，建立股票基本資訊 (砍掉股票代碼有A的)
		 * 2. 建立營收資料> 
		 * 2.1 上櫃成交資料：首頁 > 上櫃股票交易資訊 > 上櫃股票統計報表 > 市場交易月報> 櫃檯買賣股票成交資料彙總表
		 * 2.2 上市成交資料：首頁 > 統計報表 > 依統計報表查詢 > 市場交易月報> 證券交易統計表
		 * 2.3 目前以證券期貨發展基金會的資料為主取代2.1 & 2.2, BuildRevenueSFI
		 * 2.4 每個月個股成交資訊， BuildTaiwanPrice (此程式已廢棄不用)
		 * 
		 * 3. 財務報表
		 * 3.1  資產負債表 > BuildBalanceSheet
		 * 3.2 損益表 > BuildIncomeStatement
		 * 3.3 現金流量表 > BuildCashFlowsStatement
		 * 3.4 財務比率表 > BuildFinancialData
		 * 3.4 年報出來時，要重新擷取資料
		 * 4. 總經資料
		 * 4.1 台灣> BuildMacroEconomicData
		 * 4.1.1> 經建會> CepdParser
		 * 4.1.2> 央行> GetCbcMoney and GetDemandDeposit
		 * 4.1.3> 台灣加權指數> TwseIndex
		 * 4.1.4> 補投資人信心指數
		 * 4.2 美國> BuildAmericanMacroData
		 * 4.2.1> 補UMCSENT(密西根大學消費者信心指數)，自2014/1之後，本指數往後退一個月（即2月指標填到1月）
		 * 4.2.2> ISM指數> BuildPMIndex
		 * 4.2.3> S&P 500 and CRB Index>SP500Main
		 * 4.2.3.1> 補CRB Index
		 * 5. 籌碼
		 * 5.1> 集保董監持股> BuildStockDistribution
		 * 5.2> 證交所董監持股改> BuildSupervisor 
		 * 6.1 3/31年報, 5/15, 8/15, 11/15季報
		 * 6.2 每個月10號營收
		 * 6.3 每個月30號經濟指標
		 * 6.4 每個月1號籌碼與成交資訊
		 * 6.5 每個月20號董監持股 <-目前不再更新 
		 ************/
	}
	public void insertBatch() throws InterruptedException
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		BasicStockDao dao = (BasicStockDao)context.getBean("basicStockDao");
		list = new ImportBasicStock().getBasicStockList();
		dao.insertBatch(list);
		/***** 擷取股本 *****/
		List <BasicStockWrapper> wrapperList = dao.findAllData();
		for (int i = 0; i < wrapperList.size(); i++)
		{
			String stockID = wrapperList.get(i).getStockID();
			GetURLCathayBasicData urlContent = new GetURLCathayBasicData(stockID);
			BasicDataParserCathay parser = new BasicDataParserCathay(urlContent.getContent(), stockID);
			parser.parse(2);
			dao.updateCapital(stockID, parser.getCapital());
			System.out.println("stockID: " + stockID + ", index: " + i);
			Thread.sleep(1000);
		}
	}
}
