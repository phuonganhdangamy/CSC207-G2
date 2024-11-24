package interface_adapter.find_stock;

public class FindStockState {
    private String tickerSymbol = "";
    private boolean success;

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getTickerSymbol() {return tickerSymbol;}

    public boolean getSuccess() {
        return success;
    }
    public void setTickerSymbol(String tickerSymbol) {this.tickerSymbol = tickerSymbol;}
}
