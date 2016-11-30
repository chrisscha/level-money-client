package com.levelmoney.client.api.v2.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Created by chris on 11/28/16.
 */
public class GetProjectedTransactionsForMonthRequest implements ApiRequest {
    @JsonProperty("args")
    private CommonArguments args;
    @JsonProperty("year")
    private Long year;
    @JsonProperty("month")
    private Long month;

    public GetProjectedTransactionsForMonthRequest(String token) {
        args = new CommonArguments(token);

        //According to the instructions the year and month only work when they are set to this year and date.
        LocalDateTime dt = LocalDateTime.now();
        month = (long) dt.getMonthValue();
        year = (long) dt.getYear();
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public CommonArguments getArgs() {
        return args;
    }

    public void setArgs(CommonArguments args) {
        this.args = args;
    }
}
