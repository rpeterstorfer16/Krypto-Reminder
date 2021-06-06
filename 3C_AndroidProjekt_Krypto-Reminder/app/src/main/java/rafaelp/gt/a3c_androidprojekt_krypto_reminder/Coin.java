package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

public class Coin {

    private String coinName;
    private String iconLink;
    private double currentPrice;
    private double priceChangedIn24;
    private double marketCap;

    public Coin(String coinName, String iconLink, double currentPrice, double priceChangedIn24, double marketCap) {
        this.coinName = coinName;
        this.iconLink = iconLink;
        this.currentPrice = currentPrice;
        this.priceChangedIn24 = priceChangedIn24;
        this.marketCap = marketCap;
    }

    @Override
    public String toString() {
        return coinName + ": " + iconLink + " " + currentPrice + " " + priceChangedIn24 + " " + marketCap;
    }
}
