package com.levelmoney.client.api.v2.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The only one of these classes that you couldn't really autogenerate since many of the
 *
 * Created by chris on 11/28/16.
 */
public class LoginRequest implements ApiRequest {
    @JsonProperty("args")
    private CommonArguments args;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

    public LoginRequest(String email, String password) {
        args = new CommonArguments("");
        this.email = email;
        this.password = password;
    }

    public CommonArguments getArgs() {
        return args;
    }

    public void setArgs(CommonArguments args) {
        this.args = args;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
