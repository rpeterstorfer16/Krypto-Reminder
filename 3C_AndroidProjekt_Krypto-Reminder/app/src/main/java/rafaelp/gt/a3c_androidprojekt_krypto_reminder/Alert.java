package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import android.app.Service;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Alert implements Serializable {
    private String coinName;
    private String currency;
    private String lowerHigher;
    private double currentPrice;
    private double priceAlert;
    private double priceChanged;
    private double marketCap;
    private String cryptoIcon;
    private Status status;


    public Alert(String coinName,String currency,String lowerHigher, double currentPrice, double priceAlert, double priceChanged, double marketCap, String cryptoIcon, Status status) {
        this.coinName = coinName;
        this.currency = currency;
        this.lowerHigher = lowerHigher;
        this.currentPrice = currentPrice;
        this.priceAlert = priceAlert;
        this.priceChanged = priceChanged;
        this.marketCap = marketCap;
        this.cryptoIcon = cryptoIcon;
        this.status = status;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLowerHigher() {
        return lowerHigher;
    }

    public void setLowerHigher(String lowerHigher) {
        this.lowerHigher = lowerHigher;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
