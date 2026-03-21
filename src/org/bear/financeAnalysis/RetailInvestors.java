package org.bear.financeAnalysis;
import java.util.List;

import org.bear.dao.JuristicDailyReportDao;
import org.bear.entity.JuristicDailyEntity;
import org.bear.entity.RetailInvestorsEntity;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 計算散戶指標
 * @author edward
 *
 */
public class RetailInvestors 
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	JuristicDailyReportDao juristicDailyReportDao = (JuristicDailyReportDao)context.getBean("juristicDailyReportDao");
	public List<RetailInvestorsEntity> getRetailIndex(int size)
	{
		List<RetailInvestorsEntity> listRetail = juristicDailyReportDao.findRetailInvestors(size);
		for (int i = 0; i < listRetail.size(); i++)
		{
			//計算散戶指標
			int diff = listRetail.get(i).getTotalMtx()-listRetail.get(i).getInstitutionalMtx();
			double retailRate = (double)diff/listRetail.get(i).getTotalMtx()-1;
			retailRate = retailRate * 100;
			retailRate = StringUtil.setPointLength(retailRate);
			listRetail.get(i).setRetailRate(retailRate);
			//擷取大盤指數
			JuristicDailyEntity entity = juristicDailyReportDao.findByDate(listRetail.get(i).getExchangeDate());
			listRetail.get(i).setTwseIndex(entity.getTwseIndex());
			listRetail.get(i).setAxisDate(listRetail.get(i).getExchangeDate().toString());
		}
		return listRetail;
	}
}
