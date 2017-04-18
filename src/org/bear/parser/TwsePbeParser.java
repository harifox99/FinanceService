package org.bear.parser;

import java.util.HashMap;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * √“•Ê©“PE Ratio and PB Ratio Parser
 * @author edward
 *
 */
public class TwsePbeParser extends EasyParserBase 
{
	HashMap<String, Double> hashPer = new HashMap<String, Double>();
	HashMap<String, Double> hashPbr = new HashMap<String, Double>();
	public HashMap<String, Double> getHashPer() {
		return hashPer;
	}

	public HashMap<String, Double> getHashPbr() {
		return hashPbr;
	}

	@Override
	public void getTableContent(Element element) {
		// TODO Auto-generated method stub
		Element resultElement = null;
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 2; i < trList.size(); i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			String stockId = null;
			for (int j = 0; j < tdList.size(); j++)
			{
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString();
				//content = StringUtil.eraseSpecialChar(content);
				if (j == 0)
					stockId = content;
				else if (j == 4)
				{
					double per;
					if (content.equals("-"))
					{
						per = -1;
						hashPer.put(stockId, per);
					}
					else if (content.contains(","))
					{
						content = content.replace(",", "");
						per = Double.parseDouble(content);
						hashPer.put(stockId, per);
					}
					else
					{
						per = Double.parseDouble(content);
						hashPer.put(stockId, per);
					}
				}
				else if (j == 5)
				{
					double pbr;
					pbr = Double.parseDouble(content);
					hashPbr.put(stockId, pbr);
				}
									
			}
		}
	}

}
