package org.bear.shortTrade;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bear.parser.file.FileParser;
import org.bear.util.GetURLContent;
/**
 * VCP
 * @author bear
 *
 */
public class Vcp 
{
	String urlString = "https://stock.pingponglife.tw/vcp/export?date=";
	Set<String> sets = new HashSet<String>();
	public void getContent(String dateString)
	{
		try
		{
			GetURLContent content = new GetURLContent(urlString + dateString);
			FileParser fileParser = new FileParser();
			List<String> listData = fileParser.getResponse(content.getContent("UTF-8"));
			for (int i = 0; i < listData.size(); i++)
			{
				String[] stocks = listData.get(i).split(".tw");
				String stockId = stocks[0];
				sets.add(stockId);
			}			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();	
		}
	}
	

	public Set<String> getSets() {
		return sets;
	}


	public void setSets(Set<String> sets) {
		this.sets = sets;
	}


	public static void main(String[] args)
	{
		Vcp vcp = new Vcp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date today = new Date();        
		String dateString = dateFormat.format(today);
		vcp.getContent(dateString);
	}

}
