package com.wyttlb.globalcomponent.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wyttlb.globalcomponent.R;
import com.wyttlb.globalcomponent.config.TimeStrategy;
import com.wyttlb.globalcomponent.datepicker.time.DayHourMinutePicker;

import java.util.Calendar;

public class GlobalTimePickerActivity extends AppCompatActivity {

    DayHourMinutePicker mTimePicker;
    TextView tvSelectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        init();
    }

    private void init() {
        //actionbar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvSelectedTime = findViewById(R.id.tv_time);
        mTimePicker = findViewById(R.id.time_picker);

        Calendar earliestCalendar = Calendar.getInstance();
        earliestCalendar.setTimeInMillis(System.currentTimeMillis());


        TimeStrategy timeStrategy = new TimeStrategy();
        //设置一天开始时间是6点
        timeStrategy.setBeginHourInDay(6);
        //设置一天结束时间是22点
        timeStrategy.setEndHourInDay(22);
        //设置最早时间是当前时间10分钟后
        earliestCalendar.add(Calendar.MINUTE, 10);
        timeStrategy.setEarliestTime(earliestCalendar.getTimeInMillis());
        //设置最晚时间是最早时间3天后

        Calendar lastesCalendar = Calendar.getInstance();
        lastesCalendar.setTimeInMillis(earliestCalendar.getTimeInMillis());
        lastesCalendar.add(Calendar.DAY_OF_MONTH, 3);

        Log.d("lb", lastesCalendar.get(Calendar.DAY_OF_MONTH) + "-"+ lastesCalendar.get(Calendar.HOUR_OF_DAY) + "-" + lastesCalendar.get(Calendar.MINUTE));
        timeStrategy.setLastestTime(lastesCalendar.getTimeInMillis());
        //设置分钟间隔为5分钟
        timeStrategy.setMinuteDelta(5);

        mTimePicker.setTimeStrategy(timeStrategy);
        mTimePicker.setOnTimeSelectedListener(new DayHourMinutePicker.OnTimeSelectedListener() {
            @Override
            public void onTimeSelected(int day, int hour, int minute, long timeInMillis) {
                tvSelectedTime.setText(String.format("当前选中时间为：%d号%d点%d分", day, hour,minute));
            }
        });

        //设置选中时间，比最早时间晚分钟
        earliestCalendar.add(Calendar.MINUTE, 30);
        //setSelectedTime会触发回调
        mTimePicker.setSelectedTime(earliestCalendar.getTimeInMillis());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}