package com.boring.duanqifeng.tku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Mima extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mima);

        final EditText mima = (EditText)findViewById(R.id.editMima);
        final Button signIn = (Button)findViewById(R.id.signin);

        SharedPreferences sharedPreferences = getSharedPreferences("Denglu",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mima.getText().toString().equals("SCUGFS")||mima.getText().toString().equals("段其沣")){
                    startActivity(new Intent(Mima.this,Leibie.class));
                    //以后非第一次登录
                    editor.putBoolean("isFirstIn", false);
                    editor.apply();//提交修改
                    finish();
                }else{
                    Toast.makeText(Mima.this,"密码错误",Toast.LENGTH_SHORT).show();
                    mima.setText("");
                }
            }
        });
    }


}
