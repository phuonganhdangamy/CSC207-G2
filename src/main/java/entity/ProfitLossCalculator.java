package entity;

public class ProfitLossCalculator {
    private Portfolio portfolio;

    public ProfitLossCalculator(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    // Method to calculate total profit or loss
//    public double calculateTotalProfitLoss() {
//        double totalProfitLoss = 0.0;
//
//        // Loop through each stock entry in the portfolio
//        for (Portfolio.StockEntry entry : portfolio.getStocksOwned()) {
//            double purchasePrice = entry.getPurchasePrice(); // Use stored purchase price
//            double currentPrice = entry.getStock().getCurrentPrice();
//            int numShares = entry.getNumShares();
//
//            // Calculate profit/loss for this stock and add to total
//            totalProfitLoss += (currentPrice - purchasePrice) * numShares;
//        }
//
//        return totalProfitLoss;
//    }
//
//    // Method to calculate profit or loss for a specific stock
//    public double calculateStockProfitLoss(String tickerSymbol) {
//        for (Portfolio.StockEntry entry : portfolio.getStocksOwned()) {
//            if (entry.getStock().getTickerSymbol().equalsIgnoreCase(tickerSymbol)) {
//                double purchasePrice = entry.getPurchasePrice();
//                double currentPrice = entry.getStock().getCurrentPrice();
//                int numShares = entry.getNumShares();
//
//                return (currentPrice - purchasePrice) * numShares;
//            }
//        }
//        System.out.println("Stock with ticker " + tickerSymbol + " not found in portfolio.");
//        return 0.0;
//    }
}