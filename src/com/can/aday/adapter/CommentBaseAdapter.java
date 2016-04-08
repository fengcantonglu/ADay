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

public class CommentBaseAdapter extends BaseAdapter {
	ArrayList<String> list;
	Context context;
	LayoutInflater inflater;
	
	public CommentBaseAdapter() {
	}
	public CommentBaseAdapter(ArrayList<String> list,Context context) {
		this.list=list;
		this.context=context;		
		inflater=LayoutInflater.from(context);
	
	}
	public void setData(ArrayList<String> list){
		this.list=list;
		notifyDataSetChanged();
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
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.comment_layout, null);
			holder=new ViewHolder();
			holder.headImage=(ImageView)convertView.findViewById(R.id.comment_comuser_header);
			holder.name=(TextView)convertView.findViewById(R.id.comment_name);
			holder.time=(TextView)convertView.findViewById(R.id.comment_time);
			holder.commentContext=(TextView)convertView.findViewById(R.id.comment_context);
			holder.reply=(TextView)convertView.findViewById(R.id.comment_reply);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		return convertView;
	}
	class ViewHolder{
		ImageView headImage;
		TextView name;
		TextView time;
		TextView commentContext;
		TextView reply;
	}
}
