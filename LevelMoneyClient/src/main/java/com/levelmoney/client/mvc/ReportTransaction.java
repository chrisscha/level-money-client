package com.levelmoney.client.mvc;

import com.levelmoney.client.util.Collectable;
import com.levelmoney.client.util.FormatUtils;

/**
 * The model for the month summaries.
 * Also implements Collectable so that we can collect them when we aggregate the transactions.
 * <p>
 * Created by chris on 11/27/16.
 */
public class ReportTransaction implements Collectable<ReportTransaction> {
    private String month;
    private Long spend;
    private Long income;

    public ReportTransaction() {
        this("", 0L, 0L);
    }

    public ReportTransaction(String month, Long amount) {
        this.month = month;
        if (amount < 0) {
            spend = Math.abs(amount);
            income = 0L;
        } else {
            spend = 0L;
            income = amount;
        }
    }

    public ReportTransaction(String month, Long spend, Long income) {
        this.month = month;
        this.spend = spend;
        this.income = income;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getSpend() {
        return spend;
    }

    public void setSpend(Long spend) {
        this.spend = spend;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public void addIncome(Long newIncome) {
        this.income += newIncome;
    }

    public void addSpend(Long newSpend) {
        this.spend += newSpend;
    }

    @Override
    public ReportTransaction supply() {
        return new ReportTransaction();
    }

    @Override
    public void accumulate(ReportTransaction t) {
        if (!t.getMonth().equals("")) {
            this.setMonth(t.getMonth());
        }
        this.addSpend(t.getSpend());
        this.addIncome(t.getIncome());
    }

    @Override
    public ReportTransaction combine(ReportTransaction t) {
        this.accumulate(t);
        return this;
    }

    //I could almost use a Json string builder, but since the name is the same as the value of the month and then
    // would also be the name of the combined spend/income pair, building the object by hand is easier.
    @Override
    public String toString() {
        return new StringBuilder()
                .append("\"").append(getMonth()).append("\": ")
                .append("{\"spent\": \"").append(FormatUtils.convertLongToCurrency(getSpend())).append("\", ")
                .append("\"income\": \"").append(FormatUtils.convertLongToCurrency(getIncome())).append("\"}")
                .toString();
    }
}
