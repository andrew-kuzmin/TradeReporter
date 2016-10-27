package dto;

import java.util.Date;

public class Order {

	private long id;
	private long submitTime;
	private String instrument;
	private boolean buy;
	private int lots;
	private double price;
	private String account;
	private boolean deleted;
	private Long plannedDealId;
	private Long unplannedDealId;
	private Integer messageNumber;
	private String message;
	private double commission;
	private String twsId;

	public double getCommission() {	return commission;	}

	public void setCommission(double commission) {	this.commission = commission;	}

	public String getTwsId() {	return twsId;	}

	public void setTwsId(String twsId) { this.twsId = twsId;	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getPlannedDealId() {
		return plannedDealId;
	}

	public void setPlannedDealId(Long plannedDealId) {
		this.plannedDealId = plannedDealId;
	}

	public Long getUnplannedDealId() {
		return unplannedDealId;
	}

	public void setUnplannedDealId(Long unplannedDealId) {
		this.unplannedDealId = unplannedDealId;
	}

	public Integer getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(Integer messageNumber) {
		this.messageNumber = messageNumber;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		Date date = new Date(submitTime);
		return "main.java.dto.Order [id=" + id + ", submitTime=" + date.toString() + ", instrument=" + instrument + ", buy=" + buy
				+ ", lots=" + lots + ", price=" + price + ", account=" + account + ", deleted=" + deleted
				+ ", plannedDealId=" + plannedDealId + ", unplannedDealId=" + unplannedDealId + ", messageNumber="
				+ messageNumber + "]";
	}

}
