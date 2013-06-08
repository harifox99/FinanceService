package org.bear.main;

import java.util.List;

import org.bear.dao.BasicStockDao;
import org.bear.datainput.ImportBasicStock;
import org.bear.entity.BasicStockWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildBasicStock {

	/**
	 * @param args
	 */
	List <BasicStockWrapper> list;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new BuildBasicStock().insertBatch();
		/************
		 * 1. 先執行BuildBasicStock，建立股票基本資訊
		 * 2. 建立營收資料> BuildRevenueYam
		 * 2.1 上櫃成交資料：首頁 > 上櫃股票交易資訊 > 上櫃股票統計報表 > 市場交易月報> 櫃檯買賣股票成交資料彙總表
		 * 2.2 上市成交資料：首頁 > 統計報表 > 依統計報表查詢 > 市場交易月報> 證券交易統計表
		 * 3. 財務報表
		 * 3.1  資產負債表
		 * 3.2 損益表
		 * 3.3 現金流量表
		 * 3.4 年報出來時，要重新擷取資料
		 * 4. 總經資料
		 * 4.1 台灣> BuildMacroEconomicData
		 * 4.1.1> 經建會> CepdParser
		 * 4.1.2> 央行> GetCbcMoney and GetDemandDeposit
		 * 4.1.3> 台灣加權指數> TwseIndex
		 * 4.2 美國> BuildAmericanMacroData
		 * 4.2.1>補UMCSENT(密西根大學消費者信心指數)
		 * 4.2.3>S&P 500 and CRB Index>SP500Main
		 * 4.2.3.1>補CRB Index
		 * 4.3> ISM指數> Build PMIndex
		 ************/
	}
	public void insertBatch()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		BasicStockDao dao = (BasicStockDao)context.getBean("basicStockDao");
		list = new ImportBasicStock().getBasicStockList();
		dao.insertBatch(list);
	}
}
