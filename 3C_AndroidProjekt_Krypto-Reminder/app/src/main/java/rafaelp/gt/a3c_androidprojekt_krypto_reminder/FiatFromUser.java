package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

public class FiatFromUser {
    private String symbol;
    private String rate;
    private String name;


    public FiatFromUser(String symbol, String rate, String name) {
        this.symbol = symbol;
        this.rate = rate;
        this.name = name;

    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FiatFromUser{" +
                "symbol='" + symbol + '\'' +
                ", rate='" + rate + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
