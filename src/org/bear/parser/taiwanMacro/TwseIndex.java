package org.bear.parser.taiwanMacro;

import java.util.ArrayList;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.util.HttpUtil;
/**
 * 台灣加權指數月資料
 * @author edward
 *
 */
public class TwseIndex extends CbcParser
{
	private String url = "http://webline.sfi.org.tw/B/intdb/single/sfis503b.asp";
	List<NameValuePair> paramList = new ArrayList<NameValuePair>();
	public void getContent(String startYear, String startMonth, 
						   String endYear, String endMonth)
	{
		//初始值
		paramList.add(new BasicNameValuePair("SYY", startYear));
		paramList.add(new BasicNameValuePair("SMM", startMonth));
		paramList.add(new BasicNameValuePair("EYY", endYear));
		paramList.add(new BasicNameValuePair("EMM", endMonth));
		paramList.add(new BasicNameValuePair("CHARTYPE", "5"));
		paramList.add(new BasicNameValuePair("GOTOPROG", "sfis503b.asp"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.print(responseString);
		super.setResponseString(responseString);
		super.parse(0);
	}
	@Override
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 0)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String date = "";
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j).getFirstElement(HTMLElementName.SPAN);
				String content = resultElement.getContent().toString().trim().replace(",", "");
				try
				{
					int result = 0;
					if (j == 0)
					{
						date = content;
					}
					else if (j == 1)
					{
						if (content.length() == 1)
							date = date + "-0" + content;
						else
							date = date + "-" + content;
					}
					else if (j == 2)
					{
						result = dao.update("TwseOpen", content, date, "-");
						if (result <= 0)
					    	System.out.println("content: " + content);
					}
					else if (j == 3)
					{
						result = dao.update("TwseHigh", content, date, "-");
						if (result <= 0)
					    	System.out.println("content: " + content);
					}
					else if (j == 4)
					{
						result = dao.update("TwseLow", content, date, "-");
						if (result <= 0)
					    	System.out.println("content: " + content);
					}
					else if (j == 5)
					{
						result = dao.update("TwseClose", content, date, "-");
						if (result <= 0)
					    	System.out.println("content: " + content);
					}
					else
						break;					    
					
				}
				catch (Exception ex)
				{
					System.out.println(date);
					ex.printStackTrace();
				}
			}
		}
	}
}
