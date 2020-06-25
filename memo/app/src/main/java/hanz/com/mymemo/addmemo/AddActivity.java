package hanz.com.mymemo.addmemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import hanz.com.mymemo.DB.HandleData;
import hanz.com.mymemo.R;

public class AddActivity extends AppCompatActivity {

    private EditText data_title;
    private EditText data_content;
    private ImageButton complete_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        boolean nightTheme = getIntent().getBooleanExtra("nightTheme", false);
        setTheme( nightTheme ? R.style.AppTheme_NIGHT : R.style.AppTheme) ;

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

        String title = data_title.getText().toString().trim();
        String content = data_content.getText().toString().trim();

        new HandleData().addData(this, title, content);

        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();

        finish();
    }

}