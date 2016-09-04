package org.bear.parser.taiwanMacro;
import java.util.List;
import org.bear.constant.CbcIndexConstant;
import org.bear.dao.MacroEconomicDao;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
/**
 * 解析央行的總經資料(CBC)，主要是貨幣供給額
 * @author edward
 *
 */
public class CbcParser 
{
	protected MacroEconomicDao dao;
	public String responseString;
	List<Element> elementList = null;
	public String getResponseString() {
		return responseString;
	}
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	public void setDao(MacroEconomicDao dao) {
		this.dao = dao;
	}
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size()-1; i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String dateString = null;
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				content = content.replaceAll(",", "");
				try
				{
					if (j == 0)
					{
						dateString = content;
					}
					else if (j == 3 || j == 4)
						continue;
					else
					{
						
						int result = dao.update(CbcIndexConstant.MONEY[j-1], content, dateString, "M");
						if (result <= 0)
					    {					    	
					    	System.out.println(content + ", " + dateString);
					    }
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
	public void parse(int tableIndex) {
		Source source = new Source(responseString);
		//System.out.println(source.toString());
		elementList = source.getAllElements(HTMLElementName.TABLE);
		this.getTableContent(elementList.get(tableIndex));				
	}
}
