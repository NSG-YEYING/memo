package hanz.com.mymemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import hanz.com.mymemo.DB.GetDataArrayList;
import hanz.com.mymemo.addmemo.AddActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView add_btn;

    private ListView memo_list;
    private List datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        memo_list = (ListView)findViewById(R.id.memo_list);
        add_btn = (ImageView)findViewById(R.id.add_btn) ;

//     获取数据
        datas =  new GetDataArrayList().getData(this);

        MainAdapter adapter = new MainAdapter(this, datas);
        memo_list.setAdapter(adapter);
        //添加功能
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转添加页面
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

//        list view item click
        memo_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MemoItemActivity.class);
//              获取点击的 item 信息
                MainMemo memo = (MainMemo) datas.get((int) id);
//                title
                intent.putExtra("title", memo.getDataTitle());
//              content
                intent.putExtra("content", memo.getDataContent());
//              time
                String time = new MainAdapter().formatDate(memo.getDataCreateTime());
                intent.putExtra("time", time);

                startActivity(intent);
            }
        });
    }

}
