/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learningtoprogram;

//Import Statements Listed Alphabetically
import java.io.*;           //used for any type of input or output
import java.util.*;         //useful utilities like Scanner
import hsa.Console;
import hsa.*;
import java.awt.Color;
import java.io.IOException;
import java.lang.NullPointerException;
import java.math.BigDecimal;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockDividend;
import yahoofinance.quotes.stock.StockQuote;
import yahoofinance.quotes.stock.StockStats;

/**
 *
 * @author Admin
 */
public class StockMarketFundamentalAnalyzer {

    /**
     * * MAIN METHOD
     *
     **
     * @param args
     */
    public static void main(String[] args) throws IOException, NullPointerException {

        //FIRST WRITE YOUR PSEUDOCODE USING COMMENTS, THEN FILL IN WITH CODE
        System.out.println("Starting...");
        Console c = new Console();
        //Ask user how many stocks they want to analyze
        c.println("How many stocks do you want to analyze?");
        int stockcount = c.readInt();
        //Create Arrays
        String [] stocktickersymbols = new String [stockcount];
        String [] stocknames = new String [stockcount];
        
        for (int i=0;i<stockcount;i++) {
            //Ask user for the ticker symbol of the stock
            c.println("Enter the ticker symbol of the stock here:");
            stocktickersymbols[i] = c.readLine();
            //Ask user for the name of the stock
            c.println("Enter the name of the stock here:");
            stocknames[i] = c.readLine();
        }
        //Analyze stock
        for (int i=0;i<stockcount;i++) {
            analyzestock(stocktickersymbols[i], stocknames[i]);
        }
    }
    public static void analyzestock (String stocktickersymbol, String stockname) throws IOException, NullPointerException {
        Console c = new Console(30,200);
        //Initialize variables
        String YearHighLowanalysis = null;
        String CFA50analysis = null;
        String CFA200analysis = null;
        String PEanalysis = null;
        String PBanalysis = null;
        String CRanalysis = null;
        String QRanalysis = null;
        String DRanalysis = null;
        String DEanalysis = null;
        //Ask user how much current assets does the company have
        c.println(stockname + "'s Current Assets: ");
        double CA = c.readInt();
        //Ask user how much current liabilities does the company have
        c.println(stockname + "'s Current Liabilities: ");
        double CL = c.readInt();
        //Ask user how much long term assets does the company have
        c.println(stockname + "'s Long Term Assets: ");
        double LTA = c.readInt();
        //Ask user how much long term liabilties does the company have
        c.println(stockname + "'s Long Term Liabilities: ");
        double LTL = c.readInt();
        //Ask user how much inventory does the company have
        c.println(stockname + "'s Inventory: ");
        double Inventory = c.readInt();
        //Solve current ratio
        double CR = CA/CL;
        //Solve quick ratio
        double QR = (CA - Inventory)/CL;
        //When current ratio is under the good range
        if (CR < 1) {
            CRanalysis = "Company does not have enough assets to pay off its short term debts";
            QRanalysis = "Company does not have enough assets";
        }
        //When current ratio is within the good range
        else if (CR >= 1 && CR < 3) {
            CRanalysis = "Company has enough assets to pay off its debt, it is in a good financial state";
            //When quick ratio is less than 1
            if (QR < 1) {
                QRanalysis = "Company does not have enough quick assets to pay off its debt, but it is still in a good financial state";
            }
            //When quick ratio is within the good range
            else if (QR >= 1 && QR <= 2) {
                QRanalysis = "Company is in incredible financial state, it is able to pay off its debt for a long period of time";
            }
            //When quick ratio is over the good range
            else if (QR > 2) {
                QRanalysis = "Company has too many assets, it is not using them efficiently"; 
            }
        }
        //When current ratio is over the good range
        else if (CR >= 3) {
            CRanalysis = "Company has too many assets, it is not using them efficiently"; 
            QRanalysis = "Company has too many assets, it is not using them efficiently"; 
        }
        //Solve debt ratio
        double DR = (CA+LTA)/(CL+LTL);
        //When the debt ratio is over the good range
        if (DR >= 0.5) {
            DRanalysis = "Most of the assets is financed through debt and may not be in a good financial state";
        }
        //When the debt ratio is in the good range
        else if (DR < 0.5) {
            DRanalysis = "Most of the assets is financed through equity and is in a good financial state";
        }
        //When the debt ratio is very high
        else if (DR > 1) {
            DRanalysis = "THIS IS BAD, THERE IS MORE DEBT THAN ASSETS";
        }
        //Solve Debt to Equity ratio
        double DE = (CL+LTL)/((CA+LTA)-(CL+LTL));
        //When the Debt to Equity ratio is over the good range
        if (DE > 1.5) {
            DEanalysis = "Company has too much debt, investing in it will be risky";
        }
        //When the Debt to Equity ratio is within the good range
        else if (DE >= 1 && DE <= 1.5) {
            DEanalysis = "Company is handling their debt in a good way";
        }
        //When the Debt to Equity ratio is under the good range
        else if (DE < 1) {
            DEanalysis = "Company is using debt financing inefficiently";
        }
        //When Debt to Equity ratio is negative
         else if (DE < 1) {
            DEanalysis = "COMPANY IS AT RISK OF BANKTRUPTCY";
        }
        //Convert the ticker symbol as the stock to analyze
        Stock stock = YahooFinance.get(stocktickersymbol);
        //Get the information used to analyze the stock
        StockStats ss = stock.getStats();
        StockDividend sd = stock.getDividend();
        StockQuote sq = stock.getQuote();
        Double Price = sq.getPrice().doubleValue();
        Double CFYH = sq.getChangeFromYearHigh().doubleValue();
        Double CFYL = sq.getChangeFromYearLow().doubleValue();
        //When the change from year high is smaller than the change from year high
        if ((CFYH*-1)<CFYL) {
            YearHighLowanalysis = "The stock is more closer to the Year High than the Year Low";
        }
        //When the change from year high is larger than the change from year high
        else if ((CFYH*-1)>CFYL) {
            YearHighLowanalysis = "The stock is more closer to the Year Low than the Year High";
        }
        Double CFA50 = sq.getChangeFromAvg50().doubleValue();
        //When the current price is higher than the 50 day average
        if (CFA50 > 0) {
            CFA50analysis = "The stock is in a short term positive trend";
        }
        //When the current price is lower than the 50 day average
        else if (CFA50 < 0) {
            CFA50analysis = "The stock is in a short term negative trend";
        }
        Double CFA200 = sq.getChangeFromAvg200().doubleValue();
        //When the current price is higher than the 50 day average
        if (CFA200 > 0) {
            CFA200analysis = "The stock is in a long term positive trend";
        }
        //When the current price is lower than the 50 day average
        else if (CFA200 < 0) {
            CFA200analysis = "The stock is in a long term negative trend";
        }
        Double PE = ss.getPe().doubleValue();
        //When PE ratio is over the good range
        if (PE < 20) {
            PEanalysis = "Stock may have good earnings";
        }
        //When PE ratio is under the good range
        else if (PE > 25) {
            PEanalysis = "The Stock might either performed poorly or is expected to have higher earnings in the future";
        }
        //When PE ratio is in the good range
        else if (PE >= 20 && PE <= 25) {
            PEanalysis = "This is an average Price/Earnings Ratio"; 
        }
        Double PB = ss.getPriceBook().doubleValue();
        //When PB is over the good range
        if (PB > 3) {
            PBanalysis = "Stock is overvalued or an established stock";
        }
        //When PB is in the good range
        else if (PB >= 1 && PB <= 3) {
            PBanalysis = "This is an average Price/Book Ratio";
        }
        //When PB is under the good range
        else if (PB < 1) {
            PBanalysis = "Stock is undervalued, you can invest at a bargain price :)";
        }
        //Print analysis
        c.println("                  Numbers                  | Good Range  | " + stockname + "    |                Analysis             ");
        c.println("===========================================================================================================================");  
        c.println("TREND ANALYSIS");
        c.println("  Price                                    |      -      | " + Price + "        | ");
        c.println("  Change From Year High                    |      -      | " + CFYH + "         | ");
        c.println("  Change From Year Low                     |      -      | " + CFYL + "         | " + YearHighLowanalysis);
        c.println("  Change From Average Price of 50 days     |      -      | " + CFA50 + "        | " + CFA50analysis);
        c.println("  Change From Average Price of 200 days    |      -      | " + CFA200 + "       | " + CFA200analysis);
        c.println("LIQUIDITY RATIOS");
        c.println("  Current Ratio                            |    1 - 3    | " + CR + "           | " + CRanalysis);
        c.println("  Quick Ratio                              |    1 - 2    | " + QR + "           | " + CRanalysis);
        c.println("LEVERAGE RATIOS");
        c.println("  Debt  Ratio                              |Less than 0.5| " + DR + "           | " + DRanalysis);
        c.println("  Debt to Equity  Ratio                    |   1 - 1.5   | " + DE + "           | " + DEanalysis);
        c.println("PROFITABILITY RATIOS");
        //When the program can find the company's Return on Asset
        if (ss.getEps() != null && ss.getSharesOutstanding() != null) {
            //Get Earnings per Share
            Double EPS = ss.getEps().doubleValue();
            //Get Shares Outstanding
            Double SO = ss.getSharesOutstanding().doubleValue();
            //Solve Return on Asset
            Double ROA = ((EPS*SO)/(CA+LTA))/10;
            String ROAanalysis = null;
            //When the Return on Asset is very high
            if (ROA >= 20) {
                ROAanalysis = "This is a very high Return on Asset, it would make an INCREDIBLE investment";
            }
            //When the Return on Asset is good
            else if (ROA >= 5 && ROA < 20) {
                ROAanalysis = "The Return on Asset is good, you will recieve a good return";
            }
            //When the Return on Asset is below the good range
            if (ROA < 5) {
                ROAanalysis = "Company is not using their assets efficiently ";
            }
            //When the Return on Asset is negative
            if (ROA < 1) {
                ROAanalysis = "COMPANY IS LOSING MONEY";
            }
            c.println("  Return on Asset                          | More than 5 | " + ROA + "%" + "           | " + ROAanalysis);
        }
        c.println("MARKET VALUE RATIOS");
        c.println("  Price/Earnings Ratio                     |   20 - 25   | " + PE + "           | " + PEanalysis);
        c.println("  Price/Book Ratio                         |    1 - 3    | " + PB + "           | " + PBanalysis);
        //When the company gives away dividends
        if (sd.getAnnualYieldPercent() != null) {
            //Get Annual Yield Percent
            Double AYP = sd.getAnnualYieldPercent().doubleValue();
            String AYPanalysis = " ";
            c.println("DIVIDEND ANALYSIS");
            //When the dividend yield is too high
            if (AYP > 7) {
                AYPanalysis = "The divided yield perecent is too high, company may be struggling and is desperate for investors";
            }
            c.println("  Dividend Yield Percent                   | Less than 7 | " + AYP + "%" + "           | " + AYPanalysis);
        }
        //When the company gives away dividends
        if (sd.getAnnualYield() != null) {
            //Get Annual Yield
            Double AY = sd.getAnnualYield().doubleValue();
            c.println("  Dividend Yield                           |      -      | " + AY + "           | ");
        }
    }
}
