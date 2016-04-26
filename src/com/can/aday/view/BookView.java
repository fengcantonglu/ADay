package com.can.aday.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.can.aday.R;
import com.can.aday.utils.CacheTools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.text.TextUtils;
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

	/**
	 * 
	 * @param content
	 * @param authorSay
	 * @param url
	 *            {@value=null}}
	 */
	public void setData(String[] content, String authorSay, final String url) {

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
		if (!TextUtils.isEmpty(url)) {
			new Thread() {
				public void run() {
					final File file = new File(
							CacheTools.getImageFile() + File.separator + CacheTools.MD5(url) + ".png");
					if (file.exists()) {
						final Bitmap bit = BitmapFactory.decodeFile(file.getPath());
						contentImage.setImageBitmap(bit);
					} else {
						final URL u;
						try {
							u = new URL(url);
							try {
								InputStream in = u.openStream();
								FileOutputStream fos = new FileOutputStream(file);
								int len;
								byte buf[] = new byte[1024];
								while ((len = in.read(buf)) != -1) {
									fos.write(buf, 0, len);
								}
								fos.flush();
								fos.close();
								in.close();
								Bitmap bit = BitmapFactory.decodeFile(file.getPath());
								Looper.prepare();
								contentImage.setImageBitmap(bit);
								Looper.loop();
							} catch (IOException e) {
								e.printStackTrace();
							}

						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
					;
				}
			}.start();

		} else
			contentImage.setVisibility(View.GONE);

	}

	
	public View getView() {
		return mView;
	}
}
