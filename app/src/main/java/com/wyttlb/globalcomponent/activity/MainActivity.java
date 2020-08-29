package com.wyttlb.globalcomponent.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wyttlb.globalcomponent.R;

public class MainActivity extends AppCompatActivity {

    Button btnGoTextShow;
    Button btnGoTextEdit;
    Button btnGoTimePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btnGoTextShow = findViewById(R.id.btn_text);
        btnGoTextEdit = findViewById(R.id.btn_edit_text);
        btnGoTimePicker = findViewById(R.id.btn_time_picker);

        btnGoTextShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GlobalTextViewActivity.class));
            }
        });

        btnGoTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GlobalEditTextActivity.class));
            }
        });

        btnGoTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GlobalTimePickerActivity.class));
            }
        });
    }
}