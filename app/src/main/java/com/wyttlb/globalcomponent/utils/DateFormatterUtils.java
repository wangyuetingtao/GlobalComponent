package com.wyttlb.globalcomponent.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatterUtils {

    SimpleDateFormat mFormatter;

    private DateFormatterUtils() {
        mFormatter = new SimpleDateFormat("dd MMM");
    }

    private static final class Singleton {
        private static DateFormatterUtils INSTANCE = new DateFormatterUtils();
    }

    public static DateFormatterUtils getInstance() {
        return Singleton.INSTANCE;
    }

    public String formatDate(Date date) {
       return mFormatter.format(date);
    }
}
