package com.levelmoney.client.api.v2.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chris on 11/28/16.
 */
public class LoginResponse implements ApiResponse {
    @JsonProperty("error")
    private String error;
    @JsonProperty("uid")
    private Long uid;
    @JsonProperty("token")
    private String token;
    @JsonProperty("onboarding-stage")
    private String onboardingStage;
    @JsonProperty("agg-status")
    private String aggStatus;
    @JsonProperty("has-accounts-linked")
    private Boolean hasAccountsLinked;

    public String getOnboardingStage() {
        return onboardingStage;
    }

    public void setOnboardingStage(String onboardingStage) {
        this.onboardingStage = onboardingStage;
    }

    public String getAggStatus() {
        return aggStatus;
    }

    public void setAggStatus(String aggStatus) {
        this.aggStatus = aggStatus;
    }

    public Boolean getHasAccountsLinked() {
        return hasAccountsLinked;
    }

    public void setHasAccountsLinked(Boolean hasAccountsLinked) {
        this.hasAccountsLinked = hasAccountsLinked;
    }

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
