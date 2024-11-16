package interface_adapter.sell_stock;

import use_case.sell_stock.SellStockOutputBoundary;
import use_case.sell_stock.SellStockOutputData;

public class SellStockPresenter implements SellStockOutputBoundary {
    @Override
    public void prepareSuccessView(SellStockOutputData outputData) {

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
