package com.levelmoney.client.http;

import com.levelmoney.client.api.v2.core.GetAllTransactionsRequest;
import com.levelmoney.client.api.v2.core.GetAllTransactionsResponse;
import com.levelmoney.client.api.v2.core.GetProjectedTransactionsForMonthRequest;
import com.levelmoney.client.api.v2.core.GetProjectedTransactionsForMonthResponse;
import com.levelmoney.client.api.v2.core.LoginRequest;
import com.levelmoney.client.api.v2.core.LoginResponse;

import java.io.IOException;

import static com.levelmoney.client.ServerConfig.ALL_API;
import static com.levelmoney.client.ServerConfig.LOGIN_API;
import static com.levelmoney.client.ServerConfig.PROJECTED_API;

/**
 * Client that uses the HttpClient to make requests to the levelMoney service.
 * <p>
 * Created by chris on 11/28/16.
 */
public class LevelMoneyClient {
    public LoginResponse callLogin(String email, String password) throws IOException {
        LoginRequest request = new LoginRequest(email, password);

        HttpClient<LoginRequest, LoginResponse> httpClient = new HttpClient<>(LoginResponse.class, LOGIN_API);

        return httpClient.post(request);
    }


    public GetAllTransactionsResponse callGetAllTransactions(String token) throws IOException {
        GetAllTransactionsRequest request = new GetAllTransactionsRequest(token);

        HttpClient<GetAllTransactionsRequest, GetAllTransactionsResponse> httpClient =
                new HttpClient<>(GetAllTransactionsResponse.class, ALL_API);

        return httpClient.post(request);
    }

    public GetProjectedTransactionsForMonthResponse callGetProjectedTransactions(String token) throws IOException {
        GetProjectedTransactionsForMonthRequest request = new GetProjectedTransactionsForMonthRequest(token);

        HttpClient<GetProjectedTransactionsForMonthRequest, GetProjectedTransactionsForMonthResponse> httpClient =
                new HttpClient<>(GetProjectedTransactionsForMonthResponse.class, PROJECTED_API);

        return httpClient.post(request);
    }
}
