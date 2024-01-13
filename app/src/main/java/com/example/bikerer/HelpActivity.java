package com.example.bikerer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HelpActivity extends AppCompatActivity {
    private LinearLayout btn_help1, layout_help1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        btn_help1 = findViewById(R.id.btn_help1);
        layout_help1 = findViewById(R.id.layout_toggle_help1);

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
    }
}