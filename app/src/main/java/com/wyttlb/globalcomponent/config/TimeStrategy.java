package com.wyttlb.globalcomponent.config;

/**
 * 时间配置
 * @author wyttlb
 */
public class TimeStrategy {
    //分钟间隔-默认配置
    private static final int sDefalutMinuteDelta = 10;
    //每天最早开始时间-默认配置
    private static final int sDefalutBeginHourInDay = 0;
    //每天最晚结束时间-默认配置
    private static final int sDefalutEndHourInDay = 23;

    //分钟间隔
    private int mMinuteDelta = sDefalutMinuteDelta;
    private int mBeginHourInDay = sDefalutBeginHourInDay;
    private int mEndHourInDay = sDefalutEndHourInDay;

    //最早时间
    private long mEarliestTime;
    //最晚时间
    private long mLastestTime;

    public TimeStrategy() {}

    public int getMinuteDelta() {
        return mMinuteDelta;
    }

    public void setMinuteDelta(int mMinuteDelta) {
        this.mMinuteDelta = mMinuteDelta;
    }

    public int getBeginHourInDay() {
        return mBeginHourInDay;
    }

    public void setBeginHourInDay(int mBeginHourInDay) {
        this.mBeginHourInDay = mBeginHourInDay;
    }

    public int getEndHourInDay() {
        return mEndHourInDay;
    }

    public void setEndHourInDay(int mEndHourInDay) {
        this.mEndHourInDay = mEndHourInDay;
    }

    public long getEarliestTime() {
        return mEarliestTime;
    }

    public void setEarliestTime(long mEarliestTime) {
        this.mEarliestTime = mEarliestTime;
    }

    public long getLastestTime() {
        return mLastestTime;
    }

    public void setLastestTime(long mLastestTime) {
        this.mLastestTime = mLastestTime;
    }
}
