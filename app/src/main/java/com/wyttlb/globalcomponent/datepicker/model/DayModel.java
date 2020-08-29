package com.wyttlb.globalcomponent.datepicker.model;

import com.wyttlb.globalcomponent.utils.DateFormatterUtils;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期实体
 * @author wyttlb
 */
public class DayModel {
    private int mDay;
    private int mYear;
    private int mMonth;

    public DayModel(int year, int month, int day) {
        mDay = day;
        mYear = year;
        mMonth = month;
    }

    public int getDay() {
        return mDay;
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayModel)) return false;

        DayModel model = (DayModel) o;
        return mYear == model.getYear() && mMonth == model.getMonth() && mDay == model.getDay();
    }

    @Override
    public String toString() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);

        ///
        Calendar todayCal = Calendar.getInstance();
        todayCal.setTimeInMillis(System.currentTimeMillis());

        int year = todayCal.get(Calendar.YEAR);
        int month = todayCal.get(Calendar.MONTH);
        int day = todayCal.get(Calendar.DAY_OF_MONTH);

        if (year == mYear && month == mMonth && day == mDay) {
            return "Today";
        }
        return DateFormatterUtils.getInstance().formatDate(new Date(calendar.getTimeInMillis()));
    }
}
