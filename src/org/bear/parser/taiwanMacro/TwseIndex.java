package org.bear.parser.taiwanMacro;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.MacroEconomicDao;
import org.bear.parser.TwseIndexParser;
import org.bear.util.HttpUtil;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 台灣加權指數月資料
 * @author edward
 *
 */
public class TwseIndex
{
	MacroEconomicDao dao;
	public void setDao(MacroEconomicDao dao) {
		this.dao = dao;
	}
	public void getContent(String data)
	{		
		String[] date = data.split("M");
		String url = "http://www.twse.com.tw/ch/trading/indices/MI_5MINS_HIST/MI_5MINS_HIST.php";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		//日期
		paramList.add(new BasicNameValuePair("myear", StringUtil.convertChineseYear(date[0])));
		paramList.add(new BasicNameValuePair("mmon", date[1]));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		TwseIndexParser parser = new TwseIndexParser();
		parser.setResponseString(responseString);
		parser.setDao(dao);
		parser.setDateString(data);
		parser.parse(8);		
	}
	public static void main(String args[])
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		MacroEconomicDao dao = (MacroEconomicDao)context.getBean("macroEconomicDao");
		TwseIndex index = new TwseIndex();
		index.setDao(dao);
		index.getContent("2016M08");
	}
}
