package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Alert implements Serializable {
    private String coinName;
    private double currentPrice;
    private double priceAlert;
    private double priceChanged;
    private double marketCap;
    private String cryptoIcon;

    public Alert(String coinName, double currentPrice, double priceAlert, double priceChanged, double marketCap, String cryptoIcon) {
        this.coinName = coinName;
        this.currentPrice = currentPrice;
        this.priceAlert = priceAlert;
        this.priceChanged = priceChanged;
        this.marketCap = marketCap;
        this.cryptoIcon = cryptoIcon;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPriceAlert() {
        return priceAlert;
    }

    public void setPriceAlert(double priceAlert) {
        this.priceAlert = priceAlert;
    }

    public double getPriceChanged() {
        return priceChanged;
    }

    public void setPriceChanged(double priceChanged) {
        this.priceChanged = priceChanged;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public String getCryptoIcon() {
        return cryptoIcon;
    }

    public void setCryptoIcon(String cryptoIcon) {
        this.cryptoIcon = cryptoIcon;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "coinName='" + coinName + '\'' +
                ", currentPrice=" + currentPrice +
                ", priceAlert=" + priceAlert +
                ", priceChanged=" + priceChanged +
                ", marketCap=" + marketCap +
                ", cryptoIcon='" + cryptoIcon + '\'' +
                '}';
    }
}