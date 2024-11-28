package interface_adapter.find_stock;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;

/**
 * The View Model for the Find Stock View.
 */
public class FindStockViewModel extends ViewModel<FindStockState> {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String tickerSymbol;
    private String message;
    private String error;
    // Tracks whether the operation succeeded
    private String findStockState;

    public FindStockViewModel() {
        super("find stock");
        setState(new FindStockState("", "", ""));
    }

    /**
     * Adds a PropertyChangeListener to the view model.
     *
     * @param listener the PropertyChangeListener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener from the view model.
     *
     * @param listener the PropertyChangeListener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Gets the ticker symbol.
     *
     * @return the current ticker symbol
     */
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * Sets the ticker symbol and notifies listeners of the change.
     *
     * @param tickerSymbol the new ticker symbol
     */
    public void setTickerSymbol(String tickerSymbol) {
        support.firePropertyChange("tickerSymbol", this.tickerSymbol, tickerSymbol);
        this.tickerSymbol = tickerSymbol;
    }

    /**
     * Gets the success message.
     *
     * @return the success message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the success message and updates the state.
     *
     * @param message the success message to set
     */
    public void setMessage(String message) {
        getState().setMessage(message);
        support.firePropertyChange("success", this.message, message);
        this.message = message;
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error message and updates the state.
     *
     * @param error the error message to set
     */
    public void setError(String error) {
        getState().setError(error);
        support.firePropertyChange("error", this.error, error);
        this.error = error;
    }

    /**
     * Gets the state of the find stock operation.
     *
     * @return the current find stock state
     */
    public String getFindStockState() {
        return findStockState;
    }

    /**
     * Sets the state of the find stock operation and updates the view model.
     *
     * @param findStockState the new state of the find stock operation
     */
    public void setFindStockState(String findStockState) {
        // Assuming `success` holds this info
        getState().setSuccess(findStockState);
        support.firePropertyChange("findStockState", this.findStockState, findStockState);
        this.findStockState = findStockState;
    }
}
