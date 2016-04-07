package com.can.aday.adapter;

import java.util.ArrayList;

import com.can.aday.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoClassifyDetailsBaseAdapter extends BaseAdapter {
    ArrayList< String> list;
    LayoutInflater inflater;
    Context context;
    public void setData(ArrayList< String> list){
    	this.list=list;
    	notifyDataSetChanged();
    }
	public VideoClassifyDetailsBaseAdapter() {
		
	}
	public VideoClassifyDetailsBaseAdapter(ArrayList< String> list,Context context) {
		this.list=list;
		this.context=context;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.video_classify_details_layout,null);
			holder.backimage=(ImageView)convertView.findViewById(R.id.video_classify_details_image);
			holder.playimage=(ImageView)convertView.findViewById(R.id.video_classify_details_player);
			holder.name=(TextView)convertView.findViewById(R.id.video_classify_details_name);
			holder.explain=(TextView)convertView.findViewById(R.id.video_classify_details_context);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.name.setText(list.get(position));
		return convertView;
	}
	class ViewHolder{
		ImageView backimage;//背景图片
		ImageView playimage;//播放按钮
		TextView name;//名字
		TextView explain;//解释
	}

}
