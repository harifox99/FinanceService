package org.bear.token;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.net.URLConnection;
import org.jsoup.nodes.Element;

/** * Created by yuyu on 2018/3/23. * 獲取處理CSRF需要的相關資訊 * 原理: * 每個使用者Session生成了一個CSRF Token, * 該Token可用於驗證登入使用者和發起請求者是否是同一人, * 如果不是則請求失敗。 */
public class CsrfToken 
{
    String cookie;//用於請求的站點的cokie
    String csrf_token;//用於請求站點的金鑰
    /** 
     * 接收一個網站的host,設定介面請求需要的資料 
     * 1、獲取網站請求回來的Set-Cookie欄位 
     * 2、然後獲取內容中的金鑰 * <meta content="金鑰" name="csrf-token" /> 
     *  @param url
     */
    public CsrfToken(String url) 
    {
        //校驗傳輸安全
        try
        {
        	URLConnection connection= GetUrlData.getConnection(url, null);
            //獲取請求回來的資訊
            String data = GetUrlData.getStringByConnection(connection);
            //System.out.println(data);
            //匹配token
            Document xmlDoc =  Jsoup.parse(data);
            Element link = xmlDoc.select("form").first();
	        Element row = link.select("input").first();
	        csrf_token = row.attr("value");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getCookie() 
    {
        return cookie;
    }

    public void setCookie(String cookie) 
    {
        this.cookie = cookie;
    }

    public String getCsrf_token() 
    {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) 
    {
        this.csrf_token = csrf_token;
    }
    public static void main(String args[])
    {
        String url = "https://www.tdcc.com.tw/portal/zh/smWeb/qryStock";
        try
        {
        	CsrfToken token = new CsrfToken(url);
        	System.out.println(token.getCsrf_token());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}