package com.can.aday.view;

import com.can.aday.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 音乐杂志模块对象
 * 
 * @author kk0927
 *
 */
public class BookMusicView {
	Context context;
	View mView;
	TextView title;
	TextView content;
	ImageView contentImage;

	@SuppressLint("InflateParams")
	public BookMusicView(Context context) {
		this.context = context;
		mView = LayoutInflater.from(context).inflate(R.layout.book_music_content_layout, null);
		title = (TextView) mView.findViewById(R.id.content_title);
		content = (TextView) mView.findViewById(R.id.content_text);
		contentImage = (ImageView) mView.findViewById(R.id.content_image);
	}

	public void setData(String title, String[] content, Bitmap contentImage) {

		if (title != null)
			this.title.setText(title);
		if (content != null) {
			StringBuilder sb = new StringBuilder();
			for (String c : content) {
				sb.append("\t\t" + c + "\r\n");
			}
			this.content.setText(sb.toString());
		} else
			this.content.setVisibility(View.GONE);

		this.contentImage.setImageBitmap(contentImage);
	}

	public View getView() {
		return mView;
	}
}
