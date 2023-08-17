package com.portfolio.mgmtsys.model;

/*
 * Name: xiaoyu
 * Date: 2023/8/14
 * Description:
 */

public class BuyAndSellFundRequest extends Request{
    private Integer accountId;

    private String code;

    private Integer amount;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BuyFundRequest{" +
                "accountId=" + accountId +
                ", code='" + code + '\'' +
                ", amount=" + amount +
                '}';
    }
}
