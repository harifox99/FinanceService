package org.bear.parser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

import org.bear.dao.RevenueDao;
import org.bear.entity.BasicEntity;
import org.bear.entity.RevenueEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author edward
 * Parse蕃薯藤的營業收入
 */
public class RevenueParserYam extends ParserBase implements Parser
{
	//開盤指數
	HashMap <String, String> mapOpenIndex;
	//最高指數
	HashMap <String, String> mapHighIndex;
	//最低指數
	HashMap <String, String> mapLowIndex;
	//收盤指數
	HashMap <String, String> mapCloseIndex;
	//週轉率
	HashMap <String, String> mapTurnoverRatio;	
	HashMap <String, String> mapLastCloseIndex;
	//將elementList轉成可以儲存至DB的資料
	public List<RevenueEntity> entityList;	
	//一次要Parse的營收資料月份數目
	int parseDataLength;
	//1=上市, 2=上櫃
	int stockBranch;
	RevenueDao dao;
	public List<RevenueEntity> getEntity() {
		return entityList;
	}
	public void setEntity(List<RevenueEntity> entityList) {
		this.entityList = entityList;
	}
	public int getParseDataLength() {
		return parseDataLength;
	}
	public void setParseDataLength(int parseDataLength) {
		this.parseDataLength = parseDataLength;
	}
	public RevenueParserYam(List<Element> elementList, String stockID, int fileNum,
			HashMap <String, String> mapOpenIndex,HashMap <String, String> mapHighIndex, 
			HashMap <String, String> mapLowIndex, HashMap <String, String> mapCloseIndex,
			HashMap <String, String> mapTurnoverRatio, int stockBranch)
	{
		this.elementList = elementList;
		this.stockID = stockID;
		this.mapCloseIndex = mapCloseIndex;
		this.mapHighIndex = mapHighIndex;
		this.mapLowIndex = mapLowIndex;
		this.mapOpenIndex = mapOpenIndex;
		this.mapTurnoverRatio = mapTurnoverRatio;
		this.parseDataLength = fileNum;
		this.stockBranch = stockBranch;
		entityList = new ArrayList<RevenueEntity>();	
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (RevenueDao)context.getBean("revenueDao");
	}
	public void parse(int tableIndex)
	{
		this.getTableContent(elementList.get(tableIndex));
	}
	/*
	 * (non-Javadoc)
	 * @see org.bear.parser.Parser#getTableContent(net.htmlparser.jericho.Element)
	 * 拆解Element
	 */
	public void getTableContent(Element element)
	{
		Element resultElement = null;
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		for (int i = 0; i < trList.size(); i++)
		{
			if (i == 0)
				continue;
			Element trElement = trList.get(i);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			RevenueEntity entity = new RevenueEntity();
			for (int j = 0; j < tdList.size(); j++)
			{
				resultElement = tdList.get(j);
				String content = resultElement.getContent().toString().trim();
				//讓數字的","消失
				content = content.replaceAll(",", "");
				
				if (j == 0)
				{
					String key = content.substring(0, 4) + "-" + content.substring(5, 7) + "-" + stockID;
					//不是我們所需要的月資料，略過
					if (mapCloseIndex.get(key) == null && mapOpenIndex.get(key) == null &&
						mapHighIndex.get(key) == null && mapLowIndex.get(key) == null)
						break;
					entity.setCloseIndex(mapCloseIndex.get(key));
					entity.setOpenIndex(mapOpenIndex.get(key));
					entity.setHighIndex(mapHighIndex.get(key));
					entity.setLowIndex(mapLowIndex.get(key));
					entity.setTurnoverRatio(mapTurnoverRatio.get(key));
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
					try
					{
						Date date = dateFormat.parse(content.substring(0, 4) + "-" + content.substring(5, 7));
						entity.setYearMonth(date);
					}
					catch (ParseException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (j == 1)
				{
					entity.setRevenue(this.convertInt(content));
				}
				else if (j == 3)
				{
					entity.setLastRevenue(this.convertInt(content));
				}
				else if (j == 5)
				{
					entity.setAccumulation(this.convertLong(content));
				}
				else if (j == 6)
				{
					entity.setLastAccumulation(this.convertLong(content));
					entity.setStockID(stockID);
					entityList.add(entity);
				}
				else
					continue;
			}
		}
		System.out.println("stockID: " + entityList.get(0).getStockID());
		if (this.stockBranch == 2)
			this.checkStockMarketData(entityList);
		//移除最舊的一筆資料，因為最舊一筆的收盤價資料僅僅是做為下一筆的開盤價
		entityList.remove(entityList.size()-1);
		/*
		for (int k = 0; k < entityList.size(); k++)
		{
			System.out.println(entityList.get(k).getYearMonth().toString());
		}
		if (stockID.equals("3209"))*/
		dao.insertBatch(entityList);
	}
	/*
	 * 
	 * 儲存Year and Month for Revenue
	 */
	@Override
	public void setYearAndSeason(BasicEntity entity, String rowData) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStockData(String[] rowData) {
		// TODO Auto-generated method stub
		
	}
	private long convertLong(String number)
	{
		try
		{
			return Long.parseLong(number)*1000;
		}
		catch (NumberFormatException ex)
		{
			return 0;
		}
	}
	private int convertInt(String number)
	{
		try
		{
			return Integer.parseInt(number)*1000;
		}
		catch (NumberFormatException ex)
		{
			return 0;
		}
	}
	/**
	 * 因為上櫃公司很多營收資料不足，NULL值會造成無法存入資料庫，所以要做補0的動作（僅限上櫃公司）
	 */
	private void checkStockMarketData(List<RevenueEntity> entityList)
	{
		for (int i = 0; i < entityList.size(); i++)
		{
			if (entityList.get(i).getCloseIndex() == null)
				entityList.get(i).setCloseIndex("0");
			if (entityList.get(i).getHighIndex() == null)
				entityList.get(i).setHighIndex("0");
			if (entityList.get(i).getLowIndex() == null)
				entityList.get(i).setLowIndex("0");
			if (entityList.get(i).getOpenIndex() == null)
				entityList.get(i).setOpenIndex("0");
			if (entityList.get(i).getTurnoverRatio() == null)
				entityList.get(i).setTurnoverRatio("0");
		}
	}
}

