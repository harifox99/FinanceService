package org.bear.cron;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bear.main.BuildThreeBigExchange;
import org.bear.util.StringUtil;
/**
 * Cron Job update 三大法人資料
 * @author edward
 *
 */
public class ThreeBigUpdate
{
	protected void update()
	{
		BuildThreeBigExchange threeBig = new BuildThreeBigExchange();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date today = new Date();        
		String dateString = dateFormat.format(today);
		dateString = StringUtil.convertChineseYear(dateString.substring(0, 4)) + dateString.substring(4, 10);
		String[] date = {dateString};
		threeBig.update(date);
	}
}
