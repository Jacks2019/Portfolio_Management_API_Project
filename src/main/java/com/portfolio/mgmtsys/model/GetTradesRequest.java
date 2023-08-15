package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */

import java.util.Date;

public class GetTradesRequest {
   //    登陆ID，交易时间段（默认近七天）
   private Integer accountId;

   private Date startTime;

   private Date endTime;

   public Integer getAccountId() {
      return accountId;
   }

   public void setAccountId(Integer accountId) {
      this.accountId = accountId;
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
