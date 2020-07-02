package org.bear.util;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
/**
 * @author edward
 * URL Connection Base
 */
public class GetURLContentBase 
{
	protected String urlString;
	protected String urlHeaderSeason;
	protected String urlHeaderYear;
	protected String urlHeader;
	protected String urlFooter;
	public List<Element> getContent()
	{
		boolean isConnect = false;
		List<Element> elementList = null;
		while (isConnect == false)
		{
			Source source = null;
			try
			{
				CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
				source = new Source(new URL(urlString));
				elementList = source.getAllElements(HTMLElementName.TABLE);
				isConnect = true;
			}
			catch(MalformedURLException ex)
			{
				ex.printStackTrace();
			}
			catch(ProtocolException pex)
			{
				System.out.println("網路問題");
				//pex.printStackTrace();
				isConnect = false;
				try
				{
					Thread.sleep(1000);
				}
				catch (Exception ex)
				{
					System.out.println("Get URL fail interrupt!");
				}
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
				System.out.println("查無此股票資訊");
				isConnect = true;
			}
			
		}
		return elementList;
	}
	/**
	 * source = new Source(new URL(urlString));有時候擷取會失敗，所以直接把傳回的responseString丟去Parse
	 * @param responseString 回應的HTML String
	 * @return
	 */
	public List<Element> getContentString(String responseString)
	{
		boolean isConnect = false;
		List<Element> elementList = null;
		while (isConnect == false)
		{
			//System.out.println(source.toString());
			try
			{
				CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
				Source source = new Source(responseString);
				elementList = source.getAllElements(HTMLElementName.TABLE);
				//source = new Source(new URL(urlString));
				isConnect = true;
			}
			catch (IndexOutOfBoundsException ex)
			{
				System.out.println("查無此股票籌碼資訊!");
			}
		}
		return elementList;
	}
	public String getUrlString() {
		return urlString;
	}
}

