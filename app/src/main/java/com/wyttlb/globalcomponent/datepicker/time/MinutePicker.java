package com.wyttlb.globalcomponent.datepicker.time;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.wyttlb.globalcomponent.datepicker.WheelPicker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 分钟选择器
 * @author wyttlb
 */
public class MinutePicker extends WheelPicker<Integer> {

    public static final int sBeginMinuteInHour = 0;
    public static final int sEndMinuteInHour = 59;

    private OnMinuteSelectedListener mOnMinuteSelectedListener;

    //时间间隔
    private int mDelta = 1;

    private int mBeginMinuteInHour = sBeginMinuteInHour;

    private int mEndMinuteInHour = sEndMinuteInHour;

    private int mSelectedMinute;

    public MinutePicker(Context context) {
        this(context, null);
    }

    public MinutePicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MinutePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        setOnWheelChangeListener(new OnWheelChangeListener<Integer>() {
            @Override
            public void onWheelSelected(Integer item, int position) {
                mSelectedMinute = item;
                if (mOnMinuteSelectedListener != null) {
                    mOnMinuteSelectedListener.onMinuteSelected(item);
                }
            }
        });
    }

    private void init() {
        setItemMaximumWidthText("00");
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumIntegerDigits(2);
        setDataFormat(numberFormat);

        updateMinute();
    }

    public void updateMinute() {
        List<Integer> list = new ArrayList<>();

        for (int i = mBeginMinuteInHour / mDelta; i <= mEndMinuteInHour / mDelta; i++) {
            list.add(i * mDelta);
        }
        setDataList(list);
    }

    public void setBeginMinuteInHour(int beginMinuteInHour) {
        this.mBeginMinuteInHour = beginMinuteInHour;
    }

    public void setEndMinuteInHour(int endMinuteInHour) {
        if (endMinuteInHour > sEndMinuteInHour) {
            endMinuteInHour = sEndMinuteInHour;
        }
        this.mEndMinuteInHour = endMinuteInHour;
    }

    public int getSelectedMinute() {
        return mSelectedMinute;
    }

    public void setDelta(int delta) {
        if (delta <= 0) {
            throw new NumberFormatException("An integer greater than zero should be set");
        }
        this.mDelta = delta;
    }

    public void setSelectedMinute(int selectedMinute) {
        setSelectedMinute(selectedMinute, false);
    }

    public void setSelectedMinute(int selectedMinute, boolean smoothScroll) {
        //找不到默认选第一个
        //TODO 待优化为：找最近的一个delta刻度，比如分钟间隔5分钟，给的是37，可以锚到40
        if (getDataList().indexOf(selectedMinute) < 0) {
            mSelectedMinute = getDataList().get(0);
        } else {
            mSelectedMinute = selectedMinute;
        }
        setCurrentItem(selectedMinute, smoothScroll);
    }

    public void setOnMinuteSelectedListener(OnMinuteSelectedListener onMinuteSelectedListener) {
        this.mOnMinuteSelectedListener = onMinuteSelectedListener;
    }

    public interface OnMinuteSelectedListener {
        void onMinuteSelected(int hour);
    }
}
