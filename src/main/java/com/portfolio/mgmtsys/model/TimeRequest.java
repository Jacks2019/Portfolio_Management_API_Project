package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeRequest extends Request{
    Date startTime;
    Date endTime;

    public TimeRequest(Date startTime, Date endTime) {
       this.endTime = endTime;
       this.startTime = startTime;
    }
    public TimeRequest(String startTimeStr, String endTimeStr) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (startTimeStr != null) {
                startTime = dateFormat.parse(startTimeStr);
            }
            if (endTimeStr != null) {
                endTime = dateFormat.parse(endTimeStr);
            }
        }catch (Exception e){

        }

    }
    public TimeRequest(){

    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
