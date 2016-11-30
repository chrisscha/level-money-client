package com.levelmoney.client.api.v2.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chris on 11/22/16.
 */
public class CommonArguments {

    public CommonArguments(String token) {
        this.token = token;
        this.uid = 1110590645L;
        this.apiToken = "AppTokenForInterview";
        this.jsonStrictMode = false;
        this.jsonVerboseResponse = false;
    }

    @JsonProperty("uid")
    private Long uid;
    @JsonProperty("token")
    private String token;
    @JsonProperty("api-token")
    private String apiToken;
    @JsonProperty("json-strict-mode")
    private Boolean jsonStrictMode;
    @JsonProperty("json-verbose-response")
    private Boolean jsonVerboseResponse;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Boolean getJsonStrictMode() {
        return jsonStrictMode;
    }

    public void setJsonStrictMode(Boolean jsonStrictMode) {
        this.jsonStrictMode = jsonStrictMode;
    }

    public Boolean getJsonVerboseResponse() {
        return jsonVerboseResponse;
    }

    public void setJsonVerboseResponse(Boolean jsonVerboseResponse) {
        this.jsonVerboseResponse = jsonVerboseResponse;
    }
}
