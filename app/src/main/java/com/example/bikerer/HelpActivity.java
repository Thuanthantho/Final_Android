package com.example.bikerer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class HelpActivity extends AppCompatActivity {
    private LinearLayout btn_help1, btn_help2,btn_help3, layout_help1, layout_help2,layout_help3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btn_help1 = findViewById(R.id.btn_help1);
        btn_help2 = findViewById(R.id.btn_help2);
        btn_help3 = findViewById(R.id.btn_help3);
        layout_help1 = findViewById(R.id.layout_toggle_help1);
        layout_help2 = findViewById(R.id.layout_toggle_help2);
        layout_help3 = findViewById(R.id.layout_toggle_help3);

        btn_help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isVisible = layout_help1.getVisibility();
                if(isVisible==View.VISIBLE){
                    layout_help1.setVisibility(View.GONE);
                }else {
                    layout_help1.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isVisible = layout_help2.getVisibility();
                if(isVisible==View.VISIBLE){
                    layout_help2.setVisibility(View.GONE);
                }else {
                    layout_help2.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isVisible = layout_help3.getVisibility();
                if(isVisible==View.VISIBLE){
                    layout_help3.setVisibility(View.GONE);
                }else {
                    layout_help3.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}