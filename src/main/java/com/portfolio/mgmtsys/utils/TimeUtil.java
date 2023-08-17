package com.portfolio.mgmtsys.utils;

/*
 * Name: xiaoyu
 * Date: 2023/8/16
 * Description:
 */

import com.portfolio.mgmtsys.model.TimeRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtil {

    public static Date getNowTime() {
        Date time = new Date();
        // 创建 Calendar 对象并设置为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return time;
    }

    public static Date[] extractedTime(TimeRequest request) {
        Date[] time = new Date[2];
        time[1] = TimeUtil.getNowTime();
        // 创建 Calendar 对象并设置为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time[1]);
        // 将日期往前推7天
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        // 获取七天前的时间
        time[0] = calendar.getTime();

        time[0] = request.getStartTime() == null ? time[0]: request.getStartTime();// 设置开始时间
        time[1] = request.getEndTime() == null ? time[1] : request.getEndTime();// 设置结束时间
        return time;
    }

    public static  <T> Specification<T> timeLimit(Date startTime, Date endTime, Example<T> example) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(dateFormat.format(startTime));
//        System.out.println(dateFormat.format(endTime));
        return (Specification<T>) (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.greaterThan(root.get("time"), startTime));
            predicates.add(builder.lessThan(root.get("time"),endTime));
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
