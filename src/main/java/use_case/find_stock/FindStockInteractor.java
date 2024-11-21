package use_case.find_stock;

public class FindStockInteractor implements FindStockInputBoundary{

    private final FindStockOutputBoundary findStockPresenter;
    private FindStockDataAccessInterface findStockUserDataAccess;

    public FindStockInteractor(FindStockDataAccessInterface findStockUserDataAccess,
                               FindStockOutputBoundary findStockPresenter) {
        this.findStockUserDataAccess = findStockUserDataAccess;
        this.findStockPresenter = findStockPresenter;
    }

    @Override
    public void execute(FindStockInputData findStockInputData) {
        String tickerSymbol = findStockInputData.getTickerSymbol();
        boolean stockExist = findStockUserDataAccess.isStockExist(tickerSymbol);

        if (!stockExist) {

            findStockPresenter.prepareFailView(tickerSymbol + " does not exist.");
        }
        else {
            double currentCost = findStockUserDataAccess.getCost(tickerSymbol);
            FindStockOutputData outputData = new FindStockOutputData(tickerSymbol, currentCost);
            findStockPresenter.prepareSuccessView(outputData);
        }

    }
}
