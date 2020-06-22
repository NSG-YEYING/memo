package hanz.com.mymemo.addmemo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;

import hanz.com.mymemo.R;

public class AddActivity extends AppCompatActivity {
    private EditText data_title;
    private EditText data_content;
    private ImageButton complete_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_memo);

        init();
    }

    private void init() {
        data_title=(EditText)findViewById(R.id.data_title);
        data_content=(EditText)findViewById(R.id.data_content);
        complete_btn=(ImageButton)findViewById(R.id.complete_btn);
        data_content.setSelection(0);
    }
}