package interface_adapter.find_stock;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The View Model for the Find Stock View.
 */
public class FindStockViewModel extends ViewModel<FindStockState> {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String tickerSymbol;
    private String success;
    private String error;
    private String findStockSuccess; // Tracks whether the operation succeeded

    public FindStockViewModel() {
        super("find stock");
        setState(new FindStockState("", "", ""));
    }

//    public String getFindStockSuccess() {
//        return getState().getSuccess();
//    }
//
//    /**
//     * Updates the success status of the find stock operation.
//     *
//     * @param success true if the operation was successful, false otherwise.
//     */
//    public void setFindStockSuccess(String success) {
//        getState().setSuccess(success);
//    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        support.firePropertyChange("tickerSymbol", this.tickerSymbol, tickerSymbol);
        this.tickerSymbol = tickerSymbol;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        support.firePropertyChange("success", this.success, success);
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        support.firePropertyChange("error", this.error, error);
        this.error = error;
    }

    public String getFindStockSuccess() {
        return findStockSuccess;
    }

    public void setFindStockSuccess(String findStockSuccess) {
        support.firePropertyChange("findStockSuccess", this.findStockSuccess, findStockSuccess);
        this.findStockSuccess = findStockSuccess;
    }
}
