package com.wyttlb.globalcomponent.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.wyttlb.globalcomponent.R;
import com.wyttlb.globalcomponent.config.LocaleConfig;
import com.wyttlb.globalcomponent.text.GlobalAnimateTextView;

public class GlobalTextViewActivity extends AppCompatActivity {

    GlobalAnimateTextView tvMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        init();
    }

    private void init() {
        //actionbar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvMoney = findViewById(R.id.tv_money);
        tvMoney.setDuration(2000);
        tvMoney.setIsEnableAnim(true);
        tvMoney.setPrefixString(LocaleConfig.Companion.getCurrencySymbol());
        tvMoney.setNumber(0, 18888.4f);
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