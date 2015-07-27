package org.bear.parser;
import java.util.List;
import org.bear.entity.JuristicDailyEntity;
import org.bear.util.StringUtil;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 台股每日指數資料Parser
 * @author edward
 *
 */
public class TwseDailyIndexParser extends TaifexLotParser {

	public void getTableContent(Element element) {
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		JuristicDailyEntity entity = new JuristicDailyEntity();
		String year = null;
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 3)
			{				
				Element trElement = trList.get(i);
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				Element resultElement = null;
				for (int j = 0; j < tdList.size(); j++)
				{	
					try
					{																
						if (j == 1)//收盤價
						{
							resultElement = tdList.get(j);				
							String content = resultElement.getContent().toString().trim();		
							content = content.replace(",", "");
							//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							//把民國轉換成西元
							String[] dateArray = date.split("/");
							year = StringUtil.convertYear(dateArray[0]);
							year = year + "/" + dateArray[1] + "/" + dateArray[2];
							//Date dateClass = dateFormat.parse(year);
							//entity.setExchangeDate(dateClass);
							entity.setTwseIndex(Double.parseDouble(content));
						}
						else if (j == 3)//漲跌幅
						{
							resultElement = tdList.get(j);				
							String content = resultElement.getContent().toString().trim();		
							content = content.replace(",", "");	
							entity.setChange(Double.parseDouble(content));				
						}
						
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}	
		dao.update("TwseIndex", entity.getTwseIndex(), year);
		dao.update("Change", entity.getChange(), year);
	}

}
