package org.bear.token;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


/** * Created by yy on 2017/10/17. * 獲取url資訊的工具類 */
public class GetUrlData {
    /** * 破解X-CSRF-Token 跨站請求偽造限制 * @param url * @return * @throws Exception */
    public static String getX_CSRF_Token(String url) throws Exception{

        CsrfToken  csrfToken=new CsrfToken(url);
        //設定頭資訊
        Map<String,String> args=new HashMap<String, String>();
        args.put("Cookie",csrfToken.getCookie());
        args.put("X-CSRF-Token",csrfToken.getCsrf_token());
        args.put("X-Requested-With","XMLHttpRequest");
        args.put("Accept","application/json, text/javascript, */*; q=0.01");

        URLConnection connection=GetUrlData.getConnection(url,args);
        //獲取資料
        return GetUrlData.getStringByConnection(connection);
    }

    /** * 根據傳入的資料獲取URLConnection物件 * @param url * @param mapArgs * @return * @throws Exception */
    public static URLConnection getConnection(String url, Map<String,String> mapArgs)throws Exception{
        //設定請求的頭資訊
        URL urlInfo = new URL(url);
        URLConnection connection = urlInfo.openConnection();
        //設定傳入的頭資訊
        if (mapArgs!=null){
            for(String key:mapArgs.keySet()){
                connection.addRequestProperty(key,mapArgs.get(key));
            }
        }
        //設定預設頭資訊
        connection.addRequestProperty("Host", urlInfo.getHost());
        connection.addRequestProperty("Connection", "keep-alive");
        connection.addRequestProperty("Cache-Control", "max-age=0");
        connection.addRequestProperty("Upgrade-Insecure-Requests", "1");
        //表示使用者不願意目標站點追蹤使用者個人資訊。
        connection.addRequestProperty("DNT", "1");
        //強制要求快取伺服器在返回快取的版本之前將請求提交到源頭伺服器進行驗證。
        connection.addRequestProperty("Pragma", "no-cache");
        connection.addRequestProperty("Accept", "*/*");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36");
        connection.addRequestProperty("Referer", "http://"+urlInfo.getHost());
        connection.connect();

        return connection;
    }

    /** * 將獲取的連結讀取對應的資料 * @param connection * @return */
    public static String getStringByConnection(URLConnection connection) throws Exception{
        //定義返回資料的格式
        InputStreamReader input = new InputStreamReader(connection.getInputStream(),"UTF-8");
        BufferedReader reader = new BufferedReader(input);
        StringBuilder data = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null) {
            data.append(str);
        }
        //關閉操作流
        reader.close();
        input.close();
        return data.toString();
    }
}