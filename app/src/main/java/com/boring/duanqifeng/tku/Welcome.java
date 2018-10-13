package com.boring.duanqifeng.tku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;


public class Welcome extends AppCompatActivity {

    private static final int TIME = 2000;
    private static final int GO_LEIBIE = 1000;
    private static final int GO_DENGLU = 1010;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Can not write the file to root, you need to pass the file path to your file directory.
        String filePath = this.getFilesDir().getPath() + "/错题集.txt";
        File file = new File(filePath);
        if (file.exists()){
        }else {
            try {

                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        init();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_LEIBIE:
                    gomleibie();
                    break;
                case GO_DENGLU:
                    godenglu();
                    break;
            }
        }
    };

    private void init() {

        //获取登陆次数的数据
        sharedPreferences = getSharedPreferences("Denglu",MODE_PRIVATE);
        boolean isFirstIn = sharedPreferences.getBoolean("isFirstIn", true);
        if (isFirstIn) {
            handler.sendEmptyMessageDelayed(GO_DENGLU, TIME);
        } else {
            handler.sendEmptyMessageDelayed(GO_LEIBIE, TIME);
        }
    }

    private void gomleibie() {
        Intent i = new Intent(Welcome.this, Leibie.class);
        startActivity(i);
        finish();
    }

    private void godenglu() {
        Intent i = new Intent(Welcome.this, Mima.class);
        startActivity(i);
        finish();
    }
}
