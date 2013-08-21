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

