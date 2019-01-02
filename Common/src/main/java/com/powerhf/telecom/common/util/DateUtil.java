package com.powerhf.telecom.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {
    private DateUtil() {

    }

    /**
     * 将指定的日期对象按照指定的格式转化为字符串
     */
    public static String format(Date d, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    /**
     * 将字符串按照指定的格式解析为日期对象
     * @param dateString
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateString, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateString);
    }
}
