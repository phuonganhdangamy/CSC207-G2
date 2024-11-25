package interface_adapter.find_stock;

public class FindStockState {
    private String message;
    private String tickerSymbol = "";
    private String success = "";
    private String error = "";

    public FindStockState(String message, String tickerSymbol, String success) {
        this.message = message;
        this.tickerSymbol = tickerSymbol;
        this.success = success;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public String getTickerSymbol() {return tickerSymbol;}

    public String getSuccess() {
        return success;
    }

    public void setTickerSymbol(String tickerSymbol) {this.tickerSymbol = tickerSymbol;}

    public void setSuccess(String success) {this.success = success;}

    public String getError() {return error;}

    public void setError(String error) {this.error = error;}
}
