package com.boring.duanqifeng.tku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Neiwu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neiwu);


        Button danxuan = (Button) findViewById(R.id.单选);
        Button duoxuan = (Button) findViewById(R.id.多选);
        Button panduan = (Button) findViewById(R.id.判断);
        Button liwai = (Button) findViewById(R.id.例外);


        duoxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Neiwu.this,Duoxuan.class);
                intent.putExtra("leibie","neiwu");
                startActivity(intent);
            }
        });
        danxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Neiwu.this,Danxuan.class);
                intent.putExtra("leibie","neiwu");
                startActivity(intent);
            }
        });
        panduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Neiwu.this,Panduan.class);
                intent.putExtra("leibie","neiwu");
                startActivity(intent);
            }
        });
        liwai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递数据给子activity并启动子activity
                Intent intent = new Intent();
                intent.setClass(Neiwu.this,Liwai.class);
                intent.putExtra("leibie","neiwu");
                startActivity(intent);
            }
        });





    }
}
