package org.bear.financeAnalysis;
import java.util.*;

import org.bear.dao.JuristicDailyReportDao;
import org.bear.entity.JuristicDailyEntity;
import org.bear.entity.JuristicDailyReport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 三大法人交易日誌資料分析
 * @author edward
 *
 */
public class JuristicAnalysis 
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	JuristicDailyReportDao juristicDailyReportDao = (JuristicDailyReportDao)context.getBean("juristicDailyReportDao");
	public List<JuristicDailyReport> getJuristicReport(int size)
	{
		List<JuristicDailyEntity> list = juristicDailyReportDao.findLatestData(size);
		List<JuristicDailyReport> reportList = new ArrayList<JuristicDailyReport>();
		for (int i = 0; i < list.size(); i++)
		{
			this.foreignerComment(list.get(i));
		}
		return reportList;
	}
	private String foreignerComment(JuristicDailyEntity entity)
	{
		String message = "";
		long amount = entity.getAmount();
		int lot = entity.getLot() + entity.getSmallLot()/4;
		//買超金額在數十億以上&&多單超過萬口
		if (amount > 1000000000 && lot > 10000)
			message = "確定作多";
		//買超金額低於數億或小幅賣超&&多單超過萬口
		else if(amount > -500000000 && amount < 1000000000 && lot > 10000)
			message = "大盤隔日走多機率非常高";
		//同步大賣超
		else if(amount < -1000000000 && lot < -10000)
			message = "確定作空";
		//賣超低於數億元或小幅買超&&空單超過萬口
		else if (amount > -1000000000 && amount < 500000000 && lot < -10000)
			message = "留意開始做空";
		//未平倉多空單，數百至數千口，買賣超金額不大
		else if (amount > -500000000 && amount < 500000000 && lot > -5000 && lot < 5000)
			message = "中性，沒有方向";
		else 
			message = "無法判斷";
		return message;
	}
}
