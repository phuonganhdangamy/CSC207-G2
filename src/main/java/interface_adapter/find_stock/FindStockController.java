package interface_adapter.find_stock;

import use_case.find_stock.FindStockInputBoundary;
import use_case.find_stock.FindStockInputData;

/**
 * The Controller for the Find Stock Use Case.
 */
public class FindStockController {
    private final FindStockInputBoundary findStockUseCaseInteractor;

    public FindStockController(FindStockInputBoundary findStockUseCaseInteractor) {
        this.findStockUseCaseInteractor = findStockUseCaseInteractor;
    }

    /**
     * Executes the Find Stock Use Case.
     * @param tickerSymbol the username of the user finding stock
     */
    public void execute(String tickerSymbol) {
        final FindStockInputData findStockInputData = new FindStockInputData(tickerSymbol);

        findStockUseCaseInteractor.execute(findStockInputData);
    }
}
