package org.bear.parser.taiwanMacro;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bear.constant.CepdIndexConstant;
import org.bear.dao.MacroEconomicDao;
import org.bear.entity.MacroEconomicEntity;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
/**
 * 解析經建會的總經資料
 * @author edward
 *
 */
public class CepdParser {
	private String startDate;
	private String endDate;
	private String urlHeader = "http://index.cepd.gov.tw/Result.aspx?lang=1&type=";
	private String urlMiddle = "^^,,^";
	private String urlMiddleYoy = "^3,^,,^";
	private String urlFooter = ",^";
	private String url;
	Source source;
	int index;
	List<Element> elementList = null;
	MacroEconomicDao dao;
	List <MacroEconomicEntity> list;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void setDao(MacroEconomicDao dao) {
		this.dao = dao;
	}
	/**
	 * 經濟指標在經建會的代碼
	 * @param indexName
	 * @param mapName
	 */
	public void setUrl(String indexName, String mapName)
	{
		if (this.index == 6 || this.index == 7)
			this.url = urlHeader + mapName + "&p=1^1^" + startDate + "," + endDate + urlMiddleYoy + indexName + urlFooter;
		else
			this.url = urlHeader + mapName + "&p=1^1^" + startDate + "," + endDate + urlMiddle + indexName + urlFooter;			
		System.out.println(this.url);
	}
	public void getConnection()
	{
		Source source = null;
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
	public void getTableContent(Element element) {
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			list = new ArrayList<MacroEconomicEntity>();
			MacroEconomicEntity entity = new MacroEconomicEntity();
			String dateString = null;
			if (i < 1 || (i < 2 && (index == 6 || index == 7)))
				continue;
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				try
				{
					if (j == 0)
					{					
						if (index == 0)
						{
							String dateArray[] = content.split("-");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date date = dateFormat.parse(content + "-01");
							entity.setMonth(dateArray[1]);
							entity.setYear(dateArray[0]);
							entity.setYearMonth(date);
						}
						else
						{
							dateString = content;
						}
					}
					else if (j == 1 && index != 6 && index != 7)
					{
						if (index == 0)
						{
							entity.setGeneralIndex(content);
							entity.setSemiIndex("0");
							list.add(entity);
							dao.insertBatch(list);
						}
						else
						{
							int result = dao.update(CepdIndexConstant.CEPD_NAME[index], content, dateString, "-");
						    if (result <= 0)
						    {					    	
						    	System.out.println(content + ", " + dateString);
						    }
						}
					}	
					else if (index == 6 || index == 7)
					{
						if (j == 0 || j == 1)
							continue;
						resultElement = tdList.get(j).getFirstElement(HTMLElementName.FONT);		
						content = resultElement.getContent().toString().trim();	
						int result = dao.update(CepdIndexConstant.CEPD_NAME[index], content, dateString, "-");
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
		this.getTableContent(elementList.get(tableIndex));				
	}
}
