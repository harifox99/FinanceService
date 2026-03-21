package org.bear.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

/**
 * @author edward
 * 蕃薯藤網站的年度現金流量表URL
 */
public class GetURLYamCashFlow extends GetURLContentBase
{	
	public GetURLYamCashFlow(String co_id)
	{
		urlHeader = "http://ps01.megatime.com.tw/asp/Basic/GetReportJs.asp?m=&table_name=html\\YFinacs\\&StockID=";
		this.urlString = urlHeader + co_id;
	}
	public List<Element> getContent()
	{
		boolean isConnect = false;
		List<Element> elementList = null;
		while (isConnect == false)
		{
			Source source = null;
			try
			{
				source = new Source(new URL(urlString));
				String content = new String(source.toString().getBytes("ISO-8859-1"), "Big5");
				System.out.println(content);
				source = new Source(content);
				isConnect = true;
			}
			catch(MalformedURLException ex)
			{
				ex.printStackTrace();
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
			elementList = source.getAllElements(HTMLElementName.TABLE);
		}
		return elementList;
	}
}

