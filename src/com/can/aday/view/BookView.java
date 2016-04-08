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
 * 普通book模块对象
 * 
 * @author kk0927
 *
 */
public class BookView {
	Context context;
	View mView;

	TextView content;
	TextView achorSay;
	View achorSayLayout;
	ImageView contentImage;

	@SuppressLint("InflateParams")
	public BookView(Context context) {
		this.context = context;
		mView = LayoutInflater.from(context).inflate(R.layout.book_item_layout, null);
		achorSay = (TextView) mView.findViewById(R.id.athor_say_text);
		content = (TextView) mView.findViewById(R.id.book_content_text);
		achorSayLayout = mView.findViewById(R.id.athor_say_layout);
		contentImage = (ImageView) mView.findViewById(R.id.book_content_image);
	}

	public void setData(String[] content, String authorSay, Bitmap contentImage) {

		if (content != null) {
			StringBuilder sb = new StringBuilder();
			for (String c : content) {
				sb.append("\t\t" + c + "\r\n");
			}
			this.content.setText(sb.toString());
		} else
			this.content.setVisibility(View.GONE);
		if (authorSay == null || authorSay.length() < 1) {
			achorSayLayout.setVisibility(View.GONE);
		} else {
			achorSay.setText("\t\t" + authorSay);
		}
		this.contentImage.setImageBitmap(contentImage);
	}

	public View getView() {
		return mView;
	}
}
