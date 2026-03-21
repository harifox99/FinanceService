package org.bear.parser;
import java.text.SimpleDateFormat;
import java.util.List;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.entity.JuristicDailyEntity;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 擷取證交所三大法人買賣超總金額資料
 * @author edward
 *
 */
public class TwseThreeBigAmountParser extends EasyParserBase 
{
	JuristicDailyReportDao dao;
	String date;
	public JuristicDailyReportDao getDao() {
		return dao;
	}

	public void setDao(JuristicDailyReportDao dao) {
		this.dao = dao;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);		
		for (int i = 0; i < trList.size(); i++)
		{
			if (i != 5)
				continue;
			JuristicDailyEntity entity = new JuristicDailyEntity();
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			for (int j = 0; j < tdList.size(); j++)
			{									
				try
				{											
					resultElement = tdList.get(j);				
					String content = resultElement.getContent().toString().trim();	
					content = content.replace(",", "");					
					if (j == 3)//Stock ID
					{
						long amount = Long.parseLong(content);
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
						entity.setExchangeDate(dateFormat.parse(date));
						entity.setAmount(amount);
						dao.insert(entity);						
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
		}			
	}

}
