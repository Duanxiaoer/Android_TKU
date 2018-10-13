package com.boring.duanqifeng.tku;
/*  段其沣于2017年10月9日
    在四川大学江安校区创建
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Leibie extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leibie);
        //每种类别的题目数
        sharedPreferences = getSharedPreferences("TMS",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("gjs_danx",79);
        editor.putInt("gjs_dx",102);
        editor.putInt("gjs_pd",191);

        editor.putInt("jbz_danx",60);
        editor.putInt("jbz_dx",54);
        editor.putInt("jbz_lw",3);
        editor.putInt("jbz_pd",124);

        editor.putInt("jcjs_danx",47);
        editor.putInt("jcjs_dx",31);
        editor.putInt("jcjs_pd",58);

        editor.putInt("jl_danx",91);
        editor.putInt("jl_dx",21);
        editor.putInt("jl_pd",110);

        editor.putInt("jsll_danx",386);
        editor.putInt("jsll_dx",41);
        editor.putInt("jsll_pd",42);

        editor.putInt("nw_danx",84);
        editor.putInt("nw_dx",111);
        editor.putInt("nw_pd",18);
        editor.putInt("nw_lw",262);

        editor.putInt("xljc_danx",39);
        editor.putInt("xljc_dx",45);
        editor.putInt("xljc_pd",29);

        editor.putInt("zzjc_danx",24);
        editor.putInt("zzjc_dx",37);
        editor.putInt("zzjc_pd",42);

        editor.putInt("dl_danx",91);
        editor.putInt("dl_dx",18);
        editor.putInt("dl_pd",394);

        editor.apply();

        Button 内务 = (Button) findViewById(R.id.内务);
        Button 队列 = (Button) findViewById(R.id.队列);
        Button 纪律 = (Button) findViewById(R.id.纪律);
        Button 军兵种 = (Button) findViewById(R.id.军兵种);
        Button 军事理论 = (Button) findViewById(R.id.军事理论);
        Button 军事高技术 = (Button) findViewById(R.id.军事高技术);
        Button 军队基层建设 = (Button) findViewById(R.id.军队基层建设);
        Button 训练基础理论 = (Button) findViewById(R.id.训练基础理论);
        Button 作战基础知识 = (Button) findViewById(R.id.作战基础知识);
        Button 模拟考试 = (Button) findViewById(R.id.kaoShi);
        Button 错题集 = (Button) findViewById(R.id.cuoTi);






        内务.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Neiwu.class));
            }
        });
        队列.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Duilie.class));

            }
        });
        纪律.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Jilv.class));

            }
        });
        军兵种.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Jbz.class));

            }
        });
        军事理论.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Jsll.class));

            }
        });
        军事高技术.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Gjs.class));

            }
        });
        军队基层建设.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Jcjs.class));

            }
        });
        训练基础理论.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Xljc.class));

            }
        });
        作战基础知识.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Zzjc.class));
            }
        });
        模拟考试.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Moni.class));
            }
        });
        错题集.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Leibie.this,Cuotiji.class));
            }
        });




    }


}
