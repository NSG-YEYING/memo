package hanz.com.mymemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import hanz.com.mymemo.DB.GetDataArrayList;
import hanz.com.mymemo.addmemo.AddActivity;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    private EditText serch_data;
    private ImageView add_btn;

    private ListView memo_datas;
    private List datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView)findViewById(R.id.title);
        serch_data = (EditText)findViewById(R.id.serch_data);
        memo_datas = (ListView)findViewById(R.id.memo_datas);
        add_btn = (ImageView)findViewById(R.id.add_btn) ;

        //测试数据
//        getData();
       datas =  new GetDataArrayList().getData(this);

        MainAdapter adapter = new MainAdapter(this,datas);
        memo_datas.setAdapter(adapter);
        //添加功能
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转添加页面
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

}
