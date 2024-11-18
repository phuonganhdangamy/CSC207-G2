package use_case.buy_stock;

public interface BuyStockOutputBoundary {

    void prepareSuccessView(BuyStockOutputData outputData);

    void prepareFailView(String errorMessage);

}
