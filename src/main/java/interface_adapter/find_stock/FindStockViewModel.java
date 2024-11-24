package interface_adapter.find_stock;

import interface_adapter.ViewModel;

/**
 * The View Model for the Find Stock View.
 */
public class FindStockViewModel extends ViewModel<FindStockState> {
    public FindStockViewModel() {
        super("find stock");
        setState(new FindStockState());
    }

    /**
     * Retrieves whether the find stock operation was successful.
     *
     * @return true if successful, false otherwise.
     */
    public String getFindStockSuccess() {
        return getState().getSuccess();
    }

    /**
     * Updates the success status of the find stock operation.
     *
     * @param success true if the operation was successful, false otherwise.
     */
    public void setFindStockSuccess(String success) {
        getState().setSuccess(success);
    }
}
