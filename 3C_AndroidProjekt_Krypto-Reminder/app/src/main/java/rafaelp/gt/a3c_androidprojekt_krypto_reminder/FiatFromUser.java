package rafaelp.gt.a3c_androidprojekt_krypto_reminder;

public class FiatFromUser {
    private String symbol;
    private String code;
    private String name;

    public FiatFromUser(String symbol, String code, String name) {
        this.symbol = symbol;
        this.code = code;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
