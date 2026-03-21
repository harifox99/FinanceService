/**
 * 
 */
package org.bear.financeAnalysis;
import java.util.List;
import org.bear.dao.FinancialDataDao;
import org.bear.entity.BuffettAnalysisWrapper;
import org.bear.entity.FinancialDataEntity;
import org.bear.entity.RiskMapWrapper;
import org.bear.parser.BasicDataParserCathay;
import org.bear.util.CalculateRiskMap;
import org.bear.util.GetURLCathayBasicData;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author edward
 *
 */
public class BuffettAnalysis 
{
	List <RiskMapWrapper> riskMapWrapperList;
	List <FinancialDataEntity> entityList;
	BuffettAnalysisWrapper wrapper;
	//計算ROE只需要過去五年+最新一年，共六筆資料
	final int arraySize = 6;
	public BuffettAnalysisWrapper getBuffettAnalysis(String stockID)
	{
		BuffettAnalysisWrapper wrapper = this.getBuffettData(stockID, 8, 2008);
		return wrapper;
	}
	public BuffettAnalysisWrapper getBuffettData(String stockID, int totalYear, int year)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		FinancialDataDao dao = (FinancialDataDao)context.getBean("basicFinancialDataDao");
		entityList = dao.findDataByYear(stockID, String.valueOf(year));
		//預設本益比
		final int per = 12;
		wrapper = new BuffettAnalysisWrapper();
		CalculateRiskMap riskMap = new CalculateRiskMap();
		riskMapWrapperList = riskMap.getRiskMap(stockID, String.valueOf(year));
		//預期ROE
		double roeRecently = 0;
		double roeFiveYears = 0;
		double ratioNumber;
		if (riskMapWrapperList.size() - arraySize >= 0)
		{
			for (int i = riskMapWrapperList.size() - 1; i >= riskMapWrapperList.size() - arraySize; i--)
			{
				//過去最新四季
				if (i == riskMapWrapperList.size() - 1)
				{
					roeRecently = riskMapWrapperList.get(i).getRoe();				
				}
				//過去五年
				else
				{
					roeFiveYears = roeFiveYears + riskMapWrapperList.get(i).getRoe();
				}			
			}
			roeFiveYears = roeFiveYears/5;
			//今年ROE < 平均ROE
			if (roeRecently < roeFiveYears)
				wrapper.setRoe(StringUtil.setPointLength(roeRecently));
			else //今年ROE > 平均ROE
				wrapper.setRoe(StringUtil.setPointLength( (roeFiveYears+roeRecently)/2) );
		}
		//資料不足以計算ROE
		else
			wrapper.setRoe(0.0);
		//每股淨值NAV
		GetURLCathayBasicData urlContent = new GetURLCathayBasicData(stockID);
		BasicDataParserCathay parser = new BasicDataParserCathay(urlContent.getContent(), stockID);
		parser.parse(2);
		wrapper.setNav(parser.getNav());
		try
		{
			Thread.sleep(1000);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		//股價淨值比PBR
		ratioNumber = parser.getPrice()/parser.getNav();
		ratioNumber = StringUtil.setPointLength(ratioNumber);
		wrapper.setPbr(ratioNumber);
		//合理股價 = ROE * NAV * PER
		ratioNumber = wrapper.getRoe() * wrapper.getNav() * per / 100;
		ratioNumber = StringUtil.setPointLength(ratioNumber);
		wrapper.setReasonablePrice(ratioNumber);
		//盈餘再投資率
		ratioNumber = riskMapWrapperList.get(riskMapWrapperList.size() - 1).getReinvestmentRate();
		ratioNumber = StringUtil.setPointLength(ratioNumber);
		wrapper.setReinvestmentRate(ratioNumber);
		//股價安全邊際上限
		ratioNumber = this.expectedRate(0.0, wrapper.getReasonablePrice());
		ratioNumber = StringUtil.setPointLength(ratioNumber);
		wrapper.setUpperBound(ratioNumber);
		//股價安全邊際下限
		ratioNumber = this.expectedRate(0.15, wrapper.getReasonablePrice());
		ratioNumber = StringUtil.setPointLength(ratioNumber);
		wrapper.setLowerBound(ratioNumber);
		wrapper.setPrice(parser.getPrice());
		return wrapper;
	}
	/**
	 * 
	 * @param rate 期望報酬率
	 * @param reasonablePrice 合理價
	 * @return
	 */
	public double expectedRate(double rate, double reasonablePrice)
	{
		double price = 0;
		for (int i = 0; i <= entityList.size() ; i++)
		{			
			if (i == entityList.size())
			{
				price = price + reasonablePrice/Math.pow(1 + rate, i+1);
			}
			else
			{
				price = price + entityList.get(i).getCashDiv()/Math.pow(1 + rate, i+1);
			}
		}
		return price;
	}
}
