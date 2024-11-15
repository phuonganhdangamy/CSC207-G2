package use_case.buy_stock;

public interface BuyStockOutputBoundary {

    void presentSuccess(BuyStockOutputData outputData);

    void presentError(String errorMessage);

}
