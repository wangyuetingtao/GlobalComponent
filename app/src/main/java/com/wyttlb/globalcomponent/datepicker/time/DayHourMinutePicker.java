package com.wyttlb.globalcomponent.datepicker.time;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.wyttlb.globalcomponent.R;
import com.wyttlb.globalcomponent.config.TimeStrategy;
import com.wyttlb.globalcomponent.datepicker.model.DayModel;

import java.util.Calendar;

/**
 * 日十分时间选择器
 * 功能：自动筛选过去的时间
 * 支持配置最早和最晚时间(精度小时级别，分钟不支持)
 * 支持配置分钟间隔，幕布颜色，字体字号等
 * 提升性能：一次滚动即使导致多个时间变化，也仅触发一次OnTimeSelectedListener回调
 */
public class DayHourMinutePicker extends LinearLayout implements
    DaysPicker.OnDaySelectedListener, HourPicker.OnHourSelectedListener, MinutePicker.OnMinuteSelectedListener{
    private DaysPicker mDayPicker;
    private HourPicker mHourPicker;
    private MinutePicker mMinutePicker;
    private OnTimeSelectedListener mOnTimeSelectedListener;
    private TimeStrategy mTimeStrategy;

    public DayHourMinutePicker(Context context) {
        this(context, null);
    }

    public DayHourMinutePicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DayHourMinutePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_day_hour_minute, this);

        initChild();
        initAttrs(context, attrs);

    }

    @Override
    public void onDaySelected(DayModel day) {
        handleDay(day, false);
        onTimeSelected();
    }

    private void handleDay(DayModel day, boolean isSmoothScroll) {
        if (mTimeStrategy != null) {
            ///
            Calendar earliestCalendar = Calendar.getInstance();
            earliestCalendar.setTimeInMillis(mTimeStrategy.getEarliestTime());

            int earlistDay = earliestCalendar.get(Calendar.DAY_OF_MONTH);
            int earlistHour = earliestCalendar.get(Calendar.HOUR_OF_DAY);
            int earlistMinute = earliestCalendar.get(Calendar.MINUTE);

            //处理最早时间
            if(day.getDay() == earlistDay) {
                mHourPicker.setBeginHourInDay(earlistHour < mTimeStrategy.getBeginHourInDay() ? mTimeStrategy.getBeginHourInDay() : earlistHour);
                mHourPicker.setEndHourInDay(mTimeStrategy.getEndHourInDay());

                if (mHourPicker.getSelectedHour() <= earlistHour) {
                    mMinutePicker.setBeginMinuteInHour(earlistMinute);
                } else {
                    mMinutePicker.setBeginMinuteInHour(MinutePicker.sBeginMinuteInHour);
                }
            } else {
               mHourPicker.setBeginHourInDay(mTimeStrategy.getBeginHourInDay());
               mHourPicker.setEndHourInDay(mTimeStrategy.getEndHourInDay());
               mMinutePicker.setBeginMinuteInHour(MinutePicker.sBeginMinuteInHour);
            }
            mMinutePicker.setEndMinuteInHour(MinutePicker.sEndMinuteInHour);

            ///
            Calendar lastestCalendar = Calendar.getInstance();
            lastestCalendar.setTimeInMillis(mTimeStrategy.getLastestTime());

            int lastestDay = lastestCalendar.get(Calendar.DAY_OF_MONTH);
            int lastestHour = lastestCalendar.get(Calendar.HOUR_OF_DAY);
            int lastestMinute = lastestCalendar.get(Calendar.MINUTE);

            //处理最晚时间
            if (day.getDay() == lastestDay) {
                mHourPicker.setEndHourInDay(mTimeStrategy.getEndHourInDay() < lastestHour ? mTimeStrategy.getEndHourInDay() : lastestHour);
                if (mHourPicker.getSelectedHour() == lastestHour) {
                    mMinutePicker.setEndMinuteInHour(lastestMinute);
                } else {
                    mMinutePicker.setBeginMinuteInHour(MinutePicker.sBeginMinuteInHour);
                    mMinutePicker.setEndMinuteInHour(MinutePicker.sEndMinuteInHour);
                }
            }
            mHourPicker.updateHour();
            mMinutePicker.updateMinute();
            mMinutePicker.setSelectedMinute(mMinutePicker.getSelectedMinute(), isSmoothScroll);
            mHourPicker.setSelectedHour(mHourPicker.getSelectedHour(), isSmoothScroll);
        }
    }

    @Override
    public void onHourSelected(int hour) {
        handleHour(hour, false);
        onTimeSelected();
    }

    private void handleHour(int hour, boolean isSmoothScroll) {
        if (mTimeStrategy != null) {
            //处理最早时间
            ///
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mTimeStrategy.getEarliestTime());

            int earlistDay = calendar.get(Calendar.DAY_OF_MONTH);
            int earlistHour = calendar.get(Calendar.HOUR_OF_DAY);
            int earlistMinute = calendar.get(Calendar.MINUTE);

            if (mDayPicker.getSelectedDay().getDay() == earlistDay && hour == earlistHour) {
                mMinutePicker.setBeginMinuteInHour(earlistMinute);
                mMinutePicker.setEndMinuteInHour(mMinutePicker.sEndMinuteInHour);
            } else {
                mMinutePicker.setBeginMinuteInHour(MinutePicker.sBeginMinuteInHour);
                mMinutePicker.setEndMinuteInHour(MinutePicker.sEndMinuteInHour);
            }

            //处理最晚时间
            ///
            Calendar lastestCalenar = Calendar.getInstance();
            lastestCalenar.setTimeInMillis(mTimeStrategy.getLastestTime());

            int lastestDay = lastestCalenar.get(Calendar.DAY_OF_MONTH);
            int lastestHour = lastestCalenar.get(Calendar.HOUR_OF_DAY);
            int lastestMinute = lastestCalenar.get(Calendar.MINUTE);

            if (mDayPicker.getSelectedDay().getDay() == lastestDay && hour == lastestHour) {
                if (mHourPicker.getSelectedHour() == lastestHour) {
                    mMinutePicker.setEndMinuteInHour(lastestMinute);
                } else {
                    mMinutePicker.setBeginMinuteInHour(MinutePicker.sBeginMinuteInHour);
                    mMinutePicker.setEndMinuteInHour(MinutePicker.sEndMinuteInHour);
                }
            }

            mMinutePicker.updateMinute();
            mMinutePicker.setSelectedMinute(mMinutePicker.getSelectedMinute(), isSmoothScroll);
        }
    }
    @Override
    public void onMinuteSelected(int hour) {
        onTimeSelected();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DayHourAndMinutePicker);
        int textSize = a.getDimensionPixelSize(R.styleable.DayHourAndMinutePicker_itemTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        int textColor = a.getColor(R.styleable.DayHourAndMinutePicker_itemTextColor,
                Color.BLACK);
        boolean isTextGradual = a.getBoolean(R.styleable.DayHourAndMinutePicker_textGradual, true);
        boolean isCyclic = a.getBoolean(R.styleable.DayHourAndMinutePicker_wheelCyclic, false);
        int halfVisibleItemCount = a.getInteger(R.styleable.DayHourAndMinutePicker_halfVisibleItemCount, 2);
        int selectedItemTextColor = a.getColor(R.styleable.DayHourAndMinutePicker_selectedTextColor, Color.parseColor("#000000"));
        int selectedItemTextSize = a.getDimensionPixelSize(R.styleable.DayHourAndMinutePicker_selectedTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelSelectedItemTextSize));
        int itemWidthSpace = a.getDimensionPixelSize(R.styleable.DayHourAndMinutePicker_itemWidthSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemWidthSpace));
        int itemHeightSpace = a.getDimensionPixelSize(R.styleable.DayHourAndMinutePicker_itemHeightSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemHeightSpace));
        boolean isZoomInSelectedItem = a.getBoolean(R.styleable.DayHourAndMinutePicker_zoomInSelectedItem, true);
        boolean isShowCurtain = a.getBoolean(R.styleable.DayHourAndMinutePicker_wheelCurtain, true);
        int curtainColor = a.getColor(R.styleable.DayHourAndMinutePicker_wheelCurtainColor,
                Color.parseColor("#303d3d3d"));
        boolean isShowCurtainBorder = a.getBoolean(R.styleable.DayHourAndMinutePicker_wheelCurtainBorder, true);
        int curtainBorderColor = a.getColor(R.styleable.DayHourAndMinutePicker_wheelCurtainBorderColor, Color.BLACK);
        a.recycle();

        setTextSize(textSize);
        setTextColor(textColor);
        setTextGradual(isTextGradual);
        setCyclic(isCyclic);
        setHalfVisibleItemCount(halfVisibleItemCount);
        setSelectedItemTextColor(selectedItemTextColor);
        setSelectedItemTextSize(selectedItemTextSize);
        setItemWidthSpace(itemWidthSpace);
        setItemHeightSpace(itemHeightSpace);
        setZoomInSelectedItem(isZoomInSelectedItem);
        setShowCurtain(isShowCurtain);
        setCurtainColor(curtainColor);
        setShowCurtainBorder(isShowCurtainBorder);
        setCurtainBorderColor(curtainBorderColor);

    }

    private void initChild() {
        mDayPicker = findViewById(R.id.day_picker);
        mDayPicker.setOnDaySelectedListener(this);
        mHourPicker = findViewById(R.id.hour_picker);
        mHourPicker.setOnHourSelectedListener(this);
        mMinutePicker = findViewById(R.id.minute_picker);
        mMinutePicker.setOnMinuteSelectedListener(this);
    }

    private void onTimeSelected() {
        if (mOnTimeSelectedListener != null) {
            mOnTimeSelectedListener.onTimeSelected(getDay(), getHour(), getMinute(), getTimeInMillis());
        }
    }

    public void setTimeStrategy(TimeStrategy timeStrategy) {
        this.mTimeStrategy = timeStrategy;

        if (timeStrategy != null) {
            long minDate = timeStrategy.getEarliestTime();
            long maxDate = timeStrategy.getLastestTime();

            mDayPicker.setMinDay(minDate);
            mDayPicker.setMaxDay(maxDate);
            mDayPicker.updateDay();

            mHourPicker.setBeginHourInDay(timeStrategy.getBeginHourInDay());
            mHourPicker.setEndHourInDay(timeStrategy.getEndHourInDay());
            mHourPicker.updateHour();

            mMinutePicker.setDelta(timeStrategy.getMinuteDelta());
            mMinutePicker.updateMinute();
        }
    }

    /**
     * 设置初始选中time
     * @param time
     */
    public void setSelectedTime(long time) {
        ///
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        //处理最早和最晚
        handleDay(new DayModel(year, month, day), false);
        mDayPicker.setSelectedDay(new DayModel(year, month, day), false);
        handleHour(hour, false);
        mHourPicker.setSelectedHour(hour, false);
        mMinutePicker.setSelectedMinute(minute, false);
        //增加一次手动回调
        onTimeSelected();
    }

    /**
     * 获取选中的天(整数)
     * @return
     */
    public int getDay() {
        return mDayPicker.getSelectedDay().getDay();
    }

    /**
     * 获得选中的小时
     * @return
     */
    public int getHour() {
        return mHourPicker.getSelectedHour();
    }

    /**
     * 获得分钟
     * @return
     */
    public int getMinute() {
        return mMinutePicker.getSelectedMinute();
    }

    /**
     * 获得时间戳
     * @return
     */
    public long getTimeInMillis() {
        ///
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        DayModel selectedDayModel = mDayPicker.getSelectedDay();
        int year = selectedDayModel.getYear();
        int month = selectedDayModel.getMonth();
        int day = selectedDayModel.getDay();
        calendar.set(year, month, day, getHour(),getMinute());
        return  calendar.getTimeInMillis();
    }

    /**
     * 设置列表未选中文字的大小
     * @param textSize
     */
    public void setTextSize(int textSize) {
        mDayPicker.setTextSize(textSize);
        mHourPicker.setTextSize(textSize);
        mMinutePicker.setTextSize(textSize);
    }

    /**
     * 设置列表未选中文字的颜色
     * @param textColor
     */
    public void setTextColor(int textColor) {
        mDayPicker.setTextColor(textColor);
        mHourPicker.setTextColor(textColor);
        mMinutePicker.setTextColor(textColor);
    }

    /**
     * 设置列表中被选中文字的颜色
     * @param selectedItemTextColor
     */
    public void setSelectedItemTextColor(int selectedItemTextColor) {
        mDayPicker.setSelectedItemTextColor(selectedItemTextColor);
        mHourPicker.setSelectedItemTextColor(selectedItemTextColor);
        mMinutePicker.setSelectedItemTextColor(selectedItemTextColor);
    }

    /**
     * 设置列表中被选中文字的大小
     * @param selectedItemTextSize
     */
    public void setSelectedItemTextSize(int selectedItemTextSize) {
        mDayPicker.setSelectedItemTextSize(selectedItemTextSize);
        mHourPicker.setSelectedItemTextSize(selectedItemTextSize);
        mMinutePicker.setSelectedItemTextSize(selectedItemTextSize);
    }

    /**
     * 设置显示数据量的个数的一半
     * 那么总共显示的数量为 mHalfVisibleItemCount *2 + 1
     * @param halfVisibleItemCount
     */
    public void setHalfVisibleItemCount(int halfVisibleItemCount) {
        mDayPicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mHourPicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mMinutePicker.setHalfVisibleItemCount(halfVisibleItemCount);
    }

    /**
     * 设置item横向间距
     * @param itemWidthSpace
     */
    public void setItemWidthSpace(int itemWidthSpace) {
        mDayPicker.setItemWidthSpace(itemWidthSpace);
        mHourPicker.setItemWidthSpace(itemWidthSpace);
        mMinutePicker.setItemWidthSpace(itemWidthSpace);
    }

    /**
     * 设置两个item之间的高度间隔
     * @param heightSpace
     */
    public void setItemHeightSpace(int heightSpace) {
        mDayPicker.setItemHeightSpace(heightSpace);
        mHourPicker.setItemHeightSpace(heightSpace);
        mMinutePicker.setItemHeightSpace(heightSpace);
    }

    /**
     * 设置居中是否放大
     * @param zoomInSelectedItem
     */
    public void setZoomInSelectedItem(boolean zoomInSelectedItem) {
        mDayPicker.setZoomInSelectedItem(zoomInSelectedItem);
        mHourPicker.setZoomInSelectedItem(zoomInSelectedItem);
        mMinutePicker.setZoomInSelectedItem(zoomInSelectedItem);
    }

    /**
     * 设置是否循环
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        mDayPicker.setCyclic(cyclic);
        mHourPicker.setCyclic(cyclic);
        mMinutePicker.setCyclic(cyclic);
    }

    /**
     * 设置文字渐变，离中心越远越淡
     * @param textGradual
     */
    public void setTextGradual(boolean textGradual) {
        mDayPicker.setTextGradual(textGradual);
        mHourPicker.setTextGradual(textGradual);
        mMinutePicker.setTextGradual(textGradual);
    }

    /**
     * 设置中心item是否有幕布
     * @param showCurtain
     */
    public void setShowCurtain(boolean showCurtain) {
        mDayPicker.setShowCurtain(showCurtain);
        mHourPicker.setShowCurtain(showCurtain);
        mMinutePicker.setShowCurtain(showCurtain);
    }

    /**
     * 设置幕布颜色
     */
    public void setCurtainColor(int curtainColor) {
        mDayPicker.setCurtainColor(curtainColor);
        mHourPicker.setCurtainColor(curtainColor);
        mMinutePicker.setCurtainColor(curtainColor);
    }

    /**
     * 设置幕布是否显示边框
     * @param showCurtainBorder
     */
    public void setShowCurtainBorder(boolean showCurtainBorder) {
        mDayPicker.setShowCurtainBorder(showCurtainBorder);
        mHourPicker.setShowCurtainBorder(showCurtainBorder);
        mMinutePicker.setShowCurtainBorder(showCurtainBorder);
    }

    /**
     * 设置幕布边框颜色
     * @param curtainBorderColor
     */
    public void setCurtainBorderColor(int curtainBorderColor) {
        mDayPicker.setCurtainBorderColor(curtainBorderColor);
        mHourPicker.setCurtainBorderColor(curtainBorderColor);
        mMinutePicker.setCurtainBorderColor(curtainBorderColor);
    }

    public void setOnTimeSelectedListener(OnTimeSelectedListener onTimeSelectedListener) {
        this.mOnTimeSelectedListener = onTimeSelectedListener;
    }

    /**
     * The interface on date selected listener
     */
    public interface OnTimeSelectedListener {
        /**
         * on time selected
         * @param day 回调天
         * @param hour 回调hour
         * @param minute 回调分钟
         * @param timeInMillis 回调时间的haomiao值
         */
        void onTimeSelected(int day, int hour, int minute, long timeInMillis);
    }
}
