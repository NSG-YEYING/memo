package hanz.com.mymemo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import hanz.com.mymemo.MainAdapter;
import hanz.com.mymemo.MainMemo;

public class HandleData {

    private ArrayList<MainMemo> datas;

    private DBHelper dbHelper;
    private SQLiteDatabase db;


    private void initSQL(Context context) {
        dbHelper = new DBHelper(context, "mymemo", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public void addData(Context context, String title, String content) {
        initSQL(context);
        ContentValues values = new ContentValues();

//        标题内容都为空
        if (title.equals("") && content.equals("")) {
            Toast.makeText(context, "标题与内容为空", Toast.LENGTH_SHORT).show();

            return;
        }

        values.put(
                "data_title",
                title
        );
        values.put(
                "data_content",
                content
        );
        values.put(
                "data_create_time",
                Calendar.getInstance().getTimeInMillis()
        );

        db.insert("myTable", null, values);

        closeSQL();
    }

    public ArrayList getData(Context context) {
        initSQL(context);
        db = dbHelper.getReadableDatabase();

        datas = new ArrayList<>();
        Cursor cursor = db.query(
                "myTable",
                new String[]{"id", "data_title", "data_content", "data_create_time"},
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            MainMemo memo = new MainMemo();
//            title
            String title = cursor.getString(cursor.getColumnIndex("data_title"));
//           content
            String content = cursor.getString(cursor.getColumnIndex("data_content"));
//            create time
            long time = cursor.getLong(cursor.getColumnIndex("data_create_time"));
//          id
            int id = cursor.getInt(cursor.getColumnIndex("id"));

//           title
            if (title == null) {
                title = "";
            }
            memo.setDataTitle(title);

//          content
            if (content == null) {
                content = "";
            }
            memo.setDataContent(content);
//          time
            memo.setDataCreateTime(new Date(time));
//          id
            memo.setId(id);

            datas.add(memo);
        }

        cursor.close();

        Collections.reverse(datas);

        closeSQL();
        return  datas;
    }

    public ArrayList deleteData(Context context, int id) {
        initSQL(context);

        db.delete("myTable", "id=?", new String[]{String.valueOf(id)});

         ArrayList list = getData(context);
        closeSQL();

        return list;
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
        dbHelper = null;

        if (db != null || db.isOpen()) {
            db.close();
        }
    }

    public ArrayList<MainMemo> getSearchMemoByKey(String key, Context context) {
        ArrayList<MainMemo> list = new ArrayList<MainMemo>();
        ArrayList datas = getData(context);

        for (int i = 0; i < datas.size(); i++) {
            MainMemo memo = (MainMemo) datas.get(i);
            String title = memo.getDataTitle();
            String content = memo.getDataContent();
            String time = new MainAdapter().formatDate(memo.getDataCreateTime());

            if (time.contains(key) || title.contains(key) || content.contains(key)) {
                list.add(memo);
            }
        }

        return list;
    }
}
