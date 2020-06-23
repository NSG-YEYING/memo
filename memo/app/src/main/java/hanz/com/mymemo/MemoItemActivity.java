package hanz.com.mymemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import hanz.com.mymemo.DB.HandleData;

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
    private ImageButton save_btn;

    private void init() {

        data_title = (EditText) findViewById(R.id.data_title);
        data_content = (EditText) findViewById(R.id.data_content);
        data_create_time = (TextView) findViewById(R.id.data_create_time);
        save_btn = (ImageButton) findViewById(R.id.save_btn);

//      获取数据
        Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        final String content = intent.getStringExtra("content");
        String time = intent.getStringExtra("time");
        final int table_id = intent.getIntExtra("table_id", 0);

//       展示数据
        data_title.setText(title);
        data_content.setText(content);
        data_create_time.setText(time);

//       init style
        data_title.setSelection(title.length());
        data_content.setSelection(content.length());

//       更改数据
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = data_title.getText().toString().trim();
                String newContent = data_content.getText().toString().trim();

//                标题 内容同时为空
                if (newTitle.equals("") && newContent.equals("")) {
                    Toast.makeText(getApplicationContext(), "标题与内容为空", Toast.LENGTH_SHORT).show();

                    return;
                }

//                标题、内容 都未改变
                if (!newTitle.equals(title) && !newContent.equals(content)) {
                    finish();
                    return;
                }

                new HandleData().update(MemoItemActivity.this, newTitle, newContent, table_id);

                Toast.makeText(getApplicationContext(), "信息已更新", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }

}