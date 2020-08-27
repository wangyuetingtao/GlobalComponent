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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btnGoTextShow = findViewById(R.id.btn_text);
        btnGoTextEdit = findViewById(R.id.btn_edit_text);

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
    }
}