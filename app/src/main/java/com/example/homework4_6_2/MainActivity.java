package com.example.homework4_6_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework4_6_2.database.DatabaseCreate;


public class MainActivity extends AppCompatActivity {

    EditText mssv,email,fullname,dob,birthPlace;
    Button btn_add,btn_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        final DatabaseCreate databaseCreate = new DatabaseCreate(this);

        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListSvActivity.class);
                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mssv.getText().toString().equals("") && !fullname.getText().toString().equals("") && !dob.getText().toString().equals("") && !email.getText().toString().equals("") && !birthPlace.getText().toString().equals("")) {
                   Log.v("TAG",mssv.getText().toString());
                    if(databaseCreate.checkStudent(mssv.getText().toString())) {
                        databaseCreate.addStudent(new iteamModel(fullname.getText().toString(), mssv.getText().toString(), dob.getText().toString(), email.getText().toString(), birthPlace.getText().toString()));
                        Toast.makeText(MainActivity.this, "Thêm thành công ", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Mssv này đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void init(){
        mssv = findViewById(R.id.edt_mssv);
        email = findViewById(R.id.edt_email);
        fullname = findViewById(R.id.edt_fullname);
        dob = findViewById(R.id.edt_dob);
        birthPlace = findViewById(R.id.edt_birthplace);
        btn_add = findViewById(R.id.btn_add);
        btn_display = findViewById(R.id.btn_displayListSV);
    }

}
