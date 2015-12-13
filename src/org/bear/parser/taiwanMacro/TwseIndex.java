package org.bear.parser.taiwanMacro;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.constant.FredIndexParameterMap;
import org.bear.dao.MacroEconomicDao;
import org.bear.parser.EasyParserBase;
/**
 * 台灣加權指數月資料
 * @author edward
 *
 */
public class TwseIndex extends EasyParserBase
{
	private String url = "http://finance.yahoo.com/q/hp?s=%5ETWII&a=03&b=1&c=2015&d=10&e=31&f=2015&g=m";
	MacroEconomicDao dao;
	public MacroEconomicDao getDao() {
		return dao;
	}
	public void setDao(MacroEconomicDao dao) {
		this.dao = dao;
	}
	public void getContent()
	{		
		this.setUrl(url);
		this.getConnection();
		//System.out.print(responseString);
		this.parse();		
	}
	@Override
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == trList.size()-1)
				break;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String date = "";
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				String[] dateArr = null;
				
				if (j == 0)
				{					
					dateArr = content.split(",");
					date = dateArr[1].trim() + "-" + FredIndexParameterMap.MONTH.get(dateArr[0].subSequence(0, dateArr[0].indexOf(" "))) + "-01";					
				}
				else
				{
					try
					{
						int result;
						if (j == 1)
						{
							result = dao.update("TwseOpen", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
							
						}
						else if (j == 2)
						{
							result = dao.update("TwseHigh", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
						}															
						else if (j == 3)
						{
							result = dao.update("TwseLow", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
						}
						else if (j == 4)
						{
							result = dao.update("TwseClose", content.replace(",", ""), date);
							this.printDaoErrorMessage(result, content, date);
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
	/**
	 * DAO更新失敗列出錯誤訊息 (通常是資料太新，比如說到了7月，都有及時的收盤資訊，但是總經資訊不夠新，所以比較新的收盤資料找不到對應的總經資訊)
	 * @param result
	 * @param content
	 * @param date
	 */
	private void printDaoErrorMessage(int result, String content, String date)
	{
		if (result <= 0)
	    {					    	
	    	System.out.println(content + ", " + date);
	    }
	}
	public void parse()
	{		
		this.getTableContent(elementList.get(14));
	}
}
