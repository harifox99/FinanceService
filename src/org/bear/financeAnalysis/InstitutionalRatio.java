package org.bear.financeAnalysis;
import java.net.URL;
import java.util.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.constant.FinancialReport;
import org.bear.dao.BasicStockDao;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.InstitutionalEntity;
import org.bear.entity.ThreeBigExchangeEntity;
import org.bear.parser.TpexPriceParser;
import org.bear.parser.TwsePriceParser;
import org.bear.util.HttpUtil;
import org.bear.util.StringUtil;
import org.jsoup.Jsoup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 計算投本比 (投信)，外本比 (外資)，兩本比 (兩大)
 * @author edward
 *
 */
public class InstitutionalRatio 
{
	int month = 30;
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	JuristicDailyReportDao juristicDailyReportDao = (JuristicDailyReportDao)context.getBean("juristicDailyReportDao");
	BasicStockDao basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
	List<InstitutionalEntity> listAllEntity = new ArrayList<InstitutionalEntity>();
	//List<List<InstitutionalEntity>> listAllEntity = new ArrayList<List<InstitutionalEntity>>();
	Map<String, Double> mapForeigner;	 
	Map<String, Double> mapInvestment;
	Map<String, Double> mapInstitutional;
	/**
	 * 
	 * @param days 查詢幾天
	 * @param accumulation 以累積幾日來排名
	 * @param maxSize 最多顯示幾筆股票資料
	 * @param date 集保日期
	 * @param buyer 外資/投信
	 * @param priceDate 股價（日期）
	 * @param capital 股本
	 * @param price 期望股價
	 * @param isSmallCapital 設定股本 > or < 某一個值
	 * @param priceRate 漲幅比例
	 * @param compareDate 某個時間股價 (比較用)
	 * @return
	 */
	public List<InstitutionalEntity> getOrder(int days, int accumulation, 
			int maxSize, String date, String buyer, String priceDate, 
			double capital, double price, boolean isSmallCapital,
			int priceRate, String compareDate)
	{
		List<BasicStockWrapper> listStock = basicStockDao.findAllData();
		//外資個股買賣超佔股本比
		Map<String, List<Double>> foreignerStockRatio = new HashMap<String, List<Double>>();
		//外資每日買賣超資料
		List<ThreeBigExchangeEntity> listForeigner;
		//排序外資
		mapForeigner = new HashMap<String, Double>();
		ValueComparator foreignerVC = new ValueComparator(mapForeigner);
		TreeMap<String, Double> sortedForeigner = new TreeMap<String, Double>(foreignerVC);
		for (int i = 0; i < listStock.size(); i++)
		//for (int i = 0; i < 100; i++)
		{
			//if (!listStock.get(i).getStockID().equals("2421"))
				//continue;
			//得到該股票的外資交易資料
			listForeigner = juristicDailyReportDao.findStockBySize(listStock.get(i).getStockID(), days, buyer);		
			List<Double> ratioList = this.computeAccumulateCapitalRatio(listStock.get(i).getStockID(), listForeigner, listStock.get(i).getCapital(), mapForeigner, accumulation);
			foreignerStockRatio.put(listStock.get(i).getStockID(), ratioList);
		}		
		Iterator<String> it;
		//外資排序
		List<InstitutionalEntity> listForeignerEntity = new ArrayList<InstitutionalEntity>();
		sortedForeigner.putAll(mapForeigner);
		it = sortedForeigner.keySet().iterator();
		while (it.hasNext()) 
	    {
	    	String stockID = it.next();
	    	List<Double> order = foreignerStockRatio.get(stockID);
	    	InstitutionalEntity entity = new InstitutionalEntity();
	    	entity.setStockID(stockID);
	    	entity.setInfo(order);
	    	//增加買超排名
	    	ThreeBigExchangeEntity rankEntity = juristicDailyReportDao.findStockByDateAndBuyer(stockID, priceDate, buyer);
	    	if (rankEntity == null)
	    		entity.setRank(999);
	    	else
	    		entity.setRank(rankEntity.getRank());
	    	listForeignerEntity.add(entity);
	    }
		listForeignerEntity = this.checkCapital(listForeignerEntity, capital, isSmallCapital);
		this.getPrice(listForeignerEntity, priceDate);
		listForeignerEntity = this.priceFilter(listForeignerEntity, compareDate, priceRate);
		listForeignerEntity = this.checkPrice(listForeignerEntity, price);
		this.consecutiveExchange(listForeignerEntity, days, buyer, maxSize);	
		this.majorHolder(listForeignerEntity, maxSize, date);	
		this.ShareholdingRatio(listForeignerEntity, maxSize, date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
		return listForeignerEntity;
	}
    public static void main(String[] args) 
    {	    	  
    	/*
	    HashMap<String, Double> map = new HashMap<String, Double>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
        map.put("dd", 0.01);
	    map.put("aa", 0.02);
	    map.put("ee", -0.05);
	    map.put("ae", 0.6);
	    map.put("ab", 0.44);
        System.out.println("unsorted map: " + map);
        sorted_map.putAll(map);
        System.out.println("results: " + sorted_map);*/       
        InstitutionalRatio ratio = new InstitutionalRatio();
    	List<InstitutionalEntity> listForeignerEntity = new ArrayList<InstitutionalEntity>();
    	ratio.ShareholdingRatio(listForeignerEntity, 1, "2018-05-04");
    }
    /**
     * 計算不同天數 (累積資料)的投信買賣超佔股本比或是外資買賣超佔股本比
     * @param stockID 股票代碼
     * @param listInstitutional 個股的法人交易資料
     * @param capital 股本
     * @param mapOrder 用TreeMap來自動排名
     * @param days 用累積日來排名
     * @return
     */
    private List<Double> computeAccumulateCapitalRatio(String stockID, List<ThreeBigExchangeEntity> listInstitutional, 
    		double capital, Map<String, Double> mapOrder, int accumulation)
    {
    	//單位換算，分母要乘以100
    	final int converter = 100;
    	//成交量
    	double totalQuantity = 0;
    	List<Double> ratioList = new ArrayList<Double>();
    	for (int i = 0; i < listInstitutional.size(); i++)
    	{
    		totalQuantity = totalQuantity + listInstitutional.get(i).getQuantity();
    		double ratio = (double)totalQuantity/capital/converter;
    		ratioList.add(StringUtil.setPointLength(ratio));
    		if (i == accumulation-1)
    		{
    			mapOrder.put(stockID, ratio);
    		}
    	}
    	return ratioList;
    }
    
    /**
     * 過濾股本
     * @param list
     * @param exceptedCapital
     */
    private List<InstitutionalEntity> checkCapital(List<InstitutionalEntity> list, 
    		double exceptedCapital, boolean isSmallCapital)
    {
    	List<InstitutionalEntity> capitalList = new ArrayList <InstitutionalEntity>();
    	for (int i = 0; i < list.size(); i++)
    	{
    		String stockID = list.get(i).getStockID();
    		BasicStockWrapper basicEntity = basicStockDao.findBasicData(stockID);
    		double capital = basicEntity.getCapital();
    		if (capital <= exceptedCapital && isSmallCapital)
    			capitalList.add(list.get(i));
    		else if (isSmallCapital == false && capital > exceptedCapital)
    			capitalList.add(list.get(i));
    		
    	}
    	return capitalList;
    }
    /**
     * 過濾股價
     * @param list
     * @param exceptedPrice
     * @return
     */
    private List<InstitutionalEntity> checkPrice(List<InstitutionalEntity> list, double exceptedPrice)
    {
    	List<InstitutionalEntity> capitalList = new ArrayList <InstitutionalEntity>();
    	for (int i = 0; i < list.size(); i++)
    	{
    		double price = list.get(i).getPrice();
    		if (price <= exceptedPrice)
    			capitalList.add(list.get(i));
    	}
    	return capitalList;
    }
    
    /**
     * 計算連續買天數，M天有N天買
     * @param list
     * @param days
     * @param buyer
     * @param maxSize
     */
    private void consecutiveExchange(List<InstitutionalEntity> list,
    		int days, String buyer, int maxSize)
    { 	
    	final int converter = 100;
    	for (int index = 0; index < maxSize; index++) 
    	{
    		InstitutionalEntity entity = list.get(index);		
    		//股票代碼
    		String stockID = list.get(index).getStockID();
    		BasicStockWrapper basicEntity = basicStockDao.findBasicData(stockID);
    	    //未排序交易資訊 (成交量)
    	    List<ThreeBigExchangeEntity> listRawData = juristicDailyReportDao.findStockBySize(stockID, days, buyer);
    		//計算單日成交比率
    		List<Double> ratioList = new ArrayList<Double>();    		
        	for (int i = 0; i < listRawData.size(); i++)
        	{
        		//成交量
        		double quantity = listRawData.get(i).getQuantity();
        		double ratio = (double)quantity/basicEntity.getCapital()/converter;
        		ratioList.add(StringUtil.setPointLength(ratio));
        	}    	        	        	     	        	    
    	    int buy = 0;    	        	       	       	    
    	    for (int i = 0; i < ratioList.size(); i++)
    	    {
    	    	if (ratioList.get(i) > 0)
    	    		buy++;
    	    }
    	    //過去M天買N天
    	    entity.setComment("過去" + ratioList.size() + "天，買" + buy + "天");
    	    buy = 0;
    	    for (int i = 0; i < ratioList.size(); i++)
    	    {
    	    	if (ratioList.get(i) > 0)
    	    		buy++;
    	    	else
    	    		break;
    	    }    
    	    //連續買超天數
    	    entity.setConsecutiveDays("連續買超" + buy + "天");
    	    //名稱
    	    String name = basicEntity.getStockName();
    	    entity.setName(name);
    	    //股本
    	    double capital = basicEntity.getCapital();
    	    entity.setCapital(StringUtil.setPointLength(capital));
    	}    	
    	System.out.println("");
    }
    /**
     * 擷取集保大戶資料
     * @param list
     * @param maxSize
     * @param dateString
     */
    private void majorHolder(List<InstitutionalEntity> list, int maxSize, String dateString)
    {
    	//400大戶從第12行開始
    	final int startTrIndex = 12;
    	for (int i = 0; i < maxSize; i++)
    	{
    		InstitutionalEntity entity = list.get(i);
    		String url = "http://www.tdcc.com.tw/smWeb/QryStockAjax.do";
    		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
    		paramList.add(new BasicNameValuePair("scaDate", dateString));
    		paramList.add(new BasicNameValuePair("scaDates", dateString));
    		paramList.add(new BasicNameValuePair("SqlMethod", "StockNo"));
    		paramList.add(new BasicNameValuePair("StockNo", list.get(i).getStockID()));
    		paramList.add(new BasicNameValuePair("REQ_OPR", "SELECT"));
    		paramList.add(new BasicNameValuePair("clkStockNo", list.get(i).getStockID()));
    		paramList.add(new BasicNameValuePair("radioStockNo", list.get(i).getStockID()));		
    		boolean isSuccessful = false;
    		//集保填錯日期就GG了，所以要設Counter
    		int count = 15;
    		while (isSuccessful == false && count-- > 0)
    		{
    			//400張
    			double totalRatio = 0;
    			//800張
    			double eightHundredRatio = 0;
	    		try
	    		{
	    			String responseString = HttpUtil.send(url, paramList, 1, "big5");
	    			Document doc = Jsoup.parse(responseString);
	    			Element table = doc.select("table").get(8);
		    		Elements tr = table.select("tr");
		    		//400-600, 600-800, 800-1000, 1000+ (共4份資料)
		    		for (int j = startTrIndex; j < startTrIndex + 4; j++)
			        {
				        Element td = tr.get(j);
				        Elements tdList = td.select("td");
				        double ratio = Double.parseDouble(tdList.get(4).text());	
				        ratio = StringUtil.setPointLength(ratio);
				        totalRatio = totalRatio + ratio;
				        if (j == startTrIndex+2)
				        	eightHundredRatio = ratio;
				        if (j == startTrIndex+3)
				        {
				        	entity.setThousand(ratio);
				        	entity.setEightHundred(StringUtil.setPointLength(ratio+eightHundredRatio));
				        }
			        }
		    		entity.setFourHundred(StringUtil.setPointLength(totalRatio));
	    			isSuccessful = true;
	    		}
	    		//集保很容易發生Http Error
	    		catch (Exception ex)
	    		{
	    			try 
					{
	    				System.out.println("StockID:" + list.get(i).getStockID());
						Thread.sleep(FinancialReport.sleepTime * 6);
					} 
					catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						System.out.println(list.get(i).getStockID() + ": 重新查詢營收發生中斷!");
					}
	    		}
	    		
    		}
    	}
    }
    
    /**
     * 得到最新股價
     * @param list
     * @param maxSize
     * @param dateString
     */
    private void getPrice(List<InstitutionalEntity> list, String dateString)
    {
    	//證交所
    	String url = "http://www.twse.com.tw/exchangeReport/MI_INDEX?response=html&type=ALLBUT0999&date=";
		TwsePriceParser twseParser = new TwsePriceParser();
		twseParser.setUrl(url + dateString);
		twseParser.getConnection();
		twseParser.parse(4);
		HashMap<String, Double> hashPrice = twseParser.getHashPrice();	
		//櫃買
		url = "http://www.tpex.org.tw/web/stock/aftertrading/otc_quotes_no1430/stk_wn1430_print.php?l=zh-tw&se=EW&s=0,asc,0&d=";
		TpexPriceParser tpexParser = new TpexPriceParser();
		String year = dateString.substring(0, 4);
		dateString = StringUtil.convertChineseYear(year) + "/" + dateString.substring(4, 6) + "/" + dateString.substring(6, 8);
		tpexParser.setUrl(url + dateString);
		tpexParser.getConnection();
		tpexParser.parse(0);
		hashPrice.putAll(tpexParser.getHashPrice());
		for (int i = 0; i < list.size(); i++)
		{
			InstitutionalEntity entity = list.get(i);
			try
			{
				entity.setPrice(hashPrice.get(entity.getStockID()));
			}
			catch (NullPointerException ex)
			{
				entity.setPrice(0);
			}
		}
    }
    /**
     * 可以用漲幅過濾機構投資人篩選結果
     * @param list
     * @param compareDate
     * @param priceRate
     * @return
     */
    private List<InstitutionalEntity> priceFilter(List<InstitutionalEntity> list, 
    		String compareDate, int priceRate)
    {
    	List<InstitutionalEntity> filterList = new ArrayList<InstitutionalEntity>();
    	try
    	{	    	
	    	//計算某個日子股價 (上市)
	    	String url = "http://www.twse.com.tw/exchangeReport/MI_INDEX?response=html&type=ALLBUT0999&date=";
	    	TwsePriceParser twseParser = new TwsePriceParser();
	    	twseParser.setUrl(url + compareDate.replace("/", ""));
	    	twseParser.getConnection();
	    	twseParser.parse(4);
			HashMap<String, Double> previousPrice = twseParser.getHashPrice();
			//計算某個日子股價 (上櫃)
			TpexPriceParser tpexParser = new TpexPriceParser();
			String year = compareDate.substring(0, 4);
			url = "http://www.tpex.org.tw/web/stock/aftertrading/otc_quotes_no1430/stk_wn1430_print.php?l=zh-tw&se=EW&s=0,asc,0&d=";
			compareDate = StringUtil.convertChineseYear(year) + "/" + compareDate.substring(4, 6) + "/" + compareDate.substring(6, 8);
			tpexParser.setUrl(url + compareDate);
			tpexParser.getConnection();
			tpexParser.parse(0);
			previousPrice.putAll(tpexParser.getHashPrice());	
			for (int i = 0; i < list.size(); i++)
			{
				String stockId = list.get(i).getStockID();
				System.out.println("stockId: " + stockId);
				if (previousPrice.get(stockId) == null)
					continue;
				double rate = (double)list.get(i).getPrice()/previousPrice.get(stockId) * 100 - 100;
				if (rate < priceRate)
				{
					filterList.add(list.get(i));
				}
			}
    	}
    	catch (Exception ex)
    	{
    		ex.printStackTrace();
    	}
		return filterList;
    }
    /**
     * 計算外資、投信持股
     * @param list
     */
    public void ShareholdingRatio(List<InstitutionalEntity> list, int maxSize, String date)
    {
        String urlHeader = "http://moneydj.emega.com.tw/z/zc/zcl/zcl.djhtm?a=";
        try
        {
	    	for (int i = 0; i < maxSize; i++)
	    	{
	    		InstitutionalEntity entity = list.get(i);
	    		String urlString = urlHeader + entity.getStockID() + "&c=" + date + "&d=" + date;
	    		URL url = new URL(urlString);//在這邊輸入你要解析的網頁網址 
		        Document xmlDoc =  Jsoup.parse(url, 3000); //使用Jsoup jar 去解析網頁
		        //(要解析的文件,timeout)
		        Element table = xmlDoc.select("table").get(2); //select the first table.
		        Elements rows = table.select("tr");
		        for (int j = 0; j < rows.size(); j++)
		        {
		        	if (j == 7)
		        	{
		        		Element td = rows.get(j);
		        		Elements tdList = td.select("td");
		        		int total = Integer.parseInt(tdList.get(8).text().replace(",", ""));
		        		double totalRatio = Double.parseDouble(tdList.get(10).text().replace("%", ""));
		        		double foreignerRatio = Double.parseDouble(tdList.get(9).text().replace("%", ""));
		        		total = (int) (total / totalRatio) * 100;
		        		double mutualFund = Integer.parseInt(tdList.get(6).text().replace(",", ""));
		        		double mutualFundRatio = mutualFund * 100 / total;
		        		mutualFundRatio = StringUtil.setPointLength(mutualFundRatio);	
		        		//System.out.println(foreignerRatio);
		        		list.get(i).setForeignerRatio(foreignerRatio);
		        		//System.out.println(mutualFundRatio);
		        		list.get(i).setMutualFund(mutualFundRatio);
		        	}
		        }	    	
	    	}
        }
        catch (Exception ex)
        {
        	
        }
    }
}
class ValueComparator implements Comparator<String> 
{
    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) 
    {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(String a, String b) 
    {
        if (base.get(a) >= base.get(b)) 
        {
            return -1;
        } 
        else 
        {
            return 1;
        } // returning 0 would merge keys
    }
}
