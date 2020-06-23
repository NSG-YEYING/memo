package hanz.com.mymemo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hanz.com.mymemo.MainAdapter;
import hanz.com.mymemo.MainMemo;

public class GetDataArrayList {

    private List<MainMemo> datas;

    private DBHelper dbHelper;
    private SQLiteDatabase db;


    private void initSQL(Context context) {
        dbHelper = new DBHelper(context, "mymemo", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public List getData(Context context) {
        initSQL(context);

        datas = new ArrayList<>();
        Cursor cursor = db.query("myTable", null,null,null,null,null,null);
        while (cursor.moveToNext()){
            MainMemo memo = new MainMemo();
//            title
            String content = cursor.getString( cursor.getColumnIndex("data_content"));
//           content
            String title = cursor.getString(cursor.getColumnIndex("data_title"));
//            create time
            long time = cursor.getLong(cursor.getColumnIndex("data_create_time"));
//          id
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            memo.setId(id);

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

        closeSQL();
        return datas;
    }

    public void deleteData(Context context, int id){
        initSQL(context);

        db.delete("myTable", "id=?", new String[]{String.valueOf(id)});
    }

    public void update(Context context, String newTitle, String newContent, int id) {
        initSQL(context);

        ContentValues values = new ContentValues();

        values.put("data_title", newTitle);
        values.put("data_content", newContent);
        values.put("data_create_time", Calendar.getInstance().getTimeInMillis());

//                根据 id 更新数据
        db.update("myTable", values, "id=?", new String[]{String.valueOf(id)});

        closeSQL();
    }

    private void closeSQL() {
        if (db != null || db.isOpen()){
            db.close();
        }
    }

    public ArrayList<MainMemo> getSearchMemoByKey(String key) {
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
