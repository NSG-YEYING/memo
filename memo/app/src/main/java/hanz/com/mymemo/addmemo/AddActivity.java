package hanz.com.mymemo.addmemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

import hanz.com.mymemo.DB.DBHelper;
import hanz.com.mymemo.R;

public class AddActivity extends AppCompatActivity {

//    public static final String action = "mainActivity_action";

    private EditText data_title;
    private EditText data_content;
    private ImageButton complete_btn;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_memo);

        init();
    }

    private void init() {
        data_title = (EditText) findViewById(R.id.data_title);
        data_content = (EditText) findViewById(R.id.data_content);
        complete_btn = (ImageButton) findViewById(R.id.complete_btn);
//        光标首行
        data_content.setSelection(0);

        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMemo();
            }
        });
    }

    private void addMemo() {
        dbHelper = new DBHelper(this, "mymemo", null, 1);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String title = data_title.getText().toString().trim();
        String content = data_content.getText().toString().trim();

//        标题内容都为空
        if (title.equals("") && content.equals("")) {
            Toast.makeText(getApplicationContext(), "标题与内容为空", Toast.LENGTH_SHORT).show();

            return;
        }

        values.put(
                "data_title",
                title
        );
        values.put(
                "data_content",
                content
        );
        values.put(
                "data_create_time",
                Calendar.getInstance().getTimeInMillis()
        );

        db.insert("myTable", null, values);

        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();

        finish();
    }

}