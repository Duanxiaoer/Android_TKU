package com.boring.duanqifeng.tku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Random;

public class Panduan extends AppCompatActivity {

    Button submit,saveJD,clearJD,suiJi;
    RadioGroup radioGroup;
    RadioButton F, T,CheckedBtn;
    int tiShu;
    TextView tmView;
    String answer,leibie,Ti;
    String tmStr,checkedText;
    Boolean isReadFromT = true,isSuiJi = false;
    Random random = new Random();
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
            SharedPreferences sharedPreferences = getSharedPreferences("当前题pd",MODE_PRIVATE);
            SharedPreferences 类别题目数 = getSharedPreferences("TMS",MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            Ti = sharedPreferences.getString("t","");//获取上一次的进度

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_panduan);

            //获取父activity传来的数据
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            leibie = bundle.getString("leibie");

            radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            F = (RadioButton) this.findViewById(R.id.F);
            T = (RadioButton) this.findViewById(R.id.T);
            submit = (Button) this.findViewById(R.id.submit);
            saveJD = (Button) this.findViewById(R.id.saveJD);
            clearJD = (Button) this.findViewById(R.id.clearJD);
            suiJi = (Button) this.findViewById(R.id.suiJi);
            tmView = (TextView) this.findViewById(R.id.textView);

            //选择题库
            InputStream inputStream = getResources().openRawResource(R.raw.nw_pd);
            switch (leibie){
                case "neiwu":
                    inputStream = getResources().openRawResource(R.raw.nw_pd);
                    tiShu = 类别题目数.getInt("nw_pd",50);
                    break;
                case "duilie":
                    inputStream = getResources().openRawResource(R.raw.dl_pd);
                    tiShu = 类别题目数.getInt("dl_pd",50);
                    break;
                case "jilv":
                    inputStream = getResources().openRawResource(R.raw.jl_pd);
                    tiShu = 类别题目数.getInt("jl_pd",50);
                    break;
                case "gjs":
                    inputStream = getResources().openRawResource(R.raw.gjs_pd);
                    tiShu = 类别题目数.getInt("gjs_pd",50);
                    break;
                case "jsll":
                    inputStream = getResources().openRawResource(R.raw.jsll_pd);
                    tiShu = 类别题目数.getInt("jsll_pd",50);
                    break;
                case "xljc":
                    inputStream = getResources().openRawResource(R.raw.xljc_pd);
                    tiShu = 类别题目数.getInt("xljc_pd",50);
                    break;
                case "zzjc":
                    inputStream = getResources().openRawResource(R.raw.zzjc_pd);
                    tiShu = 类别题目数.getInt("zzjc_pd",50);
                    break;
                case "jcjs":
                    inputStream = getResources().openRawResource(R.raw.jcjs_pd);
                    tiShu = 类别题目数.getInt("jcjs_pd",50);
                    break;
                case "jbz":
                    inputStream = getResources().openRawResource(R.raw.jbz_pd);
                    tiShu = 类别题目数.getInt("jbz_pd",50);
                    break;
            }

            InputStreamReader inputStreamReader ;
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            //获得选中的radio的值
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    selectRadioBtn();
                }
            });

            read(tiShu);//读取题目和选项及答案



            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkedText.toCharArray().length == 0) {
                        Toast.makeText(Panduan.this, "请勿选择空选项", Toast.LENGTH_SHORT).show();
                    } else if (checkedText.toCharArray()[0] == answer.toCharArray()[0]) {
                        Toast.makeText(Panduan.this, "回答正确", Toast.LENGTH_SHORT).show();
                        read(tiShu);
                    } else {
                        Toast.makeText(Panduan.this, "回答错误，\n正确答案："+answer, Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(Panduan.this,"保存成功",Toast.LENGTH_SHORT).show();
                }
            });
            //            清除进度 
            clearJD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString("t","");
                    editor.apply();
                    Toast.makeText(Panduan.this,"清除成功",Toast.LENGTH_SHORT).show();
                }
            });
            suiJi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSuiJi = !isSuiJi;
                    if (isSuiJi){
                        Toast.makeText(Panduan.this,"目前为随机出题",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Panduan.this,"目前为顺序出题",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectRadioBtn(){
        CheckedBtn = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        checkedText = CheckedBtn.getText().toString();
        if (checkedText.equals("T")){
            checkedText = "√";
        }else if (checkedText.equals("F")){
            checkedText = "×";
        }
    }

    private  void read(int tishu){
        try {
            if(isSuiJi){//随机出题
                int i=0;
                int t=random.nextInt(tishu-1);
                while(i<t*2){//产生0~题目数-1之间的随机数，然后乘以每道题目的行数
                    bufferedReader.readLine();
                    i++;
                }
                //从随机确定的题目后读取一道显示
                T.setChecked(false);
                F.setChecked(false);

                tmStr = bufferedReader.readLine();
                tmView.setText(tmStr);

                answer = bufferedReader.readLine();
                if (answer.contains("√")){
                    answer = "√";
                }else{
                    answer = "×";
                }
                bufferedReader.reset();
           }else {
               if (Ti.equals("")){//之前没有进度，从第一道开始 
                   bufferedReader.mark(100000);//与上面的reset()配合使用，以便每次从第一题随机出题
                   T.setChecked(false);
                   F.setChecked(false);
                   tmStr = bufferedReader.readLine();
                   tmView.setText(tmStr);

                   answer = bufferedReader.readLine();
                   if (answer.contains("√")){
                       answer = "√";
                   }else{
                       answer = "×";
                   }
               }else {//之前有进度
                   if (isReadFromT){//是否是中断后的第一次读
                       String temp;
                       do{//一直找到上次的最后一道题，才开始做
                           temp = bufferedReader.readLine();
                       }while (!Ti.equals(temp));
                       T.setChecked(false);
                       F.setChecked(false);
                       tmStr = temp;
                       tmView.setText(tmStr);

                       answer = bufferedReader.readLine();
                       if (answer.contains("√")){
                           answer = "√";
                       }else{
                           answer = "×";
                       }
                       isReadFromT = false;
                   }else{
                       T.setChecked(false);
                       F.setChecked(false);
                       tmStr = bufferedReader.readLine();
                       tmView.setText(tmStr);

                       answer = bufferedReader.readLine();
                       if (answer.contains("√")){
                           answer = "√";
                       }else{
                           answer = "×";
                       }
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
