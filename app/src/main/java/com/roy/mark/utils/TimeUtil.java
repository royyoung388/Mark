package com.roy.mark.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/7/20.
 */

public class TimeUtil {

    /**
     * 时间戳转日期
     *
     * @param timestampString 时间戳
     * @return 日期
     */
    public static String TimeStamp2Date(Long timestampString) {
        //formats = "yyyy-MM-dd HH:mm:ss";
        String formats = "yyyy-MM-dd";
        timestampString *= 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestampString));
        return date;
    }

    /**
     * 日期转时间戳
     *
     * @param dateStr 日期
     * @return 时间戳
     */
    public static Long Date2TimeStamp(String dateStr) {
        try {
//            String format = "yyyy-MM-dd HH:mm:ss";
            String format = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    public static String getNowDate() {
        String formats = "yyyy-MM-dd";
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date());
        return date;
    }

    /**
     * 日期转毫秒
     * @param date 日期
     * @return 毫秒
     */
    public static Long Date2Mills(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = format.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
