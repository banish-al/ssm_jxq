package com.zilong.utils;

import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DynamicTest;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CreteNumber {
    /**
     * 获取随机数
     * @param index 随机数的长度
     * @return
     */
    public String creteRandomNumber(int index) {
        Random r = new Random();
        String str = "";
        for (int i = 0; i < index; i++){
            int ran1 = r.nextInt(10);
            str += ran1;
        }
        return str;
    }

    // 获取当前日期的编号
    public String creteDateNumber() {
        String[] date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
        String s = StringUtils.join(date);
        return s;
    }

    /**
     * 获取几天前或后的日期
     *
     * @param d
     */
    public String creteDate(int d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前日期
        String str = sdf.format(new Date());
        // 将字符串bai的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        Date date = sdf.parse(str, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, d);
        Date date1 = calendar.getTime();
        String out = sdf.format(date1);
        return out;
    }

    /**
     * 字符串转date
     *
     * @param time 当前日期
     * @return
     */
    public Date dateFormat(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = null;
        try {
            dateTime = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static void main(String[] args) {
    }
}
