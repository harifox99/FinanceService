package org.bear.cron;

import org.bear.dao.BranchDetailDao;
import org.bear.dao.DealerBranchDao;
import org.bear.datainput.MoneyDjCrawler;
import org.bear.entity.BranchDetailEntity;
import org.bear.entity.DealerBranchEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
/**
 * 券商分點爬蟲批次作業
 */
public class MoneyDjBatchRunner
{

	// 唯一允許使用 static 的 main 方法
	public static void main(String[] args)
	{
		MoneyDjBatchRunner runner = new MoneyDjBatchRunner();
		runner.executeBatch(true);
	}

	public void executeBatch(boolean isWeek)
	{
		System.out.println("--- 啟動券商分點爬蟲批次作業 ---");

		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		DealerBranchDao dealerBranchDao = (DealerBranchDao) context.getBean("dealerBranchDao");
		BranchDetailDao branchDetailDao = (BranchDetailDao) context.getBean("branchDetailDao");

		List<DealerBranchEntity> dealerBranches = dealerBranchDao.queryAll();

		if (dealerBranches == null || dealerBranches.isEmpty())
		{
			System.out.println("找不到任何券商分點資料，程式終止。");
			return;
		}

		MoneyDjCrawler crawler = new MoneyDjCrawler();

		for (DealerBranchEntity branch : dealerBranches)
		{
			String day = "";
			if (isWeek)
				day = "&c=E&d=5";
			else
				day = "&c=B&e=2026-7-6&f=2026-7-10";
			// 組成每個分點的目標 URL
			String targetUrl = "https://www.moneydj.com/z/zg/zgb/zgb0.djhtm?a=" + branch.getGroupCode() + "&b="
					+ branch.getCode() + day;
			
			System.out.println("開始抓取: " + branch.getName() + " (" + branch.getCode() + ") - 網址: " + targetUrl);

			// 呼叫爬蟲
			List<BranchDetailEntity> detailList = crawler.crawlData(targetUrl);

			if (detailList != null && !detailList.isEmpty())
			{
				int insertCount = 0;

				for (BranchDetailEntity detail : detailList)
				{
					try
					{
						// 【新增邏輯】將當前分點的 Code 塞入明細 Entity 中
						detail.setCode(branch.getCode());

						// 【修改邏輯】檢查時加入 Code 作為複合主鍵的條件
						BranchDetailEntity existingData = branchDetailDao.queryById(detail.getStockId(),
								detail.getExchangeDate(), detail.getCode());

						if (existingData == null)
						{
							boolean isSuccess = branchDetailDao.insert(detail);
							if (isSuccess)
							{
								insertCount++;
							}
							else
							{
								System.out.println("Data Duplicate!");
								System.exit(1);
							}
						}
					}
					catch (Exception e)
					{
						System.err.println("寫入資料庫失敗 - StockId: " + detail.getStockId() + ", 券商代碼: " + detail.getCode()
								+ ", 錯誤: " + e.getMessage());
					}
				}
				System.out.println(">> 完成寫入分點: " + branch.getName() + "，共新增 " + insertCount + " 筆資料。\n");
			} 
			else
			{
				System.out.println(">> 分點: " + branch.getName() + " 本日無資料或抓取失敗。\n");
			}

			// 暫停 1.5 秒
			pause(1500);
		}

		System.out.println("--- 所有分點批次作業執行完畢 ---");
	}

	private void pause(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}