package hanz.com.mymemo.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hanz.com.mymemo.MainMemo;

public class GetDataArrayList {

    private List<MainMemo> datas;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public List getData(Context context) {

        datas = new ArrayList<>();

        dbHelper = new DBHelper(context, "mymemo", null, 1);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("myTable", null,null,null,null,null,null);
        while (cursor.moveToNext()){
            MainMemo memo = new MainMemo();
//            title
            String content = cursor.getString( cursor.getColumnIndex("data_content"));
//           content
            String title = cursor.getString(cursor.getColumnIndex("data_title"));
//            create time
            long time = cursor.getLong(cursor.getColumnIndex("data_create_time"));

//           title
            memo.setDataTitle(title);
            if (content == null){
                content = "";
            }
//          content
            memo.setDataContent(content);
//          time
            memo.setDataCreateTime(new Date(time));

            datas.add(memo);
        }

        cursor.close();

        Collections.reverse(datas);

        return datas;
    }

}
