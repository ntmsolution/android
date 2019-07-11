package com.example.fragementdemo;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnload = findViewById(R.id.btnload);

        btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page1 p1 = new Page1();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.frame1,p1);
                ft.commit();
            }
        });

    }
}
