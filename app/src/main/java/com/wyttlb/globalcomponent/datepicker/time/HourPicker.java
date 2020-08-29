package com.wyttlb.globalcomponent.datepicker.time;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.wyttlb.globalcomponent.datepicker.WheelPicker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 小时选择器
 * @author wyttlb
 */
public class HourPicker extends WheelPicker<Integer> {
    private static final int sBeginHourInDay = 0;
    private static final int sEndHourInDay = 23;

    private OnHourSelectedListener mOnHourSelectedListener;
    private int mBeginHourInDay = sBeginHourInDay;
    private int mEndHourInDay = sEndHourInDay;
    private int mSelectedHour;

    public HourPicker(Context context) {
        this(context, null);
    }

    public HourPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HourPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setOnWheelChangeListener(new OnWheelChangeListener<Integer>() {
            @Override
            public void onWheelSelected(Integer item, int position) {
                mSelectedHour = item;
                if (mOnHourSelectedListener != null) {
                    mOnHourSelectedListener.onHourSelected(item);
                }
            }
        });
    }

    private void init() {
        setItemMaximumWidthText("00");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        setDataFormat(numberFormat);
        ///
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mSelectedHour = calendar.get(Calendar.HOUR_OF_DAY);

        updateHour();
    }

    public void updateHour() {
        List<Integer> list = new ArrayList<>();
        if (mBeginHourInDay == mEndHourInDay) {
            list.add(mBeginHourInDay);
        } else {
            for (int i = mBeginHourInDay; i <= mEndHourInDay; i++) {
                list.add(i);
            }
        }

        setDataList(list);
    }

    public int getSelectedHour() {
        return mSelectedHour;
    }

    public void setBeginHourInDay(int beginHourInDay) {
        this.mBeginHourInDay = beginHourInDay;
    }

    public void setEndHourInDay(int endHourInDay) {
        this.mEndHourInDay = endHourInDay;
    }

    public void setSelectedHour(int selectedHour) {
        setSelectedHour(selectedHour, false);
    }

    public void setSelectedHour(int hour, boolean smoothScroll) {
        if (getDataList().indexOf(hour) < 0) {
            mSelectedHour = getDataList().get(0);
        } else {
            mSelectedHour = hour;
        }
        setCurrentItem(hour, smoothScroll);
    }

    public void setOnHourSelectedListener(OnHourSelectedListener onHourSelectedListener) {
        mOnHourSelectedListener = onHourSelectedListener;
    }

    public interface OnHourSelectedListener {
        void onHourSelected(int hour);
    }
}
