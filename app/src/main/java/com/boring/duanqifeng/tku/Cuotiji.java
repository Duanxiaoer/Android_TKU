package com.boring.duanqifeng.tku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cuotiji extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuotiji);

        TextView textView = (TextView) findViewById(R.id.ctTextView);

        try {
            final FileInputStream fileInputStream = openFileInput("错题集.txt");
            final InputStreamReader ctinputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader ctbufferedReader = new BufferedReader(ctinputStreamReader);

            String line="",line1="";
            textView.setText("");
            while((line = ctbufferedReader.readLine()) != null){
                line1 = line1 + line +"\n";
                textView.setText(line1);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
