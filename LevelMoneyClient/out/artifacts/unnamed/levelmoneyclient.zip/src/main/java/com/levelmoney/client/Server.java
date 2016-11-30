package com.levelmoney.client;

import com.levelmoney.client.http.LevelMoneyClient;
import com.levelmoney.client.mvc.ReportController;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * The "server" that runs when you execute the program.
 * Aggregates transactions per month and calculates an average.
 * Optionally, removes donut transactions and adds this month's projections.
 * <p>
 * Created by chris on 11/15/16.
 */
public class Server {
    private static final boolean HAS_FIELD = true;
    private static final boolean NO_FIELD = false;
    private static final String EMAIL_ARG = "email";
    private static final String PASSWORD_ARG = "password";
    private static final String IGNORE_DONUTS_ARG = "ignoreDonuts";
    private static final String CRYSTAL_BALL_ARG = "crystalBall";


    public static void main(String[] args) {
        Option emailOption = new Option(EMAIL_ARG, HAS_FIELD, "login email");
        emailOption.setRequired(true);
        Option passwordOption = new Option(PASSWORD_ARG, HAS_FIELD, "login password");
        passwordOption.setRequired(true);
        Option ignoreDonutsOption = new Option(IGNORE_DONUTS_ARG, NO_FIELD, "do not show donut transactions");
        Option crystalBallOption = new Option(CRYSTAL_BALL_ARG, NO_FIELD, "look into the future...");

        Options options = new Options();
        options.addOption(emailOption);
        options.addOption(passwordOption);
        options.addOption(ignoreDonutsOption);
        options.addOption(crystalBallOption);

        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("LevelMoney Get Transactions Tool", options);
            System.exit(1);
        }

        String email = cmd.getOptionValue(EMAIL_ARG);
        String password = cmd.getOptionValue(PASSWORD_ARG);
        boolean ignoreDonuts = cmd.hasOption(IGNORE_DONUTS_ARG);
        boolean crystalBall = cmd.hasOption(CRYSTAL_BALL_ARG);

        ReportController reportController = new ReportController(new LevelMoneyClient());
        System.out.println(reportController.process(email, password, ignoreDonuts, crystalBall));
    }
}
