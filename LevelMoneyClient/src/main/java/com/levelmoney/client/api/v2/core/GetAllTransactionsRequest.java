package com.levelmoney.client.api.v2.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chris on 11/22/16.
 */
public class GetAllTransactionsRequest implements ApiRequest {
    @JsonProperty("args")
    private CommonArguments args;

    public GetAllTransactionsRequest(String token) {
        this.args = new CommonArguments(token);
    }

    public CommonArguments getArgs() {
        return args;
    }

    public void setArgs(CommonArguments args) {
        this.args = args;
    }
}
