package com.wyttlb.globalcomponent.datepicker.time;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.wyttlb.globalcomponent.datepicker.WheelPicker;
import com.wyttlb.globalcomponent.datepicker.model.DayModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 日期某天选择器
 * @author wyttlb
 */
public class DaysPicker extends WheelPicker<DayModel> {
    private long mMinDay, mMaxDay;

    private  DayModel mSelectedDay;

    private  OnDaySelectedListener mOnDaySelectedListener;

    public DaysPicker(Context context) {
        this(context, null);
    }

    public DaysPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DaysPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setOnWheelChangeListener(new OnWheelChangeListener<DayModel>() {
            @Override
            public void onWheelSelected(DayModel item, int position) {
                mSelectedDay = item;
                if (mOnDaySelectedListener != null) {
                    mOnDaySelectedListener.onDaySelected(item);
                }
            }
        });
    }

    private void init() {
        mMaxDay = mMinDay = System.currentTimeMillis();
        ///
        Calendar initialCal = Calendar.getInstance();
        initialCal.setTimeInMillis(mMinDay);
        //更新初始数据
        updateDay();
        //设置默认选中
        mSelectedDay = new DayModel(initialCal.get(Calendar.YEAR), initialCal.get(Calendar.MONTH), initialCal.get(Calendar.DAY_OF_MONTH));
        setSelectedDay(mSelectedDay, false);
    }

    public DayModel getSelectedDay() {
        return mSelectedDay;
    }

    public void setSelectedDay(DayModel selectedDay) {
        setSelectedDay(selectedDay, false);
    }

    public void setSelectedDay(DayModel selectedDay, boolean smoothScroll) {
        this.mSelectedDay = selectedDay;
        setCurrentItem(selectedDay, smoothScroll);
    }

    public void setMaxDay(long maxDay) {
        this.mMaxDay = maxDay;
    }

    public void setMinDay(long minDay) {
        this.mMinDay = minDay;
    }

    public void setOnDaySelectedListener(OnDaySelectedListener onDaySelectedListener) {
        this.mOnDaySelectedListener = onDaySelectedListener;
    }

    public void updateDay() {
        //计算最大和最小差距的天数
        long millisOfDay = 24 * 60 * 60 * 1000;
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.setTimeInMillis(mMaxDay);

        Calendar minCalendar = Calendar.getInstance();
        minCalendar.setTimeInMillis(mMinDay);

        //计算天数差距
        long daysDif = (maxCalendar.getTimeInMillis() - minCalendar.getTimeInMillis()) / millisOfDay;

        List<DayModel> list = new ArrayList<>();
        for (int i = 0; i <= daysDif; i++) {
            //计算最大到最小之间的日期
            ///
            Calendar temCalendar = Calendar.getInstance();
            temCalendar.setTimeInMillis(mMinDay);
            temCalendar.add(Calendar.DAY_OF_MONTH, i);
            //java贼啰嗦
            int year = temCalendar.get(Calendar.YEAR);
            int month = temCalendar.get(Calendar.MONTH);
            int day = temCalendar.get(Calendar.DAY_OF_MONTH);
            DayModel dayModel = new DayModel(year, month, day);
            list.add(dayModel);
        }
        setDataList(list);
    }

    public interface OnDaySelectedListener {
        void onDaySelected(DayModel day);
    }
}
