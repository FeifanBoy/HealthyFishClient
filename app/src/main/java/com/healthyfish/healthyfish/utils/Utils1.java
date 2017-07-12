package com.healthyfish.healthyfish.utils;

import android.content.Context;
import android.util.Log;
import android.view.Window;

import com.healthyfish.healthyfish.ui.widget.DatePickerDialog;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述：
 * 作者：WKJ on 2017/7/5.
 * 邮箱：
 * 编辑：WKJ
 */

public class Utils1 {

    //获取本机时间
    public static String getTime() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH);
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        StringBuffer stringBuffer = new StringBuffer().append(year).append("年").append(month).append("月").append(day).append("日");
        String date = stringBuffer.toString();
        return date;
    }

    //直接通过日期获取是星期几
    public static String getWeekFromDate(){
        Calendar calendar = Calendar.getInstance();//获取日历实例
        Date date1 = calendar.getTime();//获取当前的时间
        calendar.setTime(date1);//将获取的日期设置成当前的日期
        int number = calendar.get(Calendar.DAY_OF_WEEK);
        /*
        *获取当前日期是星期几
        * 经测试，星期天是1，星期三是4，星期六是7
        */
        String [] str = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
        Log.i("testdate",str[number]);
        return str[number];
    }


    //将字符串的日期转化为真正的日期格式，获取该日期是星期几,格式如 ： 2017年7月30日
    public static String getWeekFromStr(String dateStr){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");//设置想要的日期格式
        Calendar calendar = Calendar.getInstance();//获取日历实例
        Date date = dateFormat.parse(dateStr, new ParsePosition(0));//反向操作，将字符格式的转化为日期格式
        calendar.setTime(date);//将转化回来的日期设置成当前的日期
        int number = calendar.get(Calendar.DAY_OF_WEEK);
        /*
        *获取当前日期是星期几
        * 经测试，number显示： 星期天是1，星期三是4，星期六是7
        */
        String [] str = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
        Log.i("testdate",str[number]);
        return str[number];
    }

}
