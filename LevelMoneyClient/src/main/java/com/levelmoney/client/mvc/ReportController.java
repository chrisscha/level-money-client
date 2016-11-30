package com.levelmoney.client.mvc;

import com.levelmoney.client.api.v2.core.GetAllTransactionsRequest;
import com.levelmoney.client.api.v2.core.GetAllTransactionsResponse;
import com.levelmoney.client.api.v2.core.GetProjectedTransactionsForMonthRequest;
import com.levelmoney.client.api.v2.core.GetProjectedTransactionsForMonthResponse;
import com.levelmoney.client.api.v2.core.Transaction;
import com.levelmoney.client.http.HttpClient;
import com.levelmoney.client.http.LevelMoneyClient;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.levelmoney.client.util.FormatUtils.extractMonth;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * The controller containing the business logic for calling the levelMoney service and converting to a String.
 * I named the package MVC for model view controller, but the view is just a string and the "model" doesn't really
 * reflect what's in the service. Maybe model view presenter would have made more sense.
 * <p>
 * Basically, the business logic goes here.
 * <p>
 * Created by chris on 11/22/16.
 */
public class ReportController {
    private static final String AVERAGE_NAME = "average";
    private static final String FUTURE_NAME = "projected";
    private static final Predicate<Transaction> FILTER_DONUTS =
            t -> !(t.getRawMerchant().equals("Krispy Kreme Donuts") || t.getRawMerchant().equals("DUNKIN #336784"));
    private static final Predicate<Transaction> NO_FILTER = t -> true;

    private final LevelMoneyClient levelMoneyClient;

    public ReportController(LevelMoneyClient levelMoneyClient) {
        this.levelMoneyClient = levelMoneyClient;
    }

    private static GetAllTransactionsResponse callGetAllTransactions(String token) throws IOException {
        GetAllTransactionsRequest request = new GetAllTransactionsRequest(token);

        HttpClient<GetAllTransactionsRequest, GetAllTransactionsResponse> httpClient =
                new HttpClient<>(GetAllTransactionsResponse.class, "get-all-transactions");

        return httpClient.post(request);
    }

    private static GetProjectedTransactionsForMonthResponse callGetProjectedTransactions(String token) throws IOException {
        GetProjectedTransactionsForMonthRequest request = new GetProjectedTransactionsForMonthRequest(token);

        HttpClient<GetProjectedTransactionsForMonthRequest, GetProjectedTransactionsForMonthResponse> httpClient =
                new HttpClient<>(GetProjectedTransactionsForMonthResponse.class, "projected-transactions-for-month");

        return httpClient.post(request);
    }

    public String process(String email, String password, Boolean ignoreDonuts, Boolean crystalBall) {
        Predicate<Transaction> donutFilter = NO_FILTER;
        if (ignoreDonuts) {
            donutFilter = FILTER_DONUTS;
        }

        GetAllTransactionsResponse allResponse;
        GetProjectedTransactionsForMonthResponse projectedResponse;

        try {
            String token = levelMoneyClient.callLogin(email, password).getToken();
            allResponse = levelMoneyClient.callGetAllTransactions(token);
            if (crystalBall) {
                projectedResponse = levelMoneyClient.callGetProjectedTransactions(token);
            } else {
                projectedResponse = null;
            }
        } catch (IOException e) {
            return e.getMessage();
        }

        if (allResponse != null) {
            List<Transaction> transactions = allResponse.getTransactions();
            if (transactions == null || transactions.isEmpty()) {
                return "No transactions found!";
            }


            Collector<ReportTransaction, ReportTransaction, ReportTransaction> monthlySumCollector = Collector.of(
                    ReportTransaction::new,
                    ReportTransaction::accumulate,
                    ReportTransaction::combine,
                    IDENTITY_FINISH //since Collector is <T, R, R> instead of <T, A, R> we can do this

            );

            // 1. Convert from server response to month summaries. Filter out donuts if option chosen
            // map-reduce makes a bit more sense that map-collect here, but I'm taking advantage of groupingBy to
            //sort by month, and it wants a Collector, so... (yeah, it's a little weird to have a Collector <T,T,T>)
            Map<String, ReportTransaction> reportTransactions = transactions.stream()
                    .filter(donutFilter)
                    .map(t -> new ReportTransaction(extractMonth(t.getTransactionTime()), t.getAmount()))
                    .collect(Collectors.groupingBy(ReportTransaction::getMonth, monthlySumCollector));

            //2. calculate sums. With longs, the values overflow around 922Trillion. I expect we'd use a different scale for Zimbabwe dollars.
            //Overflows are going to be a real problem with those currencies. You can calculate a running average, but its slower and less accurate.
            Long totalSpend = reportTransactions.values().stream()
                    .mapToLong(ReportTransaction::getSpend)
                    .sum();
            Long totalIncome = reportTransactions.values().stream()
                    .mapToLong(ReportTransaction::getIncome)
                    .sum();

            //3. Calculate average and build "average" ReportTransaction
            //I'm defining the number of months for calculating the average as the number of active months.
            //Mostly, because it's easier. But you can argue that if you aren't active for 6 months it doesn't make any sense
            // to trend your average income and spend values towards zero.
            int numMonths = reportTransactions.values().size();
            long averageSpend = totalSpend / numMonths;
            long averageIncome = totalIncome / numMonths;
            ReportTransaction average = new ReportTransaction(AVERAGE_NAME, averageSpend, averageIncome);

            //4. Add the average to the map
            reportTransactions.put(AVERAGE_NAME, average);

            //5. Add projected/future transactions to the transactions if there are any
            if (projectedResponse != null) {
                List<Transaction> projectedTransactions = projectedResponse.getTransactions();

                if (!projectedTransactions.isEmpty()) {
                    ReportTransaction projectedTransaction = projectedTransactions.stream()
                            .filter(donutFilter)
                            .map(t -> new ReportTransaction(FUTURE_NAME, t.getAmount()))
                            .collect(monthlySumCollector);

                    reportTransactions.put(FUTURE_NAME, projectedTransaction);
                }
            }


            //6. Sort by month name and generate String
            return reportTransactions.values().stream()
                    .sorted(Comparator.comparing(ReportTransaction::getMonth))
                    .map(ReportTransaction::toString)
                    .collect(Collectors.joining(",\n", "{", "}"));
        }
        return "Unable to retrieve transactions!";
    }
}
