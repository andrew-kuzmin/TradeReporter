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
        REVERSE_MARKET_NAMES.put("Natural Gas", "CME/NG");
        REVERSE_MARKET_NAMES.put("Live Cattle", "CME/LC");
        REVERSE_MARKET_NAMES.put("Feeder Cattle", "CME/FC");
        REVERSE_MARKET_NAMES.put("Copper", "CME/HG");
        REVERSE_MARKET_NAMES.put("Heating Oil", "CME/HO");
        REVERSE_MARKET_NAMES.put("KCBT HRW Wheat", "CME/KW");
        REVERSE_MARKET_NAMES.put("Coffee C", "CME/KC");
        REVERSE_MARKET_NAMES.put("Coffee C (ICE)", "CME/KC");
        REVERSE_MARKET_NAMES.put("Soybean Meal", "CME/SM");
        REVERSE_MARKET_NAMES.put("Eurodollar", "CME/ED");
        REVERSE_MARKET_NAMES.put("Wheat", "CME/W");
        REVERSE_MARKET_NAMES.put("Soybean", "CME/S");
        REVERSE_MARKET_NAMES.put("Cocoa", "ICE/CC");
        REVERSE_MARKET_NAMES.put("Cocoa (ICE)", "ICE/CC");
        REVERSE_MARKET_NAMES.put("Sugar No 11", "ICE/SB");
        REVERSE_MARKET_NAMES.put("Sugar No 11 (ICE)", "ICE/S");
        REVERSE_MARKET_NAMES.put("Brent", "ICE/B");
        REVERSE_MARKET_NAMES.put("Brent (IPE)", "ICE/B");
        REVERSE_MARKET_NAMES.put("FCOJ A", "ICE/OJ");
        REVERSE_MARKET_NAMES.put("FCOJ A (ICE)", "ICE/OJ");
        REVERSE_MARKET_NAMES.put("RBOB Gasoline", "CME/RB");
        REVERSE_MARKET_NAMES.put("Silver RT", "CME/SI");
        REVERSE_MARKET_NAMES.put("Corn", "CME/C");
        REVERSE_MARKET_NAMES.put("Crude Oil", "CME/CL");
        REVERSE_MARKET_NAMES.put("Cotton No 2", "ICE/CT");
        REVERSE_MARKET_NAMES.put("Cotton No 2 (ICE)", "ICE/CT");
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
        MARKET_NAMES.put("CME/LN", "Lean Hogs");
        MARKET_NAMES.put("ZL", "Soybean Oil");
        MARKET_NAMES.put("CME/NG", "Natural Gas");
        MARKET_NAMES.put("CME/LC", "Live Cattle");
        MARKET_NAMES.put("CME/FC", "Feeder Cattle");
        MARKET_NAMES.put("CME/HG", "Copper");
        MARKET_NAMES.put("CME/HO", "Heating Oil");
        MARKET_NAMES.put("CME/KW", "KCBT HRW Wheat");
        MARKET_NAMES.put("CME/KC", "Coffee C");
        MARKET_NAMES.put("CME/SM", "Soybean Meal");
        MARKET_NAMES.put("CME/ED", "Eurodollar");
        MARKET_NAMES.put("CME/W", "Wheat");
        MARKET_NAMES.put("CME/S", "Soybean");
        MARKET_NAMES.put("ICE/CC", "Cocoa");
        MARKET_NAMES.put("ICE/SB", "Sugar No 11");
        MARKET_NAMES.put("ICE/B", "Brent");
        MARKET_NAMES.put("ICE/OJ", "FCOJ A");
        MARKET_NAMES.put("CME/RB", "RBOB Gasoline");
        // ----------unchceked----------
        MARKET_NAMES.put("CME/SI", "Silver RT");
        MARKET_NAMES.put("CME/C", "Corn");
        MARKET_NAMES.put("CME/CL", "Crude Oil");
        MARKET_NAMES.put("ICE/CT", "Cotton No 2");
    }

    static {
        MARKET_FACTORS.put("CME/LN", 0.001d);
        MARKET_FACTORS.put("ZL", 0.01d);
        MARKET_FACTORS.put("CME/NG", 0.001d);
        MARKET_FACTORS.put("CME/LC", 0.001d);
        MARKET_FACTORS.put("CME/FC", 0.001d);
        MARKET_FACTORS.put("CME/HG", 0.0001d);
        MARKET_FACTORS.put("CME/HO", 0.0001d);
        MARKET_FACTORS.put("CME/KW", 0.1d);
        MARKET_FACTORS.put("CME/KC", 1d);
        MARKET_FACTORS.put("CME/SM", 0.1d);
        MARKET_FACTORS.put("CME/ED", 0.01d);
        MARKET_FACTORS.put("CME/W", 0.1d);
        MARKET_FACTORS.put("CME/S", 0.1d);
        MARKET_FACTORS.put("ICE/CC", 1d);
        MARKET_FACTORS.put("ICE/SB", 1d);
        MARKET_FACTORS.put("ICE/B", 1d);
        MARKET_FACTORS.put("ICE/OJ", 1d);
        MARKET_FACTORS.put("CME/RB", 0.0001d);
        MARKET_FACTORS.put("CME/SI", 0.001d);
        MARKET_FACTORS.put("CME/C", 0.1d);
        MARKET_FACTORS.put("CME/CL", 0.01d);
        MARKET_FACTORS.put("ICE/CT", 1d);
    }

    static {
        MARKET_PIPS.put("ZL", 600.0d);
        MARKET_PIPS.put("CME/ED", 2500.0d);
        MARKET_PIPS.put("CME/FC", 500.0d);
        MARKET_PIPS.put("CME/HG", 25000.0d);
        MARKET_PIPS.put("CME/HO", 42000.0d);
        MARKET_PIPS.put("CME/KC", 375.0d);
        MARKET_PIPS.put("CME/KW", 50.0d);
        MARKET_PIPS.put("CME/LC", 400.0d);
        MARKET_PIPS.put("CME/LN", 400.0d);
        MARKET_PIPS.put("CME/NG", 10000.0d);
        MARKET_PIPS.put("CME/S", 50.0d);
        MARKET_PIPS.put("CME/SM", 100.0d);
        MARKET_PIPS.put("CME/W", 50.0d);
        MARKET_PIPS.put("ICE/B", 1000.0d);
        MARKET_PIPS.put("ICE/CC", 10.0d);
        MARKET_PIPS.put("ICE/OJ", 150.0d);
        MARKET_PIPS.put("ICE/SB", 1120.0d);
        // not found in current planned
        MARKET_PIPS.put("CME/C", 50.0d);
        MARKET_PIPS.put("CME/CL", 1000.0d);
        MARKET_PIPS.put("CME/RB", 42000.0d);
        MARKET_PIPS.put("CME/SI", 5000.0d);
        MARKET_PIPS.put("ICE/CT", 500.0d);
    }

}
