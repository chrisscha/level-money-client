package com.levelmoney.client.api.v2.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chris on 11/21/16.
 */
public class Transaction {
    @JsonProperty("transaction-id")
    private String transactionId;
    @JsonProperty("account-id")
    private String accountId;
    @JsonProperty("raw-merchant")
    private String rawMerchant;
    @JsonProperty("merchant")
    private String merchant;
    @JsonProperty("is-pending")
    private Boolean isPending;
    @JsonProperty("transaction-time")
    private String transactionTime;
    @JsonProperty("amount")
    private Long amount;
    @JsonProperty("previous-transaction-id")
    private String previousTransactionId;
    @JsonProperty("categorization")
    private String categorization;
    @JsonProperty("memo-only-for-testing")
    private String memoOnlyForTesting;
    @JsonProperty("payee-name-only-for-testing")
    private String payeeNameOnlyForTesting;
    @JsonProperty("clear-date")
    private Long clearDate;
    @JsonProperty("aggregation-time")
    private Long aggregationTime;

    public Long getAggregationTime() {
        return aggregationTime;
    }

    public void setAggregationTime(Long aggregationTime) {
        this.aggregationTime = aggregationTime;
    }

    public String getRawMerchant() {
        return rawMerchant;
    }

    public void setRawMerchant(String rawMerchant) {
        this.rawMerchant = rawMerchant;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public Boolean getPending() {
        return isPending;
    }

    public void setPending(Boolean pending) {
        isPending = pending;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPreviousTransactionId() {
        return previousTransactionId;
    }

    public void setPreviousTransactionId(String previousTransactionId) {
        this.previousTransactionId = previousTransactionId;
    }

    public String getCategorization() {
        return categorization;
    }

    public void setCategorization(String categorization) {
        this.categorization = categorization;
    }

    public String getMemoOnlyForTesting() {
        return memoOnlyForTesting;
    }

    public void setMemoOnlyForTesting(String memoOnlyForTesting) {
        this.memoOnlyForTesting = memoOnlyForTesting;
    }

    public String getPayeeNameOnlyForTesting() {
        return payeeNameOnlyForTesting;
    }

    public void setPayeeNameOnlyForTesting(String payeeNameOnlyForTesting) {
        this.payeeNameOnlyForTesting = payeeNameOnlyForTesting;
    }

    public Long getClearDate() {
        return clearDate;
    }

    public void setClearDate(Long clearDate) {
        this.clearDate = clearDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
