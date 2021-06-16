package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private double amountOfCoins;
    private String coinName;
    private String iconLink;
    private Date date;

    public Transaction(double amountOfCoins, String coinName, String iconLink, Date date) {
        this.amountOfCoins = amountOfCoins;
        this.coinName = coinName;
        this.iconLink = iconLink;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amountOfCoins=" + amountOfCoins +
                ", coinName='" + coinName + '\'' +
                ", iconLink='" + iconLink + '\'' +
                ", date=" + date +
                '}';
    }
}


