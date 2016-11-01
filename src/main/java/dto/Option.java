package dto;

import java.util.Date;

public class Option {
    private long id;
    private long submitTime;
    private String instrument;
    private boolean buy;
    private int lots;
    private double price;
    private String account;
    private double commission;
    private String twsId;
    private Date expDate;
    private String type;
    private double strike;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Date getExpDate() {
        return expDate;

    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public double getStrike() {
        return strike;
    }

    public void setStrike(double strike) {
        this.strike = strike;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public int getLots() {
        return lots;
    }

    public void setLots(int lots) {
        this.lots = lots;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getTwsId() {
        return twsId;
    }

    public void setTwsId(String twsId) {
        this.twsId = twsId;
    }
}
