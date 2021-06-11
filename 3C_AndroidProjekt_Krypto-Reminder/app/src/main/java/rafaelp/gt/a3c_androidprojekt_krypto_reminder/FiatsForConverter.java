package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

public class FiatsForConverter {
    private String name;
    private double rate;
    private String symbol;

    public FiatsForConverter(String name, double rate, String symbol) {
        this.name = name;
        this.rate = rate;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
