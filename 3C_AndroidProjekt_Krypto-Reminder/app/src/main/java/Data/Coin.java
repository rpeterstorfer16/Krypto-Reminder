package Data;

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

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPriceChangedIn24() {
        return priceChangedIn24;
    }

    public void setPriceChangedIn24(double priceChangedIn24) {
        this.priceChangedIn24 = priceChangedIn24;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    @Override
    public String toString() {
        return coinName + ": " + iconLink + " " + currentPrice + " " + priceChangedIn24 + " " + marketCap;
    }
}
