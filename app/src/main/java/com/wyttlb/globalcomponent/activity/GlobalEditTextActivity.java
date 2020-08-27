package com.wyttlb.globalcomponent.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.wyttlb.globalcomponent.R;
import com.wyttlb.globalcomponent.config.LocaleConfig;

public class GlobalEditTextActivity extends AppCompatActivity {

    TextView tvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_edit_text);
        init();
    }

    private void init() {
        //actionbar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvTips = findViewById(R.id.tv_tips);
        StringBuilder sb = new StringBuilder();
        sb.append("当前Locale:" + LocaleConfig.Companion.getLanguage())
                .append("\n当前小数点分隔符：" + LocaleConfig.Companion.getDecimalSeparator())
                .append("\n当前千分位分隔符：" + LocaleConfig.Companion.getGroupingSeparator())
                .append("\n当前小数位长度：" + LocaleConfig.Companion.getDecimalLength())
                .append("\n当前整数位长度：" + String.valueOf(LocaleConfig.Companion.getMaxFeeLimit()).length())
                .append("\n允许输入当最大数字" + (LocaleConfig.Companion.getMaxFeeLimit() + (Math.pow(10, LocaleConfig.Companion.getDecimalLength()) - 1) * Math.pow(0.1, LocaleConfig.Companion.getDecimalLength())))
                .append("\n更改AS Flavor，编译后使用其他国家配置")
                .append("\nLocale配置更改，参见LocaleConfig");


        tvTips.setText(sb.toString());
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