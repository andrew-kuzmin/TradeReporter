package dto;

import org.joda.time.LocalDate;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class UnplannedDeal extends Deal {

    @Override
	public String toString() {
		return "UnplannedDeal [id=" + id + ", instrument=" + instrument + ", direction=" + direction + ", start="
				+ start + ", end=" + end + ", accountName=" + accountName + ", pips=" + pips + ", factor=" + factor
				+ ", currentPrice=" + currentPrice + ", startPrice=" + startPrice + ", endPrice=" + endPrice
				+ ", \norders=" + orders + ", \npriceChart=" + priceChart + ", startEndPriceChart=" + startEndPriceChart
				+ ", urlPriceChart=" + urlPriceChart + "]";
	}

	private long id;
	private String market;
	private String month1;
	private String month2;
	private Integer year1;
	private Integer year2;
	private String instrument;
	private Boolean direction;
	

	private Long start;
	private Long end;
	private String accountName;
	private Double pips;
	private Double factor;
	private Double currentPrice;
	private Double startPrice;
	private Double endPrice;
	// manual = true, auto false
	private Boolean startPriceMode;
	private Boolean endPriceMode;
	private Integer benchmarkLots;
	private Set<Order> orders = new HashSet<>();
	private Boolean archived;


	private Integer year;
	private TreeMap<LocalDate, Double> priceChart = new TreeMap<>();
	private TreeMap<LocalDate, String> startEndPriceChart = new TreeMap<>();
	private TreeMap<LocalDate, String> urlPriceChart = new TreeMap<>();

//    public UnplannedDeal(long id, String market, String month1, String month2,
//                         Integer year1, Integer year2, String instrument, Boolean direction, Long start, Long end,
//                         Set<Order> orders, String accountName) {
//        this.id = id;
//        this.market = market;
//        this.month1 = month1;
//        this.month2 = month2;
//        this.year1 = year1;
//        this.year2 = year2;
//        this.instrument = instrument;
//        this.direction = direction;
//        this.start = start;
//        this.end = end;
//        this.orders = orders;
//        this.accountName = accountName;
//    }

	public UnplannedDeal(long id, String market,
						 String month1, String month2,
						 Integer year1, Integer year2,
						 String instrument, Boolean direction,
						 Long start, Long end, String accountName,
						 Double pips, Double factor, Double currentPrice,
						 Double startPrice, Double endPrice, Boolean startPriceMode,
						 Boolean endPriceMode, Integer benchmarkLots, Set<Order> orders,
						 Boolean archived, Integer year, TreeMap<LocalDate, Double> priceChart,
						 TreeMap<LocalDate, String> startEndPriceChart, TreeMap<LocalDate, String> urlPriceChart) {
		this.id = id;
		this.market = market;
		this.month1 = month1;
		this.month2 = month2;
		this.year1 = year1;
		this.year2 = year2;
		this.instrument = instrument;
		this.direction = direction;
		this.start = start;
		this.end = end;
		this.accountName = accountName;
		this.pips = pips;
		this.factor = factor;
		this.currentPrice = currentPrice;
		this.startPrice = startPrice;
		this.endPrice = endPrice;
		this.startPriceMode = startPriceMode;
		this.endPriceMode = endPriceMode;
		this.benchmarkLots = benchmarkLots;
		this.orders = orders;
		this.archived = archived;
		this.year = year;
		this.priceChart = priceChart;
		this.startEndPriceChart = startEndPriceChart;
		this.urlPriceChart = urlPriceChart;
	}

	public UnplannedDeal() {
    }


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getMonth1() {
		return month1;
	}

	public void setMonth1(String month1) {
		this.month1 = month1;
	}

	public String getMonth2() {
		return month2;
	}

	public void setMonth2(String month2) {
		this.month2 = month2;
	}

	public Integer getYear1() {
		return year1;
	}

	public void setYear1(Integer year1) {
		this.year1 = year1;
	}

	public Integer getYear2() {
		return year2;
	}

	public void setYear2(Integer year2) {
		this.year2 = year2;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public Boolean getDirection() {
		return direction;
	}

	public void setDirection(Boolean direction) {
		this.direction = direction;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Double getPips() {
		return pips;
	}

	public void setPips(Double pips) {
		this.pips = pips;
	}

	public Double getFactor() {
		return factor;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public Double getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}

	public Boolean getStartPriceMode() {
		return startPriceMode;
	}

	public void setStartPriceMode(Boolean startPriceMode) {
		this.startPriceMode = startPriceMode;
	}

	public Boolean getEndPriceMode() {
		return endPriceMode;
	}

	public void setEndPriceMode(Boolean endPriceMode) {
		this.endPriceMode = endPriceMode;
	}

	public Integer getBenchmarkLots() {
		return benchmarkLots;
	}

	public void setBenchmarkLots(Integer benchmarkLots) {
		this.benchmarkLots = benchmarkLots;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	public TreeMap<LocalDate, Double> getPriceChart() {
		return priceChart;
	}
	public Boolean getArchived() { return archived;	}

	public void setArchived(Boolean archived) {	this.archived = archived;	}

	public void setPriceChart(TreeMap<LocalDate, Double> priceChart) {
		this.priceChart = priceChart;
	}

	public TreeMap<LocalDate, String> getStartEndPriceChart() {
		return startEndPriceChart;
	}

	public void setStartEndPriceChart(TreeMap<LocalDate, String> startEndPriceChart) {
		this.startEndPriceChart = startEndPriceChart;
	}

	public TreeMap<LocalDate, String> getUrlPriceChart() {
		return urlPriceChart;
	}

	public void setUrlPriceChart(TreeMap<LocalDate, String> urlPriceChart) {
		this.urlPriceChart = urlPriceChart;
	}

//	public PlannedDeal toPlannedDeal(){
//		return new PlannedDeal(id, market, month1, month2, year1, year2, instrument, direction, start, end, orders, accountName);
//	}

	public PlannedDeal toPlannedDeal(){
		return new PlannedDeal(id, market, month1, month2, year1, year2, instrument, direction, start, end, pips, factor, currentPrice, startPrice, endPrice
		, startPriceMode, endPriceMode, benchmarkLots, accountName, orders, archived, "BIG", year, priceChart, startEndPriceChart, urlPriceChart);
	}


}
