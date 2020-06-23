package hanz.com.mymemo;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainAdapter extends BaseAdapter {
    //数据集合
    private List<MainMemo> datas=null;
    private Context context=null;
    public MainAdapter(){}
    public MainAdapter(Context context,List<MainMemo> datas){
        //初始化数据
        this.context=context;
        this.datas=datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView =LayoutInflater.from(context).inflate(
                        R.layout.memo_item, null);
                //控件组装
               viewHolder.dataTitle = (TextView) convertView.findViewById(R.id.data_title);
                viewHolder.dataContent = (TextView)convertView.findViewById(R.id.data_content);
                viewHolder.dataCreateTime = (TextView)convertView.findViewById(R.id.data_create_time) ;
                //数据
                MainMemo mainMemo = datas.get(position);
                viewHolder.dataTitle.setText(mainMemo.getDataTitle());
                //处理长度
                String shortCut = mainMemo.getDataContent();
                if(shortCut.length()>10){
                    shortCut = shortCut.substring(0,10);
                }
                viewHolder.dataContent.setText(shortCut);
                //转换日期 如果日期是当天，则显示当天日期，否则显示2020/11/10或2020/06/06
                String dataStr = formatDate(mainMemo.getDataCreateTime());
                viewHolder.dataCreateTime.setText(dataStr);
                convertView.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

        return convertView ;
    }

    private String formatDate(Date date){
                        Log.d("ADAPTER", "date -- " + date);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataCreateTimeStr = dateFormat.format(date);
        String nowTime = dateFormat.format(
                new Date(
                        Calendar.getInstance().getTimeInMillis()
                )
        );

        if(dataCreateTimeStr.equals(nowTime)){
            return "今天";
        }else {
            return dataCreateTimeStr;
        }

    }

    private static class ViewHolder {

        private TextView dataTitle;
        private TextView dataContent;
        private TextView dataCreateTime;

    }
}
