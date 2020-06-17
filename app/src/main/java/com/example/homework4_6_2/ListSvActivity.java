package com.example.homework4_6_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.homework4_6_2.database.DatabaseCreate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ListSvActivity extends AppCompatActivity{

    RecyclerView rcv_listSV;
    List<iteamModel> listSV;
    private SearchView searchView;
    private Toolbar toolbar;
    IteamAdapter adapter;
    final DatabaseCreate databaseCreate = new DatabaseCreate(this);
    private EditText edt_search;
    private Button btn_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sv);
        init();
        setEvent();

        listSV = new ArrayList<>();
        listSV = databaseCreate.getAllStudents();

        rcv_listSV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcv_listSV.setLayoutManager(layoutManager);

        adapter = new IteamAdapter(listSV);
        rcv_listSV.setAdapter(adapter);
        registerForContextMenu(rcv_listSV);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString();
                adapter.search(key);
            }
        });


    }
    private void init(){
        toolbar = findViewById(R.id.toolbar);
        rcv_listSV = findViewById(R.id.rcv_listSV);
        edt_search = findViewById(R.id.edt_search);
    }
    private void setEvent(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position;
        try {
           position = ((IteamAdapter) rcv_listSV.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d("TAG", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        int id = item.getItemId();
        switch (id) {
            case R.id.Delete:
                alert(id,position);
                return true;
            case R.id.Update:
                alert(id,position);
                return true;
            case R.id.search:
                return true;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu2, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
        return true;
    }

    public void alert(final int id , final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListSvActivity.this);
        final int id_student =  listSV.get(position).getId_student();
        AlertDialog dialog = builder.setTitle("Thông Báo")
                .setCancelable(true)
                .setMessage("Bạn có muốn tiếp tục ?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       if(id ==R.id.Delete){
                           databaseCreate.deleteStudent(id_student);
                           listSV.remove(position);
                           adapter.notifyItemRemoved(position);
                       }
                       else if (id == R.id.Update){
                           displayAlertDialog(listSV.get(position),id_student);
                        }
                    }
                })
                .create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void displayAlertDialog(iteamModel student, final int id_student) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog,null);

        final EditText mssv =  alertLayout.findViewById(R.id.edt_mssv);
        final EditText full_name =  alertLayout.findViewById(R.id.edt_fullname);
        final EditText dob = alertLayout.findViewById(R.id.edt_dob);
        final EditText birthplace = alertLayout.findViewById(R.id.edt_birthplace);
        final EditText email = alertLayout.findViewById(R.id.edt_email);

        mssv.setText(student.getMssv());
        full_name.setText(student.getFullname());
        dob.setText(student.getDob());
        birthplace.setText(student.getBirthplace());
        email.setText(student.getEmail());

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Sửa đổi thông tin");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Đã huỷ", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!mssv.getText().toString().equals("") && !full_name.getText().toString().equals("") && !dob.getText().toString().equals("") && !email.getText().toString().equals("") && !birthplace.getText().toString().equals("")){
                    Log.v("TAG",birthplace.getText().toString());
                    iteamModel iteam = new iteamModel(full_name.getText().toString(),mssv.getText().toString(),dob.getText().toString(),email.getText().toString(),birthplace.getText().toString());
                    databaseCreate.updateStudent(iteam,id_student);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ListSvActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ListSvActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

}
