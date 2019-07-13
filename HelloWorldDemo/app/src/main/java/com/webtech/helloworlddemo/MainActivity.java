package com.webtech.helloworlddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView ans;
    EditText edt1,edt2;
    Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ans = findViewById(R.id.ans);
        edt1 = findViewById(R.id.no1);
        edt2 = findViewById(R.id.no2);
        btnsubmit = findViewById(R.id.btnsubmit);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int no1 = Integer.parseInt(edt1.getText().toString());
                int no2 = Integer.parseInt(edt2.getText().toString());
                int finalans = no1+no2;

                ans.setText("Final Ans is: "+finalans);
            }
        });
    }
}
