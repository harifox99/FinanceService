package org.bear.datainput;
import java.util.HashMap;
import org.bear.parser.RevenueParserYam;
import org.bear.util.GetURLYamRevenue;

public class ImportRevenueYam extends ImportStockIDData
{					
	//秨絃计
	HashMap <String, String> mapOpenIndex;
	//程蔼计
	HashMap <String, String> mapHighIndex;
	//程计
	HashMap <String, String> mapLowIndex;
	//Μ絃计
	HashMap <String, String> mapCloseIndex;
	//秅锣瞯
	HashMap <String, String> mapTurnoverRatio;	
	//犁Μる计
	int fileNum;
	public HashMap<String, String> getMapOpenIndex() {
		return mapOpenIndex;
	}
	public void setMapOpenIndex(HashMap<String, String> mapOpenIndex) {
		this.mapOpenIndex = mapOpenIndex;
	}
	public HashMap<String, String> getMapHighIndex() {
		return mapHighIndex;
	}
	public void setMapHighIndex(HashMap<String, String> mapHighIndex) {
		this.mapHighIndex = mapHighIndex;
	}
	public HashMap<String, String> getMapLowIndex() {
		return mapLowIndex;
	}
	public void setMapLowIndex(HashMap<String, String> mapLowIndex) {
		this.mapLowIndex = mapLowIndex;
	}
	public HashMap<String, String> getMapCloseIndex() {
		return mapCloseIndex;
	}
	public void setMapCloseIndex(HashMap<String, String> mapCloseIndex) {
		this.mapCloseIndex = mapCloseIndex;
	}
	public HashMap<String, String> getMapTurnoverRatio() {
		return mapTurnoverRatio;
	}
	public void setMapTurnoverRatio(HashMap<String, String> mapTurnoverRatio) {
		this.mapTurnoverRatio = mapTurnoverRatio;
	}
	public void insertBatchList()
	{
		//炕力妹呼ъ犁Μ戈
		try
		{		
			int idleTime = 0;
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockID = wrapperList.get(j).getStockID();
				//if (!stockID.equals("2059"))
					//continue;
				int stockBranch = wrapperList.get(j).getStockBranch();
				System.out.println("布絏" + stockID + " " + idleTime + ". ");			
				GetURLYamRevenue urlContent = new GetURLYamRevenue(stockID);
				RevenueParserYam cashFlowsParser = new RevenueParserYam(urlContent.getContent(), stockID,
				    fileNum, mapOpenIndex, mapHighIndex, mapLowIndex, mapCloseIndex, mapTurnoverRatio, stockBranch);
				cashFlowsParser.parse(3);
				Thread.sleep(5000);		
				idleTime++;
				System.out.println("/****************************/");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}


