package hanz.com.mymemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

//        获取数据
       datas =  new GetDataArrayList().getData(this);

        MainAdapter adapter = new MainAdapter(this, datas);
        memo_datas.setAdapter(adapter);
        //添加功能
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转添加页面
                startActivity(new Intent(MainActivity.this, AddActivity.class));

            }
        });

        IntentFilter filter = new IntentFilter(AddActivity.action);
        registerReceiver(receiver, filter);

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            datas =  new GetDataArrayList().getData(MainActivity.this);

            MainAdapter adapter = new MainAdapter(MainActivity.this, datas);
            memo_datas.setAdapter(adapter);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }
}
