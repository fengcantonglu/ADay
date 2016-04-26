package com.can.aday.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 宽铺满控件，高度按照缩放比例
 * 
 * @author kk0927
 *
 */
public class MatchWidthImageView extends ImageView {
	public MatchWidthImageView(Context context) {
		super(context);

	}

	public MatchWidthImageView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public MatchWidthImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	@SuppressLint("NewApi")
	public MatchWidthImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		BitmapDrawable bd = (BitmapDrawable) getDrawable();

		Bitmap bit;
		if (bd != null && (bit = bd.getBitmap()) != null) {
			int bw = bit.getWidth();
			int bh = bit.getHeight();
			float s = w / (float) bw;
			h = (int) (bh * s);
			getLayoutParams().width = w;
			getLayoutParams().height = h;
			super.onSizeChanged(w, h, oldw, oldh);
		} else
			super.onSizeChanged(w, h, oldw, oldh);
	}
}
