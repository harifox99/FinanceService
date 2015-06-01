package org.bear.parser.taiwanMacro;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bear.constant.CepdIndexConstant;
import org.bear.entity.MacroEconomicEntity;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

public class NdcParser extends CbcParser
{
	public void getTableContent(Element element) 
	{
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size()-1; i++)
		{
			if (i < 3) //Skip Header
				continue;
			ArrayList<MacroEconomicEntity> list = new ArrayList<MacroEconomicEntity>();
			MacroEconomicEntity entity = new MacroEconomicEntity();
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String dateString = null;
			for (int j = 0; j < tdList.size(); j++)
			{				
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				try
				{
					if (j == 0)
					{
						String dateArray[] = content.split("M");
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date date = dateFormat.parse(dateArray[0] + "-" + dateArray[1] + "-01");
						entity.setMonth(dateArray[1]);
						entity.setYear(dateArray[0]);
						entity.setYearMonth(date);	
						list.add(entity);
						dateString = content;
						dao.insertBatch(list);
					}
					else if (j == 3 || j == 6)
					{
						continue;
					}
					else
					{
						int result = dao.update(CepdIndexConstant.NDC_MAP[j-1], content, dateString, "M");
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
}
