package dao;

import dto.Order;
import dto.PlannedDeal;
import dto.UnplannedDeal;
import org.joda.time.LocalDate;

import java.util.List;

public interface FillsDao {

    // orders
    List<Order> getAllOrders();

    List<Order> getDeletedOrders();

    void updateOrder(Order order);

    Order getOrderById(long id);

    void deleteOrder(long id);

    List<String> listAccounts();

    Integer getMaxMessageNumber();

    // planned deals
    void updatePlannedDeal(PlannedDeal plannedDeal);

    void deletePlannedDeal(long id);

    PlannedDeal getPlannedDeal(long id);

    List<PlannedDeal> getAllPlannedDeals();

    List<PlannedDeal> getUnarchivedPlannedDeals();

    List<PlannedDeal> getArchivedPlannedDeals();

    List<UnplannedDeal> getUnarchivedUnplannedDeals();

    List<UnplannedDeal> getArchivedUnplannedDeals();

    List<PlannedDeal> getRecentPlannedDeals(long timestampInTwoMonths);

    List<PlannedDeal> getPlannedDealsByInstrAcc(String instrument, String account);

    List<PlannedDeal> getPlannedDealsByInstr(String instrument);

    @SuppressWarnings("unchecked")
    List<UnplannedDeal> getRecentUnplannedDeals(long timestampInTwoMonths);

    List<PlannedDeal> getPlannedDealsInRange(long start, long end);

    @SuppressWarnings("unchecked")
    List<UnplannedDeal> getUnplannedDealsInRange(long start, long end);

    void deletePlans(int year, String plan);

    // unplanned deals
    void updateUnplannedDeal(UnplannedDeal unplannedDeal);

    void deleteUnplannedDeal(long id);

    UnplannedDeal getUnplannedDeal(long id);

    List<UnplannedDeal> getAllUnplannedDeals();

    List<UnplannedDeal> getUnplannedDealByInstrAcc(String instrument, String account);

    LocalDate getStartDateForChart();

    LocalDate getStartDateForUnplannedChart();

    List<PlannedDeal> getPlannedDealsByInstrAccLikeLeft(String instrument, String leftPart, String account,
                                                        long date,	boolean direction);

    @SuppressWarnings("unchecked")
    List<UnplannedDeal> getUnplannedDealsByInstrAccLikeLeft(String instrument, String leftPart, String account,
                                                            long date, boolean direction);

    List<PlannedDeal> getPlannedDealsByInstrAccLikeRight(String instrument, String rightPart, String account, long date);

    List<PlannedDeal> getPlannedDealsByInstrAccTime(String instrument, String account, long date, boolean direction);

    List<UnplannedDeal> getUnplannedDealsByInstrAccTime(String instrument, String account, long date, boolean direction);

    List<UnplannedDeal> getUnplannedDealsByInstr(String instrument);

    List<PlannedDeal> getPairsOfPlannedDeals(long timestampInTwoMonths);


}

