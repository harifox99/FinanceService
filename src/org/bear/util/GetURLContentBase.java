package org.bear.util;

import java.io.IOException;
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
}

