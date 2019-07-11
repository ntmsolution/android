package com.webtech.spinnerdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    Spinner spcolor;
    RadioButton rdomale,rdofemale;
    CheckBox cricket,hockey,chess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        spcolor = findViewById(R.id.color);
        rdomale = findViewById(R.id.rdo_male);
        rdofemale = findViewById(R.id.rdo_female);

        cricket = findViewById(R.id.chkcricket);
        hockey = findViewById(R.id.chkhockey);
        chess = findViewById(R.id.chkchess);

        String[] colordata = getResources().getStringArray(R.array.colorarray);
        List<String> colorlist = Arrays.asList(colordata);

        String gender1 = "Male";
        String hobbies1 = "Cricket,Chess";
        String[] arhobbies = hobbies1.split(",");

        if(arhobbies.length==1){
            if(arhobbies[0].equalsIgnoreCase("Cricket")){
                cricket.setChecked(true);
            }
            if(arhobbies[0].equalsIgnoreCase("Hockey")){
                hockey.setChecked(true);
            }
            if(arhobbies[0].equalsIgnoreCase("Chess")){
                chess.setChecked(true);
            }
        }

        if(arhobbies.length==2){
            if(arhobbies[0].equalsIgnoreCase("Cricket")){
                cricket.setChecked(true);
            }
            if(arhobbies[0].equalsIgnoreCase("Hockey")){
                hockey.setChecked(true);
            }
            if(arhobbies[0].equalsIgnoreCase("Chess")){
                chess.setChecked(true);
            }

            if(arhobbies[1].equalsIgnoreCase("Hockey")){
                hockey.setChecked(true);
            }
            if(arhobbies[1].equalsIgnoreCase("Chess")){
                chess.setChecked(true);
            }
        }

        if(arhobbies.length==3){
            if(arhobbies[0].equalsIgnoreCase("Cricket")){
                cricket.setChecked(true);
            }
            if(arhobbies[0].equalsIgnoreCase("Hockey")){
                hockey.setChecked(true);
            }
            if(arhobbies[0].equalsIgnoreCase("Chess")){
                chess.setChecked(true);
            }

            if(arhobbies[1].equalsIgnoreCase("Hockey")){
                hockey.setChecked(true);
            }
            if(arhobbies[1].equalsIgnoreCase("Chess")){
                chess.setChecked(true);
            }

            if(arhobbies[2].equalsIgnoreCase("Chess")){
                chess.setChecked(true);
            }
        }


        if(gender1.equalsIgnoreCase("Male")){
            rdomale.setChecked(true);
        }


        ArrayAdapter adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,colordata);
        spcolor.setAdapter(adapter);

        spcolor.setSelection(colorlist.indexOf("Blue"));

    }
}
