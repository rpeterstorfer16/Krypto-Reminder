package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private String typeOfTransaction;
    private double amountOfCoins;
    private String coinName;
    private String iconLink;
    private String date;

    public Transaction(String typeOfTransaction, double amountOfCoins, String coinName, String iconLink, String date) {
        this.typeOfTransaction = typeOfTransaction;
        this.amountOfCoins = amountOfCoins;
        this.coinName = coinName;
        this.iconLink = iconLink;
        this.date = date;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public double getAmountOfCoins() {
        return amountOfCoins;
    }

    public void setAmountOfCoins(double amountOfCoins) {
        this.amountOfCoins = amountOfCoins;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


