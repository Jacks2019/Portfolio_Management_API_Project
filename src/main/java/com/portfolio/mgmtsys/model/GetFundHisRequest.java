package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/17
 * Description:
 */

import java.util.Date;

public class GetFundHisRequest extends TimeRequest{
    private String code;

    public GetFundHisRequest(String code, Date startTime, Date endTime) {
        super(startTime, endTime);
        this.code = code;
    }
    public GetFundHisRequest(){

    }

    public GetFundHisRequest(String code, String startTime, String endTime) {
        super(startTime, endTime);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
