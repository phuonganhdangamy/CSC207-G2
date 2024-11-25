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
    private String findStockState; // Tracks whether the operation succeeded

    public FindStockViewModel() {
        super("find stock");
        setState(new FindStockState("", "", ""));
    }

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
        getState().setSuccess(success);
        support.firePropertyChange("success", this.success, success);
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        getState().setError(error);
        support.firePropertyChange("error", this.error, error);
        this.error = error;
    }

    public String getFindStockState() {
        return findStockState;
    }

    public void setFindStockState(String findStockState) {
        getState().setSuccess(findStockState); // Assuming `success` holds this info
        support.firePropertyChange("findStockState", this.findStockState, findStockState);
        this.findStockState = findStockState;
    }
}
