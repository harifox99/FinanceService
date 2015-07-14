package org.bear.parser;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public abstract class EasyParserBase {
	public String responseString;
	List<Element> elementList = null;
	public void parse(int tableIndex) {
		Source source = new Source(responseString);
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
	
	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public abstract void getTableContent(Element element);
}
