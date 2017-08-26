package org.bear.financeAnalysis;

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
	 * 查詢過去
	 * @param days 查詢幾天
	 * @param accumulation 以累積幾日來排名
	 * @param maxSize 最多顯示幾筆股票資料
	 */
	public List<InstitutionalEntity> getOrder(int days, int accumulation, 
			int maxSize, String date, String buyer)
	{
		List<BasicStockWrapper> listStock = basicStockDao.findAllData();
		//外資個股買賣超佔股本比
		Map<String, List<Double>> foreignerStockRatio = new HashMap<String, List<Double>>();
		//外資每日買賣超資料
		List<ThreeBigExchangeEntity> listForeigner;
		//投信個股買賣超佔股本比
		//Map<String, List<Double>> investmentStockRatio = new HashMap<String, List<Double>>();	
		//投信每日買賣超資料
		//List<ThreeBigExchangeEntity> listInvestment;
		//兩大個股買賣超佔股本比
		//Map<String, List<Double>> institutionalStockRatio = new HashMap<String, List<Double>>();
		//兩大每日買賣超資料
		//List<ThreeBigExchangeEntity> listInstitutional;		
		//排序外資
		mapForeigner = new HashMap<String, Double>();
		ValueComparator foreignerVC = new ValueComparator(mapForeigner);
		TreeMap<String, Double> sortedForeigner = new TreeMap<String, Double>(foreignerVC);
		//排序投信
		//mapInvestment = new HashMap<String, Double>();
		//ValueComparator investmentVC = new ValueComparator(mapInvestment);
		//TreeMap<String, Double> sortedInvestment = new TreeMap<String, Double>(investmentVC);
		//兩大排序
		//mapInstitutional = new TreeMap<String, Double>();
		//ValueComparator institutionVC = new ValueComparator(mapInstitutional);
		//TreeMap<String, Double> sortedInstitutional = new TreeMap<String, Double>(institutionVC);
		for (int i = 0; i < listStock.size(); i++)
		{
			//if (!listStock.get(i).getStockID().equals("2421"))
				//continue;
			//得到該股票的外資交易資料
			listForeigner = juristicDailyReportDao.findStockBySize(listStock.get(i).getStockID(), days, buyer);		
			List<Double> ratioList = this.computeAccumulateCapitalRatio(listStock.get(i).getStockID(), listForeigner, listStock.get(i).getCapital(), mapForeigner, accumulation);
			foreignerStockRatio.put(listStock.get(i).getStockID(), ratioList);
			//得到該股票的投信交易資料
			//listInvestment = juristicDailyReportDao.findStockBySize(listStock.get(i).getStockID(), days, "投信");			
			//ratioList = this.computeAccumulateCapitalRatio(listStock.get(i).getStockID(), listInvestment, listStock.get(i).getCapital(), mapInvestment, accumulation);
			//investmentStockRatio.put(listStock.get(i).getStockID(), ratioList);
			//得到該股票的兩大交易資料
			//listInstitutional = juristicDailyReportDao.findStockBySize(listStock.get(i).getStockID(), days, "兩大");			
			//ratioList = this.computeAccumulateCapitalRatio(listStock.get(i).getStockID(), listInstitutional, listStock.get(i).getCapital(), mapInstitutional, accumulation);
			//institutionalStockRatio.put(listStock.get(i).getStockID(), ratioList);
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
	    	listForeignerEntity.add(entity);
	    }
		//投信排序
		/*
		List<InstitutionalEntity> listInvestmentEntity = new ArrayList<InstitutionalEntity>();	
		sortedInvestment.putAll(mapInvestment);
		it = sortedInvestment.keySet().iterator();
		while (it.hasNext()) 
	    {
	    	String stockID = it.next();
	    	List<Double> order = investmentStockRatio.get(stockID);
	    	InstitutionalEntity entity = new InstitutionalEntity();
	    	entity.setStockID(stockID);
	    	entity.setInfo(order);
	    	listInvestmentEntity.add(entity);
	    }
		//兩大 (外資+投信) 排序
		List<InstitutionalEntity> listInstitutionalEntity = new ArrayList<InstitutionalEntity>();	
		sortedInstitutional.putAll(mapInstitutional);
		it = sortedInstitutional.keySet().iterator();
		while (it.hasNext()) 
	    {
	    	String stockID = it.next();
	    	List<Double> order = institutionalStockRatio.get(stockID);
	    	InstitutionalEntity entity = new InstitutionalEntity();
	    	entity.setStockID(stockID);
	    	entity.setInfo(order);
	    	listInstitutionalEntity.add(entity);
	    }*/
		this.consecutiveExchange(listForeignerEntity, days, "外資", maxSize);	
		//this.consecutiveExchange(listInvestmentEntity, days, "投信", maxSize);		
		//this.consecutiveExchange(listInstitutionalEntity, days, "兩大", maxSize);		
		this.majorHolder(listForeignerEntity, maxSize, date);	
		//this.majorHolder(listInvestmentEntity, maxSize, date);	
		//this.majorHolder(listInstitutionalEntity, maxSize, date);	
		//listAllEntity.add(listForeignerEntity);
		//listAllEntity.add(listInvestmentEntity);
		//listAllEntity.add(listInstitutionalEntity);
		return listForeignerEntity;
	}
    public static void main(String[] args) 
    {	    	    
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
        System.out.println("results: " + sorted_map);	    	    
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
    		String url = "http://www.tdcc.com.tw/smWeb/QryStock.jsp";
    		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
    		paramList.add(new BasicNameValuePair("SCA_DATE", dateString));
    		paramList.add(new BasicNameValuePair("SqlMethod", "StockNo"));
    		paramList.add(new BasicNameValuePair("StockNo", list.get(i).getStockID()));
    		paramList.add(new BasicNameValuePair("sub", "查詢"));
    		boolean isSuccessful = false;
    		while (isSuccessful == false)
    		{
    			//400張
    			double totalRatio = 0;
    			//800張
    			double eightHundredRatio = 0;
	    		try
	    		{
	    			String responseString = HttpUtil.send(url, paramList, 1, "big5");
	    			Document doc = Jsoup.parse(responseString);
	    			Element table = doc.select("table").get(7);
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
