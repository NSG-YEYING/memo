package hanz.com.mymemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import hanz.com.mymemo.DB.HandleData;
import hanz.com.mymemo.addmemo.AddActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView add_btn;
    private EditText search_data;
    private ListView memo_list;

    private List datas;
    private HandleData handleData;
    private CharSequence temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        memo_list = (ListView) findViewById(R.id.memo_list);
        add_btn = (ImageView) findViewById(R.id.add_btn);
        search_data = (EditText) findViewById(R.id.serch_data);
        search_data.setText("");

        handleData = new HandleData();

//     获取数据
        datas = handleData.getData(this);

        MainAdapter adapter = new MainAdapter(this, datas);
        memo_list.setAdapter(adapter);

//      添加功能
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
                Log.d("afterTextChanged: ", String.valueOf(s));

                datas = handleData.getSearchMemoByKey( String.valueOf(s), MainActivity.this);

                MainAdapter adapter = new MainAdapter(MainActivity.this, datas);
                memo_list.setAdapter(adapter);
                memo_list.invalidate();
            }
        });

//        list item click
        memo_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MemoItemActivity.class);
//              获取点击的 item 信息
                MainMemo memo = (MainMemo) datas.get(position);
//                title
                intent.putExtra("title", memo.getDataTitle());
//              content
                intent.putExtra("content", memo.getDataContent());
//              time
                String time = new MainAdapter().formatDate(memo.getDataCreateTime());
                intent.putExtra("time", time);
//                table id
                intent.putExtra("table_id", memo.getId());

                startActivity(intent);
            }
        });

//        list item long click
        memo_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
//              获取点击的 item 信息
                MainMemo memo = (MainMemo) datas.get(position);
                final int table_id = memo.getId();
//                dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("注意");
                dialog.setMessage("确定删除此条记录？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       datas = handleData.deleteData(MainActivity.this, table_id);
                       memo_list.setAdapter(new MainAdapter(MainActivity.this, datas));

                        Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                        memo_list.invalidate();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();

                return true;
            }
        });
    }


}
