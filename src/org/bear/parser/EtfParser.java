package org.bear.parser;
import java.util.HashMap;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
/**
 * 外資持有部位Parser(主要用來爬ETF)
 * @author edward
 *
 */
public class EtfParser extends TaifexLotParser 
{
	/**
	 * 要爬哪幾個ETF
	 */
	public static final HashMap<String, String> hashStockName = new HashMap<String, String>();
	static
	{
		hashStockName.put("00632R", "T50R");
	}
	public HashMap<String, Boolean> stockNo;
	public HashMap<Integer, Boolean> stockColumn;
	public HashMap<String, Boolean> getStockNo() {
		return stockNo;
	}

	public void setStockNo(HashMap<String, Boolean> stockNo) {
		this.stockNo = stockNo;
	}

	public HashMap<Integer, Boolean> getStockColumn() {
		return stockColumn;
	}

	public void setStockColumn(HashMap<Integer, Boolean> stockColumn) {
		this.stockColumn = stockColumn;
	}

	public void getTableContent(Element element) 
	{
		// TODO Auto-generated method stub
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 2; i < trList.size(); i++)
		{
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			Element resultElement = null;
			String stockId = null;
			for (int j = 0; j < tdList.size(); j++)
			{	
				resultElement = tdList.get(j);				
				String content = resultElement.getContent().toString().trim();
				if (j == 0) 
				{
					if (stockNo.get(content) == null)
						break;
					else
						stockId = hashStockName.get(content);
				}
				else if (stockColumn.get(j) != null)
				{
                    content = content.replace(",", "");
                    int volumn = (int)Long.parseLong(content)/1000;
					dao.update(stockId, volumn, date);
				}
			}
		}
	}
}
