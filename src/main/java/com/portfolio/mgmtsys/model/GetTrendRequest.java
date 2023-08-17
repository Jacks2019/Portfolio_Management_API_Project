package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */

public class GetTrendRequest extends TimeRequest{
   //    登陆ID，交易时间段（默认近七天）
   private Integer accountId;


   public Integer getAccountId() {
      return accountId;
   }

   public void setAccountId(Integer accountId) {
      this.accountId = accountId;
   }

}
