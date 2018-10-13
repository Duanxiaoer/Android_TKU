package com.boring.duanqifeng.tku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Duilie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duilie);


        Button danxuan = (Button) findViewById(R.id.单选);
        Button duoxuan = (Button) findViewById(R.id.多选);
        Button panduan = (Button) findViewById(R.id.判断);
        Button liwai = (Button) findViewById(R.id.例外);


        duoxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Duilie.this, Duoxuan.class);
                intent.putExtra("leibie", "duilie");
                startActivity(intent);
            }
        });
        danxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Duilie.this, Danxuan.class);
                intent.putExtra("leibie", "duilie");
                startActivity(intent);
            }
        });
        panduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Duilie.this, Panduan.class);
                intent.putExtra("leibie", "duilie");
                startActivity(intent);
            }
        });
        liwai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Duilie.this,"此类别没有超多选项题目",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
