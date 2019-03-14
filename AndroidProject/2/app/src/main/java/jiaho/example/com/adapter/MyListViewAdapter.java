package jiaho.example.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

import jiaho.example.com.R;
import jiaho.example.com.bean.WhatHappened;

public class MyListViewAdapter extends BaseAdapter {

    private Context context;
    private List<WhatHappened> dataList;

    public MyListViewAdapter(Context context, List<WhatHappened> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return null != dataList?dataList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //创建一个viewHolder
        MyViewHolder viewHolder = new MyViewHolder();
        //判断view是否为空
        if (view == null){
            view = View.inflate(context, R.layout.item,null);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.tv_time = view.findViewById(R.id.tv_time);
            viewHolder.imageView = view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        }else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        WhatHappened data = dataList.get(i);

        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_time.setText(data.getYear()+"-"+data.getMonth()+"-"+data.getDay());

        //一句话搞定图片加载
        Picasso.get().load(data.getImg()).into(viewHolder.imageView);

        return view;
    }

    public static class MyViewHolder{
        public TextView tv_title;
        public TextView tv_time;
        public ImageView imageView;
    }
}
