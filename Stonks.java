import java.util.*;
import java.io.*;

public class Stonks {
    public static final String STOCKS_FILE_NAME = "stonks.tsv";

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to the CSE 122 Stocks Simulator!");
        File newFile = new File(STOCKS_FILE_NAME);
        Scanner stonks = new Scanner(newFile);
        Scanner console = new Scanner (System.in);
        int numberOfStocks = Integer.parseInt(stonks.nextLine());
        String[] stocks = new String[numberOfStocks];
        double[] prices = new double[numberOfStocks];
        double[] portfolio = new double[numberOfStocks];
        stockData(numberOfStocks, stonks, prices, stocks);
        String menuChoice = selectChoice(console);
        while (!menuChoice.equalsIgnoreCase("Q")) {
            if (menuChoice.equalsIgnoreCase("B")){
                buyStock(stonks, console, prices, stocks, portfolio);
            }else if (menuChoice.equalsIgnoreCase("Se")){
                sellStock (stonks, console, portfolio, stocks);
            }else if (menuChoice.equalsIgnoreCase("S")){
                savePortfolio (console, portfolio, stocks);
            }else{
                System.out.println("Invalid choice: " + menuChoice);
                System.out.println("Please try again");
            }
            System.out.println();
            System.out.println("Menu: (B)uy, (Se)ll, (S)ave, (Q)uit");
            System.out.print("Enter your choice: ");
            menuChoice = console.nextLine();
        }
        double stockValue = 0.0;
        for (int i = 0; i < numberOfStocks; i++){
            stockValue = stockValue + (portfolio[i] * prices[i]);
        }
        System.out.println("Your portfolio is currently valued at: $" + stockValue);
        System.out.println(); 
    }
    
    public static void stockData(int numberOfStocks, Scanner stonks,
            double[] prices, String[] stocks) {
        System.out.println("There are " + numberOfStocks + " stocks on the market:");
        stonks.nextLine();
        int count = 0;
        while (stonks.hasNextLine()) {
            String line = stonks.nextLine();
            Scanner lineScanner = new Scanner (line);
            String stockTicker = lineScanner.next();
            double stockValue = Double.parseDouble(lineScanner.next());
            stocks[count] = stockTicker;
            prices[count] = stockValue;
            count ++;
        }
        for (int i = 0; i < numberOfStocks; i++){
            System.out.println(stocks[i] + ": " + prices[i]);
        }
    }

    public static String selectChoice(Scanner console) {
        System.out.println();
        System.out.println("Menu: (B)uy, (Se)ll, (S)ave, (Q)uit");
        System.out.print("Enter your choice: ");
        String menuChoice = console.nextLine();
        return menuChoice;
    }

    public static void buyStock(Scanner stonks, Scanner console, double[] prices,
        String[] stocks, double[] portfolio) {
        System.out.print("Enter the stock ticker: ");
        String desiredStock = console.nextLine();
        System.out.print("Enter your budget: ");
        String budget = console.nextLine();
        double userBudget = Double.parseDouble(budget);
        if (userBudget < 5.00){
            System.out.println("Budget must be at least $5");
        }else{
            for (int i = 0; i < stocks.length; i++){
                String ticker = stocks[i];
                if (ticker.equalsIgnoreCase(desiredStock)){
                    double stocksBought = (userBudget/prices[i]);
                    portfolio[i] = (portfolio[i] +stocksBought);
                    System.out.println("You successfully bought " + desiredStock + ".");
                }
            }
        }

    }

    public static void sellStock(Scanner stonks, Scanner console,
        double[] portfolio, String[] stocks) {
        System.out.print("Enter the stock ticker: ");
        String desiredStock = console.nextLine();
        System.out.print("Enter the number of shares to sell: ");
        String shares = console.nextLine();
        double sharesToSell = Double.parseDouble(shares);
        for (int i = 0; i < stocks.length; i++){
            String ticker = stocks[i];
            if (ticker.equalsIgnoreCase(desiredStock)){
                double sharesAvailable = portfolio[i];
                if (sharesAvailable >= sharesToSell){
                    portfolio[i] = (portfolio[i] - sharesToSell);
                    System.out.println("You successfully sold " + sharesToSell
                        + " shares of " + desiredStock + ".");
                }else{
                    System.out.println("You do not have enough shares of " + desiredStock
                        + " to sell " + sharesToSell + " shares.");
                }
            }
        }
    }
    
    public static void savePortfolio(Scanner console, double[] portfolio,
        String[] stocks) throws FileNotFoundException {
        System.out.print("Enter new portfolio file name: ");
        String fileName = console.nextLine();
        PrintStream output = new PrintStream(new File(fileName));
        for (int i = 0; i < portfolio.length; i++){
            if (portfolio[i] > 0){
                output.println(stocks[i] + " " + portfolio[i]);
            }
        }
    }
}
