package com.levelmoney.client.api.v2.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by chris on 11/28/16.
 */
public class GetProjectedTransactionsForMonthResponse implements ApiResponse {
    @JsonProperty("error")
    private String error;
    @JsonProperty("transactions")
    private List<Transaction> transactions;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
