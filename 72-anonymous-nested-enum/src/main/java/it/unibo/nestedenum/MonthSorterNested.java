package it.unibo.nestedenum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    enum Month {

        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days; 

        Month(int days) {
            this.days = days;
        }

        public static Month fromString(String nameMonth) {

            List<Month> equalMonth = new ArrayList<>();

            for(Month m : Month.values()) {

                if(m.name().startsWith(nameMonth.toUpperCase(Locale.ROOT))) {
                    equalMonth.add(m);
                }
            }

            if(equalMonth.size() != 1) {
                throw new IllegalArgumentException();
            }

            return equalMonth.get(0);
        }
    }


    private static class SortByDate implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            
            Month m1 = Month.fromString(o1);
            Month m2 = Month.fromString(o2);

            return Integer.compare(m1.days, m2.days);
        }
    }


    private static class SortByOrder implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            
            Month m1 = Month.fromString(o1);
            Month m2 = Month.fromString(o2);

            return Integer.compare(m1.ordinal(), m2.ordinal());
        }

    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByOrder();
    }
}
