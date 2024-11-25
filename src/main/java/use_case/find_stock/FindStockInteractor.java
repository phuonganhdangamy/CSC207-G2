package use_case.find_stock;

/**
 * Interactor for find stock use case.
 */
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

        if (tickerSymbol == null || tickerSymbol.isEmpty()) {
            findStockPresenter.prepareFailView("Input cannot be empty.");
        }
        else if (!findStockUserDataAccess.isStockExist(tickerSymbol)) {
            // System.out.println(findStockUserDataAccess.getCost(tickerSymbol));
            findStockPresenter.prepareFailView(tickerSymbol + " does not exist.");
        }
        else {
            double currentCost = findStockUserDataAccess.getCost(tickerSymbol);
            FindStockOutputData outputData = new FindStockOutputData(tickerSymbol, currentCost);
            findStockPresenter.prepareSuccessView(outputData);
        }

    }
}
