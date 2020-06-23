package hanz.com.mymemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hanz.com.mymemo.DB.GetDataArrayList;
import hanz.com.mymemo.addmemo.AddActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView add_btn;
    private EditText search_data;
    private ListView memo_list;
    private List datas;

    private List<MainMemo> memoResList;

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
        search_data = (EditText) findViewById(R.id.serch_data);

//     获取数据
        datas =  new GetDataArrayList().getData(this);

        MainAdapter adapter = new MainAdapter(this, datas);
        memo_list.setAdapter(adapter);
//        添加功能
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转添加页面
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

//        search
        search_data.addTextChangedListener(new TextWatcher() {
//            输入文本之前的状态
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

//            输入文本中的状态
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

//            输入文本后的状态
            @Override
            public void afterTextChanged(Editable s) {
                memoResList = getSearchMemoByKey(s.toString());

                MainAdapter adapter = new MainAdapter(MainActivity.this, memoResList);
                memo_list.setAdapter(adapter);
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

    private ArrayList<MainMemo> getSearchMemoByKey(String key) {
//                Log.d("afterTextChanged: ", key);
        ArrayList<MainMemo> list = new ArrayList<MainMemo>();

        for (int i = 0; i < datas.size(); i ++) {
            MainMemo memo = (MainMemo) datas.get(i);
            String title = memo.getDataTitle();
            String content = memo.getDataContent();
            String time =  new MainAdapter().formatDate(memo.getDataCreateTime());

            if (time.contains(key) || title.contains(key) || content.contains(key)){
                list.add(memo);
            }
        }

        return list;
    }
}
