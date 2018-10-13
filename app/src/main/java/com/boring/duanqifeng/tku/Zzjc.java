package com.boring.duanqifeng.tku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Zzjc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zzjc);

        Button danxuan = (Button) findViewById(R.id.单选);
        Button duoxuan = (Button) findViewById(R.id.多选);
        Button panduan = (Button) findViewById(R.id.判断);
        Button liwai = (Button) findViewById(R.id.例外);


        duoxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Zzjc.this, Duoxuan.class);
                intent.putExtra("leibie", "zzjc");
                startActivity(intent);
            }
        });
        danxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Zzjc.this, Danxuan.class);
                intent.putExtra("leibie", "zzjc");
                startActivity(intent);
            }
        });
        panduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Zzjc.this, Panduan.class);
                intent.putExtra("leibie", "zzjc");
                startActivity(intent);
            }
        });
        liwai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Zzjc.this,"此类别没有超多选项题目",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
