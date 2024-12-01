package interface_adapter.buy_stock;

import interface_adapter.ViewModel;

/**
 * ViewModel for managing the buy stock state.
 */
public class BuyStockViewModel extends ViewModel<BuyStockState> {

    public BuyStockViewModel() {
        super("buy stock");

        // Initialize with default values
        this.setState(new BuyStockState("", 0, 0.0));
    }
}
