package hanz.com.mymemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hanz.com.mymemo.addmemo.AddActivity;

public class MainActivity extends AppCompatActivity {
    private TextView title;
    private EditText serch_data;
    private ListView memo_datas;
    private List<MainMemo> datas;
    private ImageView add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title =(TextView)findViewById(R.id.title);
        serch_data=(EditText)findViewById(R.id.serch_data);
        memo_datas=(ListView)findViewById(R.id.memo_datas);
        add_btn =(ImageView)findViewById(R.id.add_btn) ;

        //测试数据
        datas=new ArrayList<>();
        for (int i=0;i<20;i++){
            MainMemo memo=new MainMemo();
            memo.setDataContent("备忘数据内容"+i);
            //当天日期测试
            memo.setDataCreateTime(new Date(Calendar.getInstance().getTimeInMillis()));
            //昨天日期测试
          //  Calendar c=Calendar.getInstance();
          //  c.set(2020,5,18);
          //  memo.setDataCreateTime(new Date(c.getTimeInMillis()));
        //    memo.setDataTitle("备忘标题"+i);
            datas.add(memo);
        }
        MainAdapter adapter =new MainAdapter(this,datas);
        memo_datas.setAdapter(adapter);
        //添加功能
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转添加页面
                Intent i=new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);

            }
        });
    }
}
