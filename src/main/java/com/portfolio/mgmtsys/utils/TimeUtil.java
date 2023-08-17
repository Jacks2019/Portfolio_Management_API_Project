package com.portfolio.mgmtsys.utils;

/*
 * Name: xiaoyu
 * Date: 2023/8/16
 * Description:
 */

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static Date getNowTime() {
        Date time = new Date();
        // 创建 Calendar 对象并设置为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return time;
    }
}
