package com.example.attendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem> classItems = new ArrayList<>();
    Toolbar toolbar;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(v -> showDialog());

        loadData();
        
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        classAdapter = new ClassAdapter(this, classItems);
        recyclerView.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));
        setToolbar();
    }

    private void loadData() {
        Cursor cursor = dbHelper.getClassTable();

        classItems.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.C_ID));
            String className = cursor.getString(cursor.getColumnIndex(DBHelper.CLASS_NAME_KEY));
            String subjectName = cursor.getString(cursor.getColumnIndex(DBHelper.SUBJECT_NAME_KEY));

            classItems.add(new ClassItem(id,className,subjectName));
        }
    }

    private void setToolbar() {

    toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.title_toolbar);
        TextView subtitle = findViewById(R.id.subtitle_toolbar);
        ImageButton back = findViewById(R.id.back);
        ImageButton save = findViewById(R.id.save);

        title.setText("Attendance");
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra("className",classItems.get(position).getClassName());
        intent.putExtra("subjectName",classItems.get(position).getSubjectName());
        intent.putExtra("position",position);
        startActivity(intent);
    }

    private void showDialog(){
     MyDialog dialog = new MyDialog();
     dialog.show(getSupportFragmentManager(),MyDialog.CLASS_ADD_DIALOG);
     dialog.setListener((className,subjectName)->addClass(className,subjectName));
    }
    private void addClass(String className, String subjectName){
        long cid = dbHelper.addClass(className,subjectName);
        ClassItem classItem = new ClassItem(cid,className,subjectName);
        classItems.add(classItem);
        classAdapter.notifyDataSetChanged();
    }
}