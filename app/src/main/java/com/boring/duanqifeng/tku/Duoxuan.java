package com.boring.duanqifeng.tku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Duoxuan extends AppCompatActivity {

    Button submit,saveJD,clearJD,suiJI;
    CheckBox A, B,C,D;
    List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
    int tiShu;
    TextView tmView;
    String answer,leibie,Ti;
    Random random = new Random();
    String tmStr,checkedText = "";
    Boolean isReadFromT = true,isSuiJi=false;
    BufferedReader bufferedReader;

    //为了将错题写入错题集
    FileOutputStream fileOutputStream ;
    OutputStreamWriter outputStreamWriter;
    BufferedWriter bufferedWriter ;
    //为了搜索错题集
    FileInputStream fileInputStream ;
    InputStreamReader ctinputStreamReader;
    BufferedReader ctbufferedReader;


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            //声明缓存空间
            SharedPreferences 当前题dx = getSharedPreferences("当前题dx",MODE_PRIVATE);
            SharedPreferences 类别题目数 = getSharedPreferences("TMS",MODE_PRIVATE);
            final SharedPreferences.Editor editor = 当前题dx.edit();

            Ti = 当前题dx.getString("t","");//获取上一次的进度

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_duoxuan);

            //获取父activity传来的数据
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            leibie = bundle.getString("leibie");



            A = (CheckBox) this.findViewById(R.id.A);
            B = (CheckBox) this.findViewById(R.id.B);
            C = (CheckBox) this.findViewById(R.id.C);
            D = (CheckBox) this.findViewById(R.id.D);

            //将所有的checkbox放到一个集合中
            checkBoxList.add(A);
            checkBoxList.add(B);
            checkBoxList.add(C);
            checkBoxList.add(D);

            submit = (Button) this.findViewById(R.id.submit);
            saveJD = (Button) this.findViewById(R.id.saveJD);
            suiJI = (Button) this.findViewById(R.id.suiJI);
            clearJD = (Button) this.findViewById(R.id.clearJD);
            tmView = (TextView) this.findViewById(R.id.textView);

            //选择题库
            InputStream inputStream = getResources().openRawResource(R.raw.nw_dx);
            switch (leibie){
                case "neiwu":
                    inputStream = getResources().openRawResource(R.raw.nw_dx);
                    tiShu = 类别题目数.getInt("nw_dx",50);
                    break;
                case "duilie":
                    inputStream = getResources().openRawResource(R.raw.dl_dx);
                    tiShu = 类别题目数.getInt("dl_dx",50);
                    break;
                case "jilv":
                    inputStream = getResources().openRawResource(R.raw.jl_dx);
                    tiShu = 类别题目数.getInt("jl_dx",50);
                    break;
                case "gjs":
                    inputStream = getResources().openRawResource(R.raw.gjs_dx);
                    tiShu = 类别题目数.getInt("gjs_dx",50);
                    break;
                case "jsll":
                    inputStream = getResources().openRawResource(R.raw.jsll_dx);
                    tiShu = 类别题目数.getInt("jsll_dx",50);
                    break;
                case "xljc":
                    inputStream = getResources().openRawResource(R.raw.xljc_dx);
                    tiShu = 类别题目数.getInt("xljc_dx",50);
                    break;
                case "zzjc":
                    inputStream = getResources().openRawResource(R.raw.zzjc_dx);
                    tiShu = 类别题目数.getInt("zzjc_dx",50);
                    break;
                case "jcjs":
                    inputStream = getResources().openRawResource(R.raw.jcjs_dx);
                    tiShu = 类别题目数.getInt("jcjs_dx",50);
                    break;
                case "jbz":
                    inputStream = getResources().openRawResource(R.raw.jbz_dx);
                    tiShu = 类别题目数.getInt("jbz_dx",50);
                    break;
            }

            InputStreamReader inputStreamReader ;
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);



            read(tiShu);//读取题目和选项及答案



            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkedText = "";
                    for (CheckBox checkbox : checkBoxList){
                        if (checkbox.isChecked()){
                            checkedText += checkbox.getText().charAt(0);
                        }
                    }

                    if (checkedText.toCharArray().length == 0) {
                        Toast.makeText(Duoxuan.this, "请勿选择空选项", Toast.LENGTH_SHORT).show();
                    } else if (checkedText.equals(answer)) {
                        Toast.makeText(Duoxuan.this, "回答正确", Toast.LENGTH_SHORT).show();
                        read(tiShu);
                    } else {
                        Toast.makeText(Duoxuan.this, "回答错误，\n正确答案："+answer, Toast.LENGTH_SHORT).show();

                        try {//搜索错题集，如没有则将其写入错题集

                            //为了将错题写入错题集
                            fileOutputStream = openFileOutput("错题集.txt", Context.MODE_APPEND);
                            outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");
                            bufferedWriter =new BufferedWriter(outputStreamWriter);
                            //为了搜索错题集
                            fileInputStream = openFileInput("错题集.txt");
                            ctinputStreamReader = new InputStreamReader(fileInputStream);
                            ctbufferedReader = new BufferedReader(ctinputStreamReader);


                            Boolean isInside = false;//默认该题目还不存在于错题集中

                            String line="",line1=tmView.getText().toString();
                            while((line = ctbufferedReader.readLine()) != null){
                                if (line.contains(line1)){
                                    isInside = true;
                                    System.out.println("该题目已经在错题本中");
                                    break;//该题目已经在错题本中，跳出
                                }
                            }


                            if (!isInside){//如果该题目还未录入，则录入错题集
                                bufferedWriter.append(tmView.getText().toString()).append("\n");
                                bufferedWriter.append(A.getText().toString()).append("\n");
                                bufferedWriter.append(B.getText().toString()).append("\n");
                                bufferedWriter.append(C.getText().toString()).append("\n");
                                bufferedWriter.append(D.getText().toString()).append("\n");
                                bufferedWriter.append(answer).append("\n");
                                bufferedWriter.newLine();
                                System.out.println("写入成功");
                            }

                            ctbufferedReader.close();//读错题
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
//              保存进度
            saveJD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString("t",tmView.getText().toString());
                    editor.apply();
                    Toast.makeText(Duoxuan.this,"保存成功",Toast.LENGTH_SHORT).show();

                }
            });

            //            清除进度
            clearJD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString("t","");
                    editor.apply();
                    Toast.makeText(Duoxuan.this,"清除成功",Toast.LENGTH_SHORT).show();
                }
            });
            suiJI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSuiJi = !isSuiJi;
                    if (isSuiJi){
                        Toast.makeText(Duoxuan.this,"目前为随机出题",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Duoxuan.this,"目前为顺序出题",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private  void read(int tishu){
        try {
            if(isSuiJi){//随机出题
                int i=0;
                int t=random.nextInt(tishu-1);
                while(i<t*6){//产生0~题目数-1之间的随机数，然后乘以每道题目的行数
                    bufferedReader.readLine();
                    i++;
                }
                //从随机确定的题目后读取一道显示
                A.setChecked(false);
                B.setChecked(false);
                C.setChecked(false);
                D.setChecked(false);
                tmStr = bufferedReader.readLine();
                tmView.setText(tmStr);
                tmStr = bufferedReader.readLine();
                A.setText(tmStr);
                tmStr = bufferedReader.readLine();
                B.setText(tmStr);
                tmStr = bufferedReader.readLine();
                C.setText(tmStr);
                tmStr = bufferedReader.readLine();
                D.setText(tmStr);
                answer = bufferedReader.readLine();
                bufferedReader.reset();
            }else{
                if (Ti.equals("")){//之前没有进度，从第一道开始
                    bufferedReader.mark(100000);//与上面的reset()配合使用，以便每次从第一题随机出题
                    A.setChecked(false);
                    B.setChecked(false);
                    C.setChecked(false);
                    D.setChecked(false);
                    tmStr = bufferedReader.readLine();
                    tmView.setText(tmStr);
                    tmStr = bufferedReader.readLine();
                    A.setText(tmStr);
                    tmStr = bufferedReader.readLine();
                    B.setText(tmStr);
                    tmStr = bufferedReader.readLine();
                    C.setText(tmStr);
                    tmStr = bufferedReader.readLine();
                    D.setText(tmStr);
                    answer = bufferedReader.readLine();
                }else{//之前有进度
                    if (isReadFromT){//是否是中断后的第一次读
                        String temp;
                        do{//一直找到上次的最后一道题，才开始做
                            temp = bufferedReader.readLine();
                        }while (!Ti.equals(temp));
                        A.setChecked(false);
                        B.setChecked(false);
                        C.setChecked(false);
                        D.setChecked(false);
                        tmStr = temp;
                        tmView.setText(tmStr);
                        tmStr = bufferedReader.readLine();
                        A.setText(tmStr);
                        tmStr = bufferedReader.readLine();
                        B.setText(tmStr);
                        tmStr = bufferedReader.readLine();
                        C.setText(tmStr);
                        tmStr = bufferedReader.readLine();
                        D.setText(tmStr);

                        answer = bufferedReader.readLine();
                        isReadFromT = false;
                    }else{
                        A.setChecked(false);
                        B.setChecked(false);
                        C.setChecked(false);
                        D.setChecked(false);
                        tmStr = bufferedReader.readLine();
                        tmView.setText(tmStr);
                        tmStr = bufferedReader.readLine();
                        A.setText(tmStr);
                        tmStr = bufferedReader.readLine();
                        B.setText(tmStr);
                        tmStr = bufferedReader.readLine();
                        C.setText(tmStr);
                        tmStr = bufferedReader.readLine();
                        D.setText(tmStr);
                        answer = bufferedReader.readLine();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
//            outputStreamWriter.close();
//            fileOutputStream.close();

            bufferedReader.close();//题库
//            ctinputStreamReader.close();
//            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
