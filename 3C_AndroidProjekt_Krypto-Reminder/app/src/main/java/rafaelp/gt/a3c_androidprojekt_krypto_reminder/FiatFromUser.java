package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

public class FiatFromUser {
    private String name;
    private String rate;
    private String symbol;

    public FiatFromUser(String name, String rate, String symbol) {
        this.name = name;
        this.rate = rate;
        this.symbol = symbol;
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
