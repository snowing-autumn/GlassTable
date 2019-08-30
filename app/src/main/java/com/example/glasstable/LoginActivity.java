package com.example.glasstable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    ImageView codeImag;
    EditText userName;
    EditText userPasswd;
    EditText loginCode;
    Bitmap code;
    Button loginOk;
    String inputedName;
    String inputetPasswd;
    String inputedCode;
    ArrayList<Course> tempCourseList;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:codeImag.setImageBitmap(code);
                    break;
                case 2:
                    Intent intent=new Intent();
                    intent.putExtra("courseList",tempCourseList);
                    setResult(0,intent);
                    finish();
            }}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        codeImag=(ImageView)findViewById(R.id.codeImage);
        userName=(EditText)findViewById(R.id.userName);
        userPasswd=(EditText)findViewById(R.id.userPassed);
        loginCode=(EditText)findViewById(R.id.loginCode);
        loginOk=(Button)findViewById(R.id.LoginOk);

        loginOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputedName=userName.getText().toString();
                inputetPasswd=userPasswd.getText().toString();
                inputedCode=loginCode.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tempCourseList=Internet.getCourseList(inputedName,inputetPasswd,inputedCode);
                        Message ms=new Message();
                        ms.what=2;
                        handler.sendMessage(ms);
                    }
                }).start();

            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String FilePath = getFilesDir().getAbsolutePath();
                    code=Internet.getCode(FilePath + "//"+"code.jpeg");
                    Message ms=new Message();
                    ms.what=1;
                    handler.sendMessage(ms);
                }catch (IOException e){
                    Log.e("","",e);
                }
            }
        }).start();



    }
}
