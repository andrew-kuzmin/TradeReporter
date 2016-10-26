package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealOps {

    // rewrite maps as unmodifiable and final
    public static final Map<String, Character> REVERSE_MONTH_NAMES = new HashMap<>();
    public static final Map<String, String> REVERSE_MARKET_NAMES = new HashMap<>();
    public static final Map<Character, String> MONTH_NAMES = new HashMap<>();
    public static final Map<String, String> MARKET_NAMES = new HashMap<>();
    public static final Map<String, Double> MARKET_FACTORS = new HashMap<>();
    public static final Map<String, Double> MARKET_PIPS = new HashMap<>();
    public static final List<String> MONTHS_LIST = new ArrayList();

    static {
        REVERSE_MONTH_NAMES.put("Jan", 'F');
        REVERSE_MONTH_NAMES.put("Feb", 'G');
        REVERSE_MONTH_NAMES.put("Mar", 'H');
        REVERSE_MONTH_NAMES.put("Apr", 'J');
        REVERSE_MONTH_NAMES.put("May", 'K');
        REVERSE_MONTH_NAMES.put("Jun", 'M');
        REVERSE_MONTH_NAMES.put("Jul", 'N');
        REVERSE_MONTH_NAMES.put("Aug", 'Q');
        REVERSE_MONTH_NAMES.put("Sep", 'U');
        REVERSE_MONTH_NAMES.put("Oct", 'V');
        REVERSE_MONTH_NAMES.put("Nov", 'X');
        REVERSE_MONTH_NAMES.put("Dec", 'Z');
    }

    static {
        REVERSE_MARKET_NAMES.put("Lean Hogs", "HE");
        REVERSE_MARKET_NAMES.put("Soybean Oil", "ZL");
        REVERSE_MARKET_NAMES.put("Natural Gas", "NG");
        REVERSE_MARKET_NAMES.put("Live Cattle", "LE");
        REVERSE_MARKET_NAMES.put("Feeder Cattle", "GF");
        REVERSE_MARKET_NAMES.put("Copper", "HG");
        REVERSE_MARKET_NAMES.put("Heating Oil", "HO");
        REVERSE_MARKET_NAMES.put("KCBT HRW Wheat", "KE");
        REVERSE_MARKET_NAMES.put("Coffee", "KC");
        REVERSE_MARKET_NAMES.put("Soybean Meal", "ZM");
        REVERSE_MARKET_NAMES.put("Eurodollar", "GE");
        REVERSE_MARKET_NAMES.put("Wheat", "ZW");
        REVERSE_MARKET_NAMES.put("Soybean", "ZS");
        REVERSE_MARKET_NAMES.put("Cocoa", "CC");
        REVERSE_MARKET_NAMES.put("Sugar No 11", "SB");
        REVERSE_MARKET_NAMES.put("Brent", "B");
        REVERSE_MARKET_NAMES.put("FCOJ A", "OJ");
        REVERSE_MARKET_NAMES.put("RBOB Gasoline", "RB");
        REVERSE_MARKET_NAMES.put("Corn", "ZC");
        REVERSE_MARKET_NAMES.put("Crude Oil", "CL");
        REVERSE_MARKET_NAMES.put("Cotton No 2", "CT");
        REVERSE_MARKET_NAMES.put("Gold", "GC");
        REVERSE_MARKET_NAMES.put("Platinum", "PL");
        REVERSE_MARKET_NAMES.put("Silver", "SI");
        REVERSE_MARKET_NAMES.put("Euro FX", "EUR");
        REVERSE_MARKET_NAMES.put("British Pound Dollar", "GBP");
        REVERSE_MARKET_NAMES.put("Eurodollar", "GE");
        REVERSE_MARKET_NAMES.put("U.S. Dollar Index", "DX");
        REVERSE_MARKET_NAMES.put("Mini-sized Dow $5", "YM");
        REVERSE_MARKET_NAMES.put("Russell 2000 mini", "TF");
        REVERSE_MARKET_NAMES.put("Nasdaq 100 mini", "NQ");
        REVERSE_MARKET_NAMES.put("ES MINI", "ES");
        REVERSE_MARKET_NAMES.put("Brent Crude Oil", "COIL");
        REVERSE_MARKET_NAMES.put("Canadian Dollar", "CAD");
        REVERSE_MARKET_NAMES.put("Euro", "M6E");
        REVERSE_MARKET_NAMES.put("British Pound", "M6B");
    }

    static {
        MONTH_NAMES.put('F', "Jan");
        MONTH_NAMES.put('G', "Feb");
        MONTH_NAMES.put('H', "Mar");
        MONTH_NAMES.put('J', "Apr");
        MONTH_NAMES.put('K', "May");
        MONTH_NAMES.put('M', "Jun");
        MONTH_NAMES.put('N', "Jul");
        MONTH_NAMES.put('Q', "Aug");
        MONTH_NAMES.put('U', "Sep");
        MONTH_NAMES.put('V', "Oct");
        MONTH_NAMES.put('X', "Nov");
        MONTH_NAMES.put('Z', "Dec");
    }

    static {
        MONTHS_LIST.add("Jan");
        MONTHS_LIST.add("Feb");
        MONTHS_LIST.add("Mar");
        MONTHS_LIST.add("Apr");
        MONTHS_LIST.add("May");
        MONTHS_LIST.add("Jun");
        MONTHS_LIST.add("Jul");
        MONTHS_LIST.add("Aug");
        MONTHS_LIST.add("Sep");
        MONTHS_LIST.add("Oct");
        MONTHS_LIST.add("Nov");
        MONTHS_LIST.add("Dec");
    }

    static {
        MARKET_NAMES.put("HE", "Lean Hogs");
        MARKET_NAMES.put("ZL", "Soybean Oil");
        MARKET_NAMES.put("NG", "Natural Gas");
        MARKET_NAMES.put("LE", "Live Cattle");
        MARKET_NAMES.put("GF", "Feeder Cattle");
        MARKET_NAMES.put("HG", "Copper");
        MARKET_NAMES.put("HO", "Heating Oil");
        MARKET_NAMES.put("KE", "KCBT HRW Wheat");
        MARKET_NAMES.put("KC", "Coffee");
        MARKET_NAMES.put("ZM", "Soybean Meal");
        MARKET_NAMES.put("GE", "Eurodollar");
        MARKET_NAMES.put("ZW", "Wheat");
        MARKET_NAMES.put("ZS", "Soybean");
        MARKET_NAMES.put("CC", "Cocoa");
        MARKET_NAMES.put("SB", "Sugar No 11");
        MARKET_NAMES.put("B", "Brent");
        MARKET_NAMES.put("OJ", "FCOJ A");
        MARKET_NAMES.put("RB", "RBOB Gasoline");
        MARKET_NAMES.put("ZC", "Corn");
        MARKET_NAMES.put("CL", "Crude Oil");
        MARKET_NAMES.put("CT", "Cotton No 2");
        MARKET_NAMES.put("GC", "Gold");
        MARKET_NAMES.put("PL", "Platinum");
        MARKET_NAMES.put("SI", "Silver");
        MARKET_NAMES.put("EUR", "Euro FX");
        MARKET_NAMES.put("GBP", "British Pound Dollar");
        MARKET_NAMES.put("GE", "Eurodollar");
        MARKET_NAMES.put("DX", "U.S. Dollar Index");
        MARKET_NAMES.put("YM", "Mini-sized Dow $5");
        MARKET_NAMES.put("TF", "Russell 2000 mini");
        MARKET_NAMES.put("NQ", "Nasdaq 100 mini");
        MARKET_NAMES.put("ES", "ES MINI");
        MARKET_NAMES.put("COIL", "Brent Crude Oil");
        MARKET_NAMES.put("CAD", "Canadian Dollar");
        MARKET_NAMES.put("M6E", "Euro");
        MARKET_NAMES.put("M6B", "British Pound");
    }

    static {
        MARKET_FACTORS.put("COIL", 0.01d);
        MARKET_FACTORS.put("HE", 0.025d);
        MARKET_FACTORS.put("ZL", 0.01d);
        MARKET_FACTORS.put("NG", 0.001d);
        MARKET_FACTORS.put("LE", 0.025d);
        MARKET_FACTORS.put("GF", 0.025d);
        MARKET_FACTORS.put("HG", 0.0005d);
        MARKET_FACTORS.put("HO", 0.0001d);
        MARKET_FACTORS.put("KE", 0.25d);
        MARKET_FACTORS.put("KC", 0.0005d);
        MARKET_FACTORS.put("ZM", 0.1d);
        MARKET_FACTORS.put("GE", 0.005d);
        MARKET_FACTORS.put("ZW", 0.25d);
        MARKET_FACTORS.put("ZS", 0.25d);
        MARKET_FACTORS.put("CC", 1d);
        MARKET_FACTORS.put("SB", 0.0001d);
        MARKET_FACTORS.put("B", 1d);
        MARKET_FACTORS.put("OJ", 0.0005d);
        MARKET_FACTORS.put("RB", 0.0001d);
        MARKET_FACTORS.put("SI", 0.005d);
        MARKET_FACTORS.put("ZC", 0.25d);
        MARKET_FACTORS.put("CL", 0.01d);
        MARKET_FACTORS.put("CT", 0.0001d);
        MARKET_FACTORS.put("GC", 0.1d);
        MARKET_FACTORS.put("PL", 0.1d);
        MARKET_FACTORS.put("ES", 0.25d);
        MARKET_FACTORS.put("GBP", 0.0001d);
        MARKET_FACTORS.put("CAD", 0.00001d);
        MARKET_FACTORS.put("EUR", 0.0001d);
        MARKET_FACTORS.put("DX", 0.005d);
        MARKET_FACTORS.put("YM", 1d);
        MARKET_FACTORS.put("TF", 0.1d);
        MARKET_FACTORS.put("NQ", 0.25d);
        MARKET_FACTORS.put("M6E", 0.00005d);
        MARKET_FACTORS.put("M6B", 0.0001d);

    }

    static {
        MARKET_PIPS.put("COIL", 1000.0d);
        MARKET_PIPS.put("ZL", 600.0d);
        MARKET_PIPS.put("GE", 2500.0d);
        MARKET_PIPS.put("GF", 500.0d);
        MARKET_PIPS.put("HG", 25000.0d);
        MARKET_PIPS.put("HO", 42000.0d);
        MARKET_PIPS.put("KC", 37500.0d);
        MARKET_PIPS.put("KE", 50.0d);
        MARKET_PIPS.put("LE", 400.0d);
        MARKET_PIPS.put("HE", 400.0d);
        MARKET_PIPS.put("NG", 10000.0d);
        MARKET_PIPS.put("ZS", 50.0d);
        MARKET_PIPS.put("ZM", 100.0d);
        MARKET_PIPS.put("ZW", 50.0d);
        MARKET_PIPS.put("B", 1000.0d);
        MARKET_PIPS.put("CC", 10.0d);
        MARKET_PIPS.put("OJ", 15000.0d);
        MARKET_PIPS.put("SB", 112000.0d);
        MARKET_PIPS.put("ZC", 50.0d);
        MARKET_PIPS.put("CL", 1000.0d);
        MARKET_PIPS.put("RB", 42000.0d);
        MARKET_PIPS.put("SI", 5000.0d);
        MARKET_PIPS.put("CT", 50000.0d);
        MARKET_PIPS.put("GC", 100.0d);
        MARKET_PIPS.put("PL", 50.0d);
        MARKET_PIPS.put("GBP", 62500.0d);
        MARKET_PIPS.put("CAD", 100000.0d);
        MARKET_PIPS.put("EUR", 125000.0d);
        MARKET_PIPS.put("DX", 1000.0d);
        MARKET_PIPS.put("YM", 5.0d);
        MARKET_PIPS.put("TF", 100.0d);
        MARKET_PIPS.put("NQ", 20.0d);
        MARKET_PIPS.put("ES", 50.0d);
        MARKET_PIPS.put("M6E", 12500.0d);
        MARKET_PIPS.put("M6B", 6250.0d);
    }

}
