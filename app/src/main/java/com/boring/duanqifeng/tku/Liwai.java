package com.boring.duanqifeng.tku;

import android.content.Context;
import android.content.Intent;
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

public class Liwai extends AppCompatActivity {

    Button submit;
    CheckBox A, B,C,D,E,F;
    List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
    TextView tmView;
    String answer,leibie;
    String tmStr,checkedText = "";
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
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_liwai);

            //获取父activity传来的数据
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            leibie = bundle.getString("leibie");

            A = (CheckBox) this.findViewById(R.id.A);
            B = (CheckBox) this.findViewById(R.id.B);
            C = (CheckBox) this.findViewById(R.id.C);
            D = (CheckBox) this.findViewById(R.id.D);
            E = (CheckBox) this.findViewById(R.id.E);
            F = (CheckBox) this.findViewById(R.id.F);

            //将所有的checkbox放到一个集合中
            checkBoxList.add(A);
            checkBoxList.add(B);
            checkBoxList.add(C);
            checkBoxList.add(D);
            checkBoxList.add(E);
            checkBoxList.add(F);

            submit = (Button) this.findViewById(R.id.submit);
            tmView = (TextView) this.findViewById(R.id.textView);

            //选择题库
            InputStream inputStream = getResources().openRawResource(R.raw.nw_lw);
            switch (leibie){
                case "neiwu":
                    inputStream = getResources().openRawResource(R.raw.nw_lw);
                    break;
                case "duilie":
                    inputStream = getResources().openRawResource(R.raw.nw_dx);
                    break;
                case "jilv":
                    inputStream = getResources().openRawResource(R.raw.nw_dx);
                    break;
                case "gjs":
                    inputStream = getResources().openRawResource(R.raw.nw_dx);
                    break;
                case "jsll":
                    inputStream = getResources().openRawResource(R.raw.nw_dx);
                    break;
                case "xljc":
                    inputStream = getResources().openRawResource(R.raw.nw_dx);
                    break;
                case "zzjc":
                    inputStream = getResources().openRawResource(R.raw.nw_dx);
                    break;
                case "jcjs":
                    inputStream = getResources().openRawResource(R.raw.nw_dx);
                    break;
                case "jbz":
                    inputStream = getResources().openRawResource(R.raw.jbz_lw);
                    break;
            }

            InputStreamReader inputStreamReader ;
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);



            read();//读取题目和选项及答案



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
                        Toast.makeText(Liwai.this, "请勿选择空选项", Toast.LENGTH_SHORT).show();
                    } else if (checkedText.equals(answer)) {
                        Toast.makeText(Liwai.this, "回答正确", Toast.LENGTH_SHORT).show();
                        read();
                    } else {
                        Toast.makeText(Liwai.this, "回答错误，\n正确答案："+answer, Toast.LENGTH_SHORT).show();

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
                                bufferedWriter.append(E.getText().toString()).append("\n");
                                bufferedWriter.append(F.getText().toString()).append("\n");
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private  void read(){
        try {

            A.setChecked(false);
            B.setChecked(false);
            C.setChecked(false);
            D.setChecked(false);
            E.setChecked(false);
            F.setChecked(false);
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
            tmStr = bufferedReader.readLine();
            E.setText(tmStr);
            tmStr = bufferedReader.readLine();
            F.setText(tmStr);
            answer = bufferedReader.readLine();
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
