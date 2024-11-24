package interface_adapter.find_stock;

public class FindStockState {
    private String tickerSymbol = "";
    private String success;

    public String getTickerSymbol() {return tickerSymbol;}

    public String getSuccess() {
        return success;
    }

    public void setTickerSymbol(String tickerSymbol) {this.tickerSymbol = tickerSymbol;}

    public void setSuccess(String success) {this.success = success;}
}
