package org.bear.financeAnalysis;
import java.util.*;

import org.bear.entity.BalanceSheetEntity;
import org.bear.entity.CashConversionCycleWrapper;
import org.bear.entity.CashFlowsEntity;
import org.bear.entity.CashFlowsWrapper;
import org.bear.entity.EarningPowerWrapper;
import org.bear.entity.EarningQualityWrapper;
import org.bear.entity.FinancialStructureAnalysisWrapper;
import org.bear.entity.IncomeStatementEntity;
import org.bear.entity.LongTermInvestmentWrapper;
import org.bear.entity.NonOperatingAnalysisWrapper;
import org.bear.entity.PerShareWrapper;
import org.bear.entity.ROEAnalysisWrapper;
import org.bear.entity.RiskMapWrapper;
import org.bear.entity.ShortTermLiquidityWrapper;
import org.bear.util.CalculateCCC;
import org.bear.util.CalculateEarningQuality;
import org.bear.util.CalculateROEAnalysis;
import org.bear.util.CalculateRiskMap;
import org.bear.util.StringUtil;
import org.bear.dao.BalanceSheetDao;
import org.bear.dao.CashFlowsDao;
import org.bear.dao.IncomeStatementDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FinanceUtil
{
	public FinanceUtil()
	{
		//earningPowerList = new ArrayList<EarningPowerWrapper>(); 
	}
	/**
	 * 獲利能力分析季報表
	 * @param stockID
	 * @param year
	 * @param seasons
	 * @return
	 */
	public List<EarningPowerWrapper> getEarningPowerBySeason(String stockID, String year, String seasons)
	{
		IncomeStatementDao dao;
		List <EarningPowerWrapper> earningPowerList = new ArrayList<EarningPowerWrapper>();
		EarningPowerWrapper wrapper;
		List <IncomeStatementEntity> incomeStatementList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
		incomeStatementList = dao.findDataBySeason(stockID, year, seasons);
		for (int i = 0; i < incomeStatementList.size(); i++)
		{
			double ratioNumber;
			wrapper = new EarningPowerWrapper();
			//毛利率			
			ratioNumber = (double) incomeStatementList.get(i).getGrossProfit()*100/incomeStatementList.get(i).getOperatingRevenue();
			wrapper.setGrossProfitRatio(StringUtil.setPointLength(ratioNumber));
			//營業利益率
			ratioNumber = (double) incomeStatementList.get(i).getOperatingIncome()*100/incomeStatementList.get(i).getOperatingRevenue();
			wrapper.setOperatingProfitRatio(StringUtil.setPointLength(ratioNumber));
			//稅前淨利率
			ratioNumber = (double) incomeStatementList.get(i).getPreTaxIncome()*100/incomeStatementList.get(i).getOperatingRevenue();
			wrapper.setIncomeBeforeTaxRatio(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(incomeStatementList.get(i).getYear() + "-" + incomeStatementList.get(i).getSeasons());
			//wrapper.setSeasons(incomeStatementList.get(i).getSeasons());
			earningPowerList.add(wrapper);
		}
		return earningPowerList;
	}
	/**
	 * 獲利能力分析年報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<EarningPowerWrapper> getEarningPowerByYear(String stockID, String year)
	{
		IncomeStatementDao dao;
		List <EarningPowerWrapper> earningPowerList = new ArrayList<EarningPowerWrapper>();
		EarningPowerWrapper wrapper;
		List <IncomeStatementEntity> incomeStatementList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
		incomeStatementList = dao.findDataByYear(stockID, year);
		for (int i = 0; i < incomeStatementList.size(); i++)
		{
			double ratioNumber;
			wrapper = new EarningPowerWrapper();
			//毛利率
			ratioNumber = (double) incomeStatementList.get(i).getGrossProfit()*100/incomeStatementList.get(i).getOperatingRevenue();
			wrapper.setGrossProfitRatio(StringUtil.setPointLength(ratioNumber));
			//營業利益率
			ratioNumber = (double) incomeStatementList.get(i).getOperatingIncome()*100/incomeStatementList.get(i).getOperatingRevenue();
			wrapper.setOperatingProfitRatio(StringUtil.setPointLength(ratioNumber));
			//稅前淨利率
			ratioNumber = (double) incomeStatementList.get(i).getPreTaxIncome()*100/incomeStatementList.get(i).getOperatingRevenue();
			wrapper.setIncomeBeforeTaxRatio(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(incomeStatementList.get(i).getYear());
			earningPowerList.add(wrapper);
		}
		return earningPowerList;
	}
	/**
	 * 營業外收支穩定度分析年報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<NonOperatingAnalysisWrapper> getNonOperatingAnalysisByYear(String stockID, String year)
	{
		IncomeStatementDao dao;
		List <NonOperatingAnalysisWrapper> nonOperatingList = new ArrayList<NonOperatingAnalysisWrapper>();
		NonOperatingAnalysisWrapper wrapper;
		List <IncomeStatementEntity> incomeStatementList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
		incomeStatementList = dao.findDataByYear(stockID, year);
		for (int i = 0; i < incomeStatementList.size(); i++)
		{
			double ratioNumber;
			wrapper = new NonOperatingAnalysisWrapper();
			//營業外收入占稅前盈餘比率
			ratioNumber = (double) incomeStatementList.get(i).getNonOperatingRevenue()*100/incomeStatementList.get(i).getPreTaxIncome();
			wrapper.setNonOperatingRevenue(StringUtil.setPointLength(ratioNumber));
			//營業外支出占稅前盈餘比率
			ratioNumber = (double) incomeStatementList.get(i).getNonOperatingExpense()*100/incomeStatementList.get(i).getPreTaxIncome();
			wrapper.setNonOperatingExpense(StringUtil.setPointLength(ratioNumber));
			//營業外收支占稅前盈餘比率
			ratioNumber = (double) (incomeStatementList.get(i).getNonOperatingRevenue() - incomeStatementList.get(i).getNonOperatingExpense()) * 100 / incomeStatementList.get(i).getPreTaxIncome();
			wrapper.setNonOperatingTotal(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(incomeStatementList.get(i).getYear());
			nonOperatingList.add(wrapper);
		}
		return nonOperatingList;
	}
	/**
	 * 營業外收支穩定度分析季報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<NonOperatingAnalysisWrapper> getNonOperatingAnalysisBySeason(String stockID, String year, String seasons)
	{
		IncomeStatementDao dao;
		List <NonOperatingAnalysisWrapper> nonOperatingList = new ArrayList<NonOperatingAnalysisWrapper>();
		NonOperatingAnalysisWrapper wrapper;
		List <IncomeStatementEntity> incomeStatementList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
		incomeStatementList = dao.findDataBySeason(stockID, year, seasons);
		for (int i = 0; i < incomeStatementList.size(); i++)
		{
			double ratioNumber;
			wrapper = new NonOperatingAnalysisWrapper();
			//營業外收入占稅前盈餘比率
			ratioNumber = (double) incomeStatementList.get(i).getNonOperatingRevenue()*100/incomeStatementList.get(i).getPreTaxIncome();
			wrapper.setNonOperatingRevenue(StringUtil.setPointLength(ratioNumber));
			//營業外支出占稅前盈餘比率
			ratioNumber = (double) incomeStatementList.get(i).getNonOperatingExpense()*100/incomeStatementList.get(i).getPreTaxIncome();
			wrapper.setNonOperatingExpense(StringUtil.setPointLength(ratioNumber));
			//營業外收支占稅前盈餘比率
			ratioNumber = (double) (incomeStatementList.get(i).getNonOperatingRevenue() - incomeStatementList.get(i).getNonOperatingExpense()) * 100 / incomeStatementList.get(i).getPreTaxIncome();
			wrapper.setNonOperatingTotal(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(incomeStatementList.get(i).getYear() + "-" + incomeStatementList.get(i).getSeasons());
			nonOperatingList.add(wrapper);
		}
		return nonOperatingList;
	}
	/**
	 * 每股營收盈餘分析年報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<PerShareWrapper> getPerShareByYear(String stockID, String year)
	{
		IncomeStatementDao dao;
		List <PerShareWrapper> revenueIncomeList = new ArrayList<PerShareWrapper>();
		PerShareWrapper wrapper;
		List <IncomeStatementEntity> incomeStatementList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
		incomeStatementList = dao.findDataByYear(stockID, year);
		for (int i = 0; i < incomeStatementList.size(); i++)
		{
			double ratioNumber;
			wrapper = new PerShareWrapper();
			//每股營收
			ratioNumber = (double) incomeStatementList.get(i).getOperatingRevenue()/incomeStatementList.get(i).getWghtAvgStocks()*10;
			wrapper.setRevenuePerShare(StringUtil.setPointLength(ratioNumber));
			//每股營業利益
			ratioNumber = (double) incomeStatementList.get(i).getOperatingIncome()/incomeStatementList.get(i).getWghtAvgStocks()*10;
			wrapper.setIncomePerShare(StringUtil.setPointLength(ratioNumber));
			//EPS
			wrapper.setEps(incomeStatementList.get(i).getEps());
			//設定期別
			wrapper.setYear(incomeStatementList.get(i).getYear());
			revenueIncomeList.add(wrapper);
		}
		return revenueIncomeList;
	}
	/**
	 * 每股營收盈餘分析季報表
	 * @param stockID
	 * @param year
	 * @param seasons
	 * @return
	 */
	public List<PerShareWrapper> getPerShareBySeason(String stockID, String year, String seasons)
	{
		IncomeStatementDao dao;
		List <PerShareWrapper> revenueIncomeList = new ArrayList<PerShareWrapper>();
		PerShareWrapper wrapper;
		List <IncomeStatementEntity> incomeStatementList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
		incomeStatementList = dao.findDataBySeason(stockID, year, seasons);
		for (int i = 0; i < incomeStatementList.size(); i++)
		{
			double ratioNumber;
			wrapper = new PerShareWrapper();
			//每股營收
			ratioNumber = (double) incomeStatementList.get(i).getOperatingRevenue()/incomeStatementList.get(i).getWghtAvgStocks()*10;
			wrapper.setRevenuePerShare(StringUtil.setPointLength(ratioNumber));
			//每股營業利益
			ratioNumber = (double) incomeStatementList.get(i).getOperatingIncome()/incomeStatementList.get(i).getWghtAvgStocks()*10;
			wrapper.setIncomePerShare(StringUtil.setPointLength(ratioNumber));
			//EPS
			wrapper.setEps(incomeStatementList.get(i).getEps());
			//設定期別
			wrapper.setYear(incomeStatementList.get(i).getYear() + "-" + incomeStatementList.get(i).getSeasons());
			revenueIncomeList.add(wrapper);
		}
		return revenueIncomeList;
	}
	/**
	 * 財務結構指標分析年報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<FinancialStructureAnalysisWrapper> getfinancialStructureAnalysisByYear(String stockID, String year)
	{
		BalanceSheetDao dao;
		List <FinancialStructureAnalysisWrapper> financialStructureList = new ArrayList<FinancialStructureAnalysisWrapper>();
		FinancialStructureAnalysisWrapper wrapper;
		List <BalanceSheetEntity> balanceSheetList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		balanceSheetList = dao.findDataByYear(stockID, year);
		for (int i = 0; i < balanceSheetList.size(); i++)
		{
			double ratioNumber;
			wrapper = new FinancialStructureAnalysisWrapper();
			//自有資本比率
			ratioNumber = (double) balanceSheetList.get(i).getStockholdersEquity()/balanceSheetList.get(i).getTotalAssets()*100;
			wrapper.setHoldersEquityRatio(StringUtil.setPointLength(ratioNumber));
			//負債比率
			ratioNumber = (double) balanceSheetList.get(i).getTotalLiability()/balanceSheetList.get(i).getTotalAssets()*100;
			wrapper.setDebtRatio(StringUtil.setPointLength(ratioNumber));
			//權益乘數
			ratioNumber = (double) balanceSheetList.get(i).getTotalAssets()/balanceSheetList.get(i).getStockholdersEquity();
			wrapper.setEquityMultiplier(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(balanceSheetList.get(i).getYear());
			financialStructureList.add(wrapper);
		}
		return financialStructureList;
	}
	/**
	 * 財務結構指標分析季報表
	 * @param stockID
	 * @param year
	 * @param seasons
	 * @return
	 */
	public List<FinancialStructureAnalysisWrapper> getfinancialStructureAnalysisBySeason(String stockID, String year, String seasons)
	{
		BalanceSheetDao dao;
		List <FinancialStructureAnalysisWrapper> financialStructureList = new ArrayList<FinancialStructureAnalysisWrapper>();
		FinancialStructureAnalysisWrapper wrapper;
		List <BalanceSheetEntity> balanceSheetList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		balanceSheetList = dao.findDataBySeason(stockID, year, seasons);
		for (int i = 0; i < balanceSheetList.size(); i++)
		{
			double ratioNumber;
			wrapper = new FinancialStructureAnalysisWrapper();
			//自有資本比率
			ratioNumber = (double) balanceSheetList.get(i).getStockholdersEquity()/balanceSheetList.get(i).getTotalAssets()*100;
			wrapper.setHoldersEquityRatio(StringUtil.setPointLength(ratioNumber));
			//負債比率
			ratioNumber = (double) balanceSheetList.get(i).getTotalLiability()/balanceSheetList.get(i).getTotalAssets()*100;
			wrapper.setDebtRatio(StringUtil.setPointLength(ratioNumber));
			//權益乘數
			ratioNumber = (double) balanceSheetList.get(i).getTotalAssets()/balanceSheetList.get(i).getStockholdersEquity();
			wrapper.setEquityMultiplier(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(balanceSheetList.get(i).getYear() + "-" + balanceSheetList.get(i).getSeasons());
			financialStructureList.add(wrapper);
		}
		return financialStructureList;
	}
	/**
	 * 短期償債能力分析年報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<ShortTermLiquidityWrapper> getShortTermLiquidityByYear(String stockID, String year)
	{
		BalanceSheetDao dao;
		List <ShortTermLiquidityWrapper> shortTermList = new ArrayList<ShortTermLiquidityWrapper>();
		ShortTermLiquidityWrapper wrapper;
		List <BalanceSheetEntity> balanceSheetList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		balanceSheetList = dao.findDataByYear(stockID, year);
		for (int i = 0; i < balanceSheetList.size(); i++)
		{
			double ratioNumber;
			wrapper = new ShortTermLiquidityWrapper();
			//流動比率
			ratioNumber = (double) balanceSheetList.get(i).getCurrentAssets()/balanceSheetList.get(i).getCurrentLiability()*100;
			wrapper.setCurrentRatio(StringUtil.setPointLength(ratioNumber));
			//速動比率
			ratioNumber = (double) (balanceSheetList.get(i).getCurrentAssets() - balanceSheetList.get(i).getPrepaidExpense() - balanceSheetList.get(i).getInventory())
			              /balanceSheetList.get(i).getCurrentLiability()*100;
			wrapper.setQuickRatio(StringUtil.setPointLength(ratioNumber));
			//應收帳款及存貨佔總資產比率
			ratioNumber = (double) (balanceSheetList.get(i).getReceivable() + balanceSheetList.get(i).getInventory()) / balanceSheetList.get(i).getTotalAssets()*100;
			wrapper.setSpecialRatio(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(balanceSheetList.get(i).getYear());
			shortTermList.add(wrapper);
		}
		return shortTermList;
	}
	/**
	 * 短期償債能力分析季報表
	 * @param stockID
	 * @param year
	 * @param seasons
	 * @return
	 */
	public List<ShortTermLiquidityWrapper> getShortTermLiquidityBySeason(String stockID, String year, String seasons)
	{
		BalanceSheetDao dao;
		List <ShortTermLiquidityWrapper> shortTermList = new ArrayList<ShortTermLiquidityWrapper>();
		ShortTermLiquidityWrapper wrapper;
		List <BalanceSheetEntity> balanceSheetList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		balanceSheetList = dao.findDataBySeason(stockID, year, seasons);
		for (int i = 0; i < balanceSheetList.size(); i++)
		{
			double ratioNumber;
			wrapper = new ShortTermLiquidityWrapper();
			//流動比率
			ratioNumber = (double) balanceSheetList.get(i).getCurrentAssets()/balanceSheetList.get(i).getCurrentLiability()*100;
			wrapper.setCurrentRatio(StringUtil.setPointLength(ratioNumber));
			//速動比率
			ratioNumber = (double) (balanceSheetList.get(i).getCurrentAssets() - balanceSheetList.get(i).getPrepaidExpense() - balanceSheetList.get(i).getInventory())
			              /balanceSheetList.get(i).getCurrentLiability()*100;
			wrapper.setQuickRatio(StringUtil.setPointLength(ratioNumber));
			//應收帳款及存貨佔總資產比率
			ratioNumber = (double) (balanceSheetList.get(i).getReceivable() + balanceSheetList.get(i).getInventory()) / balanceSheetList.get(i).getTotalAssets()*100;
			wrapper.setSpecialRatio(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(balanceSheetList.get(i).getYear() + "-" + balanceSheetList.get(i).getSeasons());
			shortTermList.add(wrapper);
		}
		return shortTermList;
	}
	/**
	 * 長期投資佔總資產比率年報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<LongTermInvestmentWrapper> getLongTermInvestmentByYear(String stockID, String year)
	{
		BalanceSheetDao dao;
		List <LongTermInvestmentWrapper> longTermList = new ArrayList<LongTermInvestmentWrapper>();
		LongTermInvestmentWrapper wrapper;
		List <BalanceSheetEntity> balanceSheetList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		balanceSheetList = dao.findDataByYear(stockID, year);
		for (int i = 0; i < balanceSheetList.size(); i++)
		{
			double ratioNumber;
			wrapper = new LongTermInvestmentWrapper();
			//長期投資佔總資產比率
			ratioNumber = (double) balanceSheetList.get(i).getLongTermInvestment()/balanceSheetList.get(i).getTotalAssets()*100;
			wrapper.setLongTermTotalAssetRatio(StringUtil.setPointLength(ratioNumber));
			//長期資金佔固定資產比率
			ratioNumber = (double) (balanceSheetList.get(i).getLongTermLiability()+balanceSheetList.get(i).getStockholdersEquity())/balanceSheetList.get(i).getFixedAssets()*100;
			wrapper.setLongTermCapitalRatio(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(balanceSheetList.get(i).getYear());
			longTermList.add(wrapper);
		}
		return longTermList;
	}
	/**
	 * 長期投資佔總資產比率季報表
	 * @param stockID
	 * @param year
	 * @param seasons
	 * @return
	 */
	public List<LongTermInvestmentWrapper> getLongTermInvestmentBySeason(String stockID, String year, String seasons)
	{
		BalanceSheetDao dao;
		List <LongTermInvestmentWrapper> longTermList = new ArrayList<LongTermInvestmentWrapper>();
		LongTermInvestmentWrapper wrapper;
		List <BalanceSheetEntity> balanceSheetList;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		balanceSheetList = dao.findDataBySeason(stockID, year, seasons);
		for (int i = 0; i < balanceSheetList.size(); i++)
		{
			double ratioNumber;
			wrapper = new LongTermInvestmentWrapper();
			//長期投資佔總資產比率
			ratioNumber = (double) balanceSheetList.get(i).getLongTermInvestment()/balanceSheetList.get(i).getTotalAssets()*100;
			wrapper.setLongTermTotalAssetRatio(StringUtil.setPointLength(ratioNumber));
			//長期資金佔固定資產比率
			ratioNumber = (double) (balanceSheetList.get(i).getLongTermLiability()+balanceSheetList.get(i).getStockholdersEquity())/balanceSheetList.get(i).getFixedAssets()*100;
			wrapper.setLongTermCapitalRatio(StringUtil.setPointLength(ratioNumber));
			//設定期別
			wrapper.setYear(balanceSheetList.get(i).getYear() + "-" + balanceSheetList.get(i).getSeasons());
			longTermList.add(wrapper);
		}
		return longTermList;
	}
	/**
	 * 盈餘品質分析年報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<EarningQualityWrapper> getEarningQualityByYear(String stockID, String year)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		//資產負債表
		BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		List <BalanceSheetEntity> balanceSheetList;
		//損益表
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
		List <IncomeStatementEntity> incomeStatementList;
		/***********************************************/
		List <EarningQualityWrapper> earningQualityList;
		balanceSheetList = balanceSheetDao.findDataByYear(stockID, year);
		incomeStatementList = incomeStatementDao.findDataByYear(stockID, year);
		earningQualityList = new CalculateEarningQuality(balanceSheetList, incomeStatementList, true).getEarningQuality(); 
		return earningQualityList;
	}
	/**
	 * 盈餘品質分析季報表
	 * @param stockID
	 * @param year
	 * @param seasons
	 * @return
	 */
	public List<EarningQualityWrapper> getEarningQualityBySeason(String stockID, String year, String seasons)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		//資產負債表
		BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		List <BalanceSheetEntity> balanceSheetList;
		//損益表
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
		List <IncomeStatementEntity> incomeStatementList;
		/***********************************************/
		List <EarningQualityWrapper> earningQualityList;
		balanceSheetList = balanceSheetDao.findDataBySeason(stockID, year, seasons);
		incomeStatementList = incomeStatementDao.findDataBySeason(stockID, year, seasons);
		earningQualityList = new CalculateEarningQuality(balanceSheetList, incomeStatementList, false).getEarningQuality(); 
		return earningQualityList;
	}
	/**
	 * ROE細部分析年報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<ROEAnalysisWrapper> getROEAnalysisByYear(String stockID, String year)
	{
		//平均資產總額
		int averageAsset = 0;
		//銷貨收入
		int operatingRevenue = 0;
		//稅後淨利
		int netIncome = 0;
		//平均股東權益
		int averageEquity = 0;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		//資產負債表
		BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		List <BalanceSheetEntity> balanceSheetList;
		//損益表
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
		List <IncomeStatementEntity> incomeStatementList;
		/***********************************************/
		List <ROEAnalysisWrapper> roeAnalysisList;
		balanceSheetList = balanceSheetDao.findDataByYear(stockID, year);
		incomeStatementList = incomeStatementDao.findDataByYear(stockID, year);
		roeAnalysisList = new CalculateROEAnalysis(balanceSheetList, incomeStatementList, true).getROEAnalysis();
		//計算近四季資料
		balanceSheetList = balanceSheetDao.findDataBySeason(stockID, "2009", "01");
		incomeStatementList = incomeStatementDao.findDataBySeason(stockID, "2009", "01");
		for (int i = balanceSheetList.size() - 1; i >= balanceSheetList.size() - 4; i--)
		{
			/* 設定最近四季ROE平均資料之期別
			if (i == balanceSheetList.size() - 1)
			{
				year = balanceSheetList.get(i).getYear();
				int intYear = Integer.parseInt(year);
				intYear++;
				year = String.valueOf(intYear);
			}*/
			operatingRevenue += incomeStatementList.get(i).getOperatingRevenue();
			netIncome += incomeStatementList.get(i).getNetIncome();
			averageAsset += balanceSheetList.get(i).getTotalAssets();
			averageEquity += balanceSheetList.get(i).getStockholdersEquity();
		}
		double ratioNumber;
		averageAsset = averageAsset/4;
		averageEquity = averageEquity/4;
		ROEAnalysisWrapper wrapper = new ROEAnalysisWrapper();
		wrapper.setYear("過去四季平均");
		//過去四季純益率
		ratioNumber = (double)netIncome/operatingRevenue*100;
		ratioNumber = StringUtil.setPointLength(ratioNumber);
		wrapper.setNetProfitMargin(ratioNumber);
		//過去四季總資產週轉率
		ratioNumber = (double)operatingRevenue/averageAsset*100;
		ratioNumber = StringUtil.setPointLength(ratioNumber);
		wrapper.setTotalAssetTurnover(ratioNumber);
		//過去四季權益乘數
		ratioNumber = (double)averageAsset/averageEquity;
		ratioNumber = StringUtil.setPointLength(ratioNumber);
		wrapper.setEquityMultiplier(ratioNumber);
		//過去四季ROE
		ratioNumber = (double)netIncome/averageEquity*100;
		ratioNumber = StringUtil.setPointLength(ratioNumber);
		wrapper.setRoe(ratioNumber);
		roeAnalysisList.add(wrapper);
		return roeAnalysisList;
	}
	/**
	 * ROE細部分析季報表
	 * @param stockID
	 * @param year
	 * @param seasons
	 * @return
	 */
	public List<ROEAnalysisWrapper> getROEAnalysisBySeason(String stockID, String year, String seasons)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		//資產負債表
		BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		List <BalanceSheetEntity> balanceSheetList;
		//損益表
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
		List <IncomeStatementEntity> incomeStatementList;
		/***********************************************/
		List <ROEAnalysisWrapper> roeAnalysisList;
		balanceSheetList = balanceSheetDao.findDataBySeason(stockID, year, seasons);
		incomeStatementList = incomeStatementDao.findDataBySeason(stockID, year, seasons);
		roeAnalysisList = new CalculateROEAnalysis(balanceSheetList, incomeStatementList, false).getROEAnalysis(); 
		return roeAnalysisList;
	}
	/**
	 * 現金流量分析
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<CashFlowsWrapper> getCashFlowByYear(String stockID, String year, String season)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		//現金流量表
		CashFlowsDao cashFlowsDao = (CashFlowsDao)context.getBean("basicCashFlowsDao");
		List <CashFlowsEntity> cashFlowsList;
		//損益表
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
		List <IncomeStatementEntity> incomeStatementList;
		//資產負債
		BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");;
		List <BalanceSheetEntity> balanceSheetList;
		/***********************************************/
		List <CashFlowsWrapper> wrapperList = new ArrayList<CashFlowsWrapper>();
		CashFlowsWrapper wrapper;
		incomeStatementList = incomeStatementDao.findDataBySeason(stockID, year, season);
		cashFlowsList = cashFlowsDao.findDataBySeason(stockID, year, season);
		balanceSheetList = balanceSheetDao.findDataBySeason(stockID, year, season);
		for (int i = 0; i < cashFlowsList.size(); i++)
		{
			wrapper = new CashFlowsWrapper();
			//營運活動之現金流量
			wrapper.setOperatingActivity(cashFlowsList.get(i).getOperatingActivity());
			//投資活動之現金流量
			wrapper.setInvestingActivity(cashFlowsList.get(i).getInvestingActivity());
			//自由現金流量
			wrapper.setFreeCashFlow(cashFlowsList.get(i).getFreeCashFlow());
			//理財活動之現金流量
			wrapper.setFinancingActivity(cashFlowsList.get(i).getFinancingActivity());
			//本期產生現金流量
			wrapper.setNetCashFlows(cashFlowsList.get(i).getNetCashFlows());
			//設定期別
			wrapper.setYear(balanceSheetList.get(i).getYear() + "-" + balanceSheetList.get(i).getSeasons());
			/* 移除錯誤的現金流量計算法，可能是當初資料不足，所以使用舊的計算方法，當資料補足之後，改用新的計算方法, 2013/11/05
			//計算最新一年的累計損益資料
			if (i == cashFlowsList.size() - 1)
			{
				String lasterYear = cashFlowsList.get(i).getYear();
				//累計稅後純益
				int netIncome = 0;
				incomeStatementList = incomeStatementDao.findDataBySeason(stockID, lasterYear, "01");
				for (int j = 0; j < incomeStatementList.size(); j++)
				{
					netIncome += incomeStatementList.get(j).getNetIncome();
				}
				//累計流動負債
				int currentDebt = 0;
				balanceSheetList = balanceSheetDao.findDataBySeason(stockID, lasterYear, "01");
				currentDebt = balanceSheetList.get(balanceSheetList.size()-1).getCurrentLiability();			
				//現金流量比率
				double ratioNumber = (double) cashFlowsList.get(i).getOperatingActivity()/currentDebt*100;
				wrapper.setCashFlowToCurrentDebt(StringUtil.setPointLength(ratioNumber));
				//稅後純益
				wrapper.setIncomeSummary(netIncome);
				//營運活動之現金佔稅後純益比率
				ratioNumber = (double) cashFlowsList.get(i).getOperatingActivity()/netIncome*100;
				wrapper.setCashNetRatio(StringUtil.setPointLength(ratioNumber));	
				//營運活動現金流量與會計盈餘落差
				wrapper.setDifference(cashFlowsList.get(i).getOperatingActivity() - netIncome);				
			}
			else
			{*/
			//現金流量比率
			double ratioNumber = (double) cashFlowsList.get(i).getOperatingActivity()/balanceSheetList.get(i).getCurrentLiability()*100;
			wrapper.setCashFlowToCurrentDebt(StringUtil.setPointLength(ratioNumber));
			//稅後純益
			wrapper.setIncomeSummary(incomeStatementList.get(i).getNetIncome());
			//營運活動之現金佔稅後純益比率
			ratioNumber = (double)cashFlowsList.get(i).getOperatingActivity()/incomeStatementList.get(i).getNetIncome()*100;
			wrapper.setCashNetRatio(StringUtil.setPointLength(ratioNumber));				
			//營運活動現金流量與會計盈餘落差
			wrapper.setDifference(cashFlowsList.get(i).getOperatingActivity() - incomeStatementList.get(i).getNetIncome());								
			//}
			wrapperList.add(wrapper);
		}		
		return wrapperList;
	}
	/**
	 * 現金轉換循環年報表
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<CashConversionCycleWrapper> getCCCByYear(String stockID, String year)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		//資產負債表
		BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		List <BalanceSheetEntity> balanceSheetList;
		//損益表
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
		List <IncomeStatementEntity> incomeStatementList;
		/***********************************************/
		List <CashConversionCycleWrapper> cccList;
		balanceSheetList = balanceSheetDao.findDataByYear(stockID, year);
		incomeStatementList = incomeStatementDao.findDataByYear(stockID, year);
		cccList = new CalculateCCC(balanceSheetList, incomeStatementList, true).getCCC();
		return cccList;
	}
	/**
	 * 現金轉換循環季報表
	 * @param stockID
	 * @param year
	 * @param seasons
	 * @return
	 */
	public List<CashConversionCycleWrapper> getCCCBySeason(String stockID, String year, String seasons)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		//資產負債表
		BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
		List <BalanceSheetEntity> balanceSheetList;
		//損益表
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
		List <IncomeStatementEntity> incomeStatementList;
		/***********************************************/
		List <CashConversionCycleWrapper> cccList;
		balanceSheetList = balanceSheetDao.findDataBySeason(stockID, year, seasons);
		incomeStatementList = incomeStatementDao.findDataBySeason(stockID, year, seasons);
		cccList = new CalculateCCC(balanceSheetList, incomeStatementList, false).getCCC();
		return cccList;
	}
	/**
	 * 投資風險地圖
	 * @param stockID
	 * @param year
	 * @return
	 */
	public List<RiskMapWrapper> getRiskMapByYear(String stockID, String year)
	{		
		List <RiskMapWrapper> riskMapList = new CalculateRiskMap().getRiskMap(stockID, year);
		return riskMapList;
	}
}
