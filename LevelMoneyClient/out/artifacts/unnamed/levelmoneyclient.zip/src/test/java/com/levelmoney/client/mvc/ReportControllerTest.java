package com.levelmoney.client.mvc;

import com.levelmoney.client.api.v2.core.GetAllTransactionsResponse;
import com.levelmoney.client.api.v2.core.GetProjectedTransactionsForMonthResponse;
import com.levelmoney.client.api.v2.core.LoginResponse;
import com.levelmoney.client.api.v2.core.Transaction;
import com.levelmoney.client.http.LevelMoneyClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Basic Tests for the  ReportController.
 * Doesn't even come close to testing all of the edge cases and failure cases.
 * <p>
 * Just tests the happy cases and the simplest of failure cases.
 * <p>
 * Created by chris on 11/28/16.
 */
public class ReportControllerTest {

    LevelMoneyClient levelMoneyClientMock;
    ReportController controller;
    List<Transaction> transactionList;
    List<Transaction> projectedList;
    private static final String EMAIL = "foo@bar.com";
    private static final String PASSWORD = "<(-_-<)";
    private static final String ERROR_MESSAGE = "The only winning move is not to pay.";
    private static final String TOKEN = "T0LK1EN";
    private static final String OCTOBER = "2016-10-31";
    private static final String NOVEMBER = "2016-11-01";

    private static final String TRANSACTION_SUMMARY =
            "{\"2016-10\": {\"spent\": \"$15.00\", \"income\": \"$6.00\"}," + "\n" +
                    "\"2016-11\": {\"spent\": \"$10.00\", \"income\": \"$4.00\"}," + "\n" +
                    "\"average\": {\"spent\": \"$12.50\", \"income\": \"$5.00\"}}";

    private static final String NO_DONUT_SUMMARY = //October is missing a $1.00 payment in October
            "{\"2016-10\": {\"spent\": \"$15.00\", \"income\": \"$5.00\"}," + "\n" +
                    "\"2016-11\": {\"spent\": \"$10.00\", \"income\": \"$4.00\"}," + "\n" +
                    "\"average\": {\"spent\": \"$12.50\", \"income\": \"$4.50\"}}";

    private static final String PROJECTED_SUMMARY = //first three lines same as TRANSACTION_SUMMARY
            "{\"2016-10\": {\"spent\": \"$15.00\", \"income\": \"$6.00\"}," + "\n" +
                    "\"2016-11\": {\"spent\": \"$10.00\", \"income\": \"$4.00\"}," + "\n" +
                    "\"average\": {\"spent\": \"$12.50\", \"income\": \"$5.00\"}," + "\n" +
                    "\"projected\": {\"spent\": \"$8.00\", \"income\": \"$2.00\"}}";

    @Before
    public void setUp() {
        levelMoneyClientMock = mock(LevelMoneyClient.class);
        controller = new ReportController(levelMoneyClientMock);

        transactionList = Arrays.asList(
                generateTransaction(OCTOBER, 10000L, true),
                generateTransaction(OCTOBER, 20000L, false),
                generateTransaction(OCTOBER, 30000L, false),
                generateTransaction(OCTOBER, -40000L, false),
                generateTransaction(OCTOBER, -50000L, false),
                generateTransaction(OCTOBER, -60000L, false),
                generateTransaction(NOVEMBER, 10000L, false),
                generateTransaction(NOVEMBER, 10000L, false),
                generateTransaction(NOVEMBER, 20000L, false),
                generateTransaction(NOVEMBER, -20000L, false),
                generateTransaction(NOVEMBER, -20000L, false),
                generateTransaction(NOVEMBER, -60000L, false)
        );

        projectedList = Arrays.asList(
                generateTransaction(NOVEMBER, 10000L, false),
                generateTransaction(NOVEMBER, 10000L, false),
                generateTransaction(NOVEMBER, -20000L, false),
                generateTransaction(NOVEMBER, -60000L, false)
        );
    }

    @Test
    public void testFailedLogin() throws Exception {
        when(levelMoneyClientMock.callLogin(EMAIL, PASSWORD)).thenThrow(new IOException(ERROR_MESSAGE));

        String response = controller.process(EMAIL, PASSWORD, false, false);
        assertEquals(ERROR_MESSAGE, response);
    }

    @Test
    public void testBasicFunctionality() throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(TOKEN);
        when(levelMoneyClientMock.callLogin(EMAIL, PASSWORD)).thenReturn(loginResponse);

        GetAllTransactionsResponse getAllResponse = new GetAllTransactionsResponse();
        getAllResponse.setTransactions(transactionList);
        when(levelMoneyClientMock.callGetAllTransactions(TOKEN)).thenReturn(getAllResponse);

        String response = controller.process(EMAIL, PASSWORD, false, false);
        assertEquals(TRANSACTION_SUMMARY, response);
    }

    @Test
    public void testIgnoreDonuts() throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(TOKEN);
        when(levelMoneyClientMock.callLogin(EMAIL, PASSWORD)).thenReturn(loginResponse);

        GetAllTransactionsResponse getAllResponse = new GetAllTransactionsResponse();
        getAllResponse.setTransactions(transactionList);
        when(levelMoneyClientMock.callGetAllTransactions(TOKEN)).thenReturn(getAllResponse);

        String response = controller.process(EMAIL, PASSWORD, true, false);
        assertEquals(NO_DONUT_SUMMARY, response);
    }

    @Test
    public void testCrystalBall() throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(TOKEN);
        when(levelMoneyClientMock.callLogin(EMAIL, PASSWORD)).thenReturn(loginResponse);

        GetAllTransactionsResponse getAllResponse = new GetAllTransactionsResponse();
        getAllResponse.setTransactions(transactionList);
        when(levelMoneyClientMock.callGetAllTransactions(TOKEN)).thenReturn(getAllResponse);

        GetProjectedTransactionsForMonthResponse getProjectedResponse = new GetProjectedTransactionsForMonthResponse();
        getProjectedResponse.setTransactions(projectedList);
        when(levelMoneyClientMock.callGetProjectedTransactions(TOKEN)).thenReturn(getProjectedResponse);

        String response = controller.process(EMAIL, PASSWORD, false, true);
        assertEquals(PROJECTED_SUMMARY, response);
    }

    private static Transaction generateTransaction(String time, Long amount, boolean isDonut) {
        Transaction t = new Transaction();
        t.setTransactionTime(time);
        t.setAmount(amount);
        if (isDonut) {
            t.setRawMerchant("Krispy Kreme Donuts");
        } else {
            t.setRawMerchant("Not Donuts");
        }
        return t;
    }
}
