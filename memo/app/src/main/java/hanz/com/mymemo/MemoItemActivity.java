package hanz.com.mymemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class MemoItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_item);

        init();
    }

    private EditText
                data_title,
                data_content;

    private TextView data_create_time;


    private void init() {

        data_title = (EditText) findViewById(R.id.data_title);
        data_content = (EditText) findViewById(R.id.data_content);
        data_create_time = (TextView) findViewById(R.id.data_create_time);

//      获取数据
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String time = intent.getStringExtra("time");

//       展示数据
        data_title.setText(title);
        data_content.setText(content);
        data_create_time.setText(time);

//       init style
        data_title.setSelection(title.length());
        data_content.setSelection(content.length());

    }
}