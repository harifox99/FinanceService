package org.bear.massData;

import org.bear.dao.BasicStockDao;
import org.bear.parser.OutStandingParser;
import org.bear.util.HttpUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 更新在外流通發行股數
 * @author bear
 *
 */
public class UpdateSharesOutstanding 
{
	String url = "https://mops.twse.com.tw/mops/web/ajax_t51sb01";
	String parameters;
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	BasicStockDao dao = (BasicStockDao)context.getBean("basicStockDao");
	public void getData()
	{
		HttpUtil httpUtil = new HttpUtil();		
		String response = httpUtil.httpPost(url, parameters, "UTF-8");
		OutStandingParser parser = new OutStandingParser();
		parser.setResponseString(response);
		parser.setDao(dao);
		parser.parse(1);
	}
	
	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public static void main(String[] args)
	{
		//上市
		UpdateSharesOutstanding shares = new UpdateSharesOutstanding();
		shares.setParameters("encodeURIComponent=1&step=1&firstin=1&TYPEK=sii&code=");
		shares.getData();
		//上櫃
		UpdateSharesOutstanding otc = new UpdateSharesOutstanding();
		otc.setParameters("encodeURIComponent=1&step=1&firstin=1&TYPEK=otc&code=");
		otc.getData();
	}

}
