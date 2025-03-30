package org.bear.cron;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.mail.Session;
import org.bear.dao.GoodInfoDao;
import org.bear.shortTrade.Orchid;
import org.bear.shortTrade.Vcp;
import org.bear.util.EmailUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CronVcp 
{
	public void goVcp(Set <String> kdGolden, String dateString)
	{
		//VCP and 蘭弦
		Vcp vcp = new Vcp();
		vcp.getContent(dateString);
		Set<String> vcpSets = vcp.getSets();
		Orchid orchid = new Orchid();
		orchid.getContent();
		Set<String> orchidSets = orchid.getSets();
		//Send Mail
		String smtpHostServer = "msr.hinet.net";
	    String emailID = "aluba0504@gmail.com";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.host", smtpHostServer);
	    props.put("mail.smtp.port", 587);
	    final String user = "love.wine@msa.hinet.net";
	    final String pass = "chtl@9191";   
	    Session session = Session.getInstance(props, null);
		EmailUtil.sendEmail(session, emailID, "每日選股日報:" + dateString, user, pass, kdGolden, vcpSets, orchidSets);
	}
	public static void main(String[] args)
	{
		new CronVcp().start();
	}
	public void start()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		GoodInfoDao goodInfoDao = (GoodInfoDao)context.getBean("goodInfoDao");	
		CronVcp vcp = new CronVcp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date today = new Date();        
		String dateString = dateFormat.format(today);
		dateString = "2025-03-24";
		//KD指標	
		Set <String> kdGolden = goodInfoDao.getData(dateString);
		if (kdGolden == null)
			kdGolden = new HashSet<String>();
		vcp.goVcp(kdGolden, dateString);
	}
}
