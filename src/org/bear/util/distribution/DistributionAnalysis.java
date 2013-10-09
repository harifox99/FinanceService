package org.bear.util.distribution;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bear.dao.JdbcStockDistributionDao;
import org.bear.dao.JdbcThreeBigDao;
import org.bear.entity.StockDistributionEntity;
import org.bear.entity.ThreeBigEntity;
import org.bear.journal.wrapper.DistributionWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class DistributionAnalysis 
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	JdbcStockDistributionDao stockDistributionDao = (JdbcStockDistributionDao)context.getBean("stockDistributionDao");
	JdbcThreeBigDao threeBigDao = (JdbcThreeBigDao)context.getBean("threeBigDao");
	public List<DistributionWrapper> getDistribution(String stockID, int duration)
	{		
		List <DistributionWrapper> distributionWrapperList = new ArrayList<DistributionWrapper>();
		List <StockDistributionEntity> distributionList = stockDistributionDao.latest(stockID, duration);
		List <ThreeBigEntity> threeBigList = threeBigDao.latest(stockID, duration);
		
		for (int i = 0; i < distributionList.size()-1; i++)
		{
			DistributionWrapper wrapper = new DistributionWrapper();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM"); 
			wrapper.setYearMonth(dateFormat.format(distributionList.get(i).getYearMonth()));
			//散戶
			wrapper.setRetailInvestor(
			distributionList.get(i).getD1() - distributionList.get(i+1).getD1() +
			distributionList.get(i).getD1000() - distributionList.get(i+1).getD1000() +
			distributionList.get(i).getD5000() - distributionList.get(i+1).getD5000() +
			distributionList.get(i).getD10000() - distributionList.get(i+1).getD10000() +
			distributionList.get(i).getD15000() - distributionList.get(i+1).getD15000() +
			distributionList.get(i).getD20000() - distributionList.get(i+1).getD20000() +
			distributionList.get(i).getD30000() - distributionList.get(i+1).getD30000() +
			distributionList.get(i).getD40000() - distributionList.get(i+1).getD40000() +
			distributionList.get(i).getD50000() - distributionList.get(i+1).getD50000());
			//中實戶
			wrapper.setMediumInvestor(
			distributionList.get(i).getD100000() - distributionList.get(i+1).getD100000() +
			distributionList.get(i).getD200000() - distributionList.get(i+1).getD200000());
			//大戶
			wrapper.setMajorInvestor(
			distributionList.get(i).getD400000() - distributionList.get(i+1).getD400000() +
			distributionList.get(i).getD600000() - distributionList.get(i+1).getD600000() + 
			distributionList.get(i).getD800000() - distributionList.get(i+1).getD800000());
			//大股東
			wrapper.setMajorShareholders(
			distributionList.get(i).getD600000() - distributionList.get(i+1).getD600000() +
			distributionList.get(i).getD800000() - distributionList.get(i+1).getD800000() + 
			distributionList.get(i).getD1000000() - distributionList.get(i+1).getD1000000());
			//三大法人
			wrapper.setThreeBig(threeBigList.get(i).getQuantity());
			//經理人(董監持股要20號才有，查不到先補0)
			if (threeBigList.get(i).getManager() == 0)
				wrapper.setManager(0);
			else
				wrapper.setManager(threeBigList.get(i).getManager() - threeBigList.get(i+1).getManager());
			//董監(同上)
			if (threeBigList.get(i).getSupervisor() == 0)
				wrapper.setSupervisors(0);
			else
				wrapper.setSupervisors(threeBigList.get(i).getSupervisor() - threeBigList.get(i+1).getSupervisor());
			//大大股東
			if (threeBigList.get(i).getStrongStockHolder() == 0)
				wrapper.setStrongStockHolder(0);
			else
				wrapper.setStrongStockHolder(threeBigList.get(i).getStrongStockHolder() - threeBigList.get(i+1).getStrongStockHolder());
			distributionWrapperList.add(wrapper);
		}
		distributionWrapperList = this.reverse(distributionWrapperList);
		return distributionWrapperList;
	}
	
	private List<DistributionWrapper> reverse(List<DistributionWrapper> distributionWrapperList)
	{
		List <DistributionWrapper> reverseList = new ArrayList<DistributionWrapper>();
		for (int i = distributionWrapperList.size()-1; i >= 0; i--)
		{
			//股數/1000=張數
			distributionWrapperList.get(i).setMajorInvestor(distributionWrapperList.get(i).getMajorInvestor()/1000);
			distributionWrapperList.get(i).setMajorShareholders(distributionWrapperList.get(i).getMajorShareholders()/1000);
			distributionWrapperList.get(i).setManager(distributionWrapperList.get(i).getManager()/1000);
			distributionWrapperList.get(i).setMediumInvestor(distributionWrapperList.get(i).getMediumInvestor()/1000);
			distributionWrapperList.get(i).setRetailInvestor(distributionWrapperList.get(i).getRetailInvestor()/1000);
			distributionWrapperList.get(i).setStrongStockHolder(distributionWrapperList.get(i).getStrongStockHolder()/1000);
			distributionWrapperList.get(i).setSupervisors(distributionWrapperList.get(i).getSupervisors()/1000);
			distributionWrapperList.get(i).setThreeBig(distributionWrapperList.get(i).getThreeBig()/1000);
			reverseList.add(distributionWrapperList.get(i));
		}
		return reverseList;
	}
	
	public static void main (String args[])
	{
		DistributionAnalysis analysis = new DistributionAnalysis();
		List<DistributionWrapper> list = analysis.getDistribution("4702", 12);
	}
	
}
