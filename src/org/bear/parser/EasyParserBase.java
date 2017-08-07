package org.bear.parser;

import java.net.URL;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public abstract class EasyParserBase
{
	public String responseString;
	public String url;
	public List<Element> elementList = null;
	Source source;
	public void parse(int tableIndex) 
	{
		source = new Source(responseString);
		//System.out.println(source.toString());
		try
		{
			elementList = source.getAllElements(HTMLElementName.TABLE);
			this.getTableContent(elementList.get(tableIndex));
		}
		catch (IndexOutOfBoundsException ex)
		{
			System.out.println("查無此股票籌碼資訊!");
		}
	}
	public void getConnection()
	{
		try
		{
			source = new Source(new URL(url));
			elementList = source.getAllElements(HTMLElementName.TABLE);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
	public abstract void getTableContent(Element element);
	public void parseHttpGet(int index)
	{		
		this.getTableContent(elementList.get(index));
	}
}
