package org.bear.token;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** * Created by yy on 2017/12/19. * 用於正則的工具類 */
public class RegexFinder {

    /** * 匹配一個數據與正則,返回匹配的資料 * * @param regex * @param info * @return * @throws Exception */
    public  static String findOne(String regex,String info){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(info);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }

    /** * 匹配一個數據與正則,返回匹配的資料 * * @param regex * @param info * @return * @throws Exception */
    public  static String findOneByReplaceEmpty(String regex,String info, String replace)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(info);
        if(matcher.find()){
            return matcher.group().replaceAll(replace,"");
        }
        return null;
    }

    /** * 獲取正則匹配全部可能,到list資料 * @param regex * @param info * @return 成功返回一個 */
    public  static List<String> getAllToList(String regex,String info){
        //資料安全校驗
        if(StringUtils.isEmpty(regex)||StringUtils.isEmpty(info)){
            return null;
        }
        List<String> back=new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(info);
        while (matcher.find()){
            back.add(matcher.group());
        }
        return back;
    }

    /** * 獲取正則匹配全部可能,到list資料,替換字元中得資料 * @param regex * @param info * @param replace * @return */
    public  static List<String> getAllToListByReplaceEmpty(String regex,String info
            , String replace) {
        //資料安全校驗
        if(StringUtils.isEmpty(regex)||StringUtils.isEmpty(info)){
            return null;
        }
        List<String> back=new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(info);
        while (matcher.find()){
            back.add(matcher.group().replaceAll(replace,""));
        }
        return back;
    }
}