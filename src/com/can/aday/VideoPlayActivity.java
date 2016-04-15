package com.can.aday;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * 播放视频的界面
 * 
 * @author Administrator
 *
 */
public class VideoPlayActivity extends Activity {
	ImageView backImage;// 返回按钮
	VideoView playVideo;// 播放视频的控件
	LinearLayout videoLinear;
	FrameLayout videoFm;
	boolean isFm = true;// 为了FrameLayout两次点击

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/**
		 * super.onCreate(savedInstanceState);
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);(无title)
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);(全屏)
		 * 在这里要强调一点，设置全屏的代码必须放在setContentView(R.layout.main)之前，不然会报错
		 * android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
		 * 在这里我还想说明一下，用第一种方法在我们的应用运行后，会看到短暂的状态栏，然后才全屏，而第二种方法是不会有这种情况的，
		 * 所以我建议大家使用第二种方法。
		 */
		setContentView(R.layout.myvideo_play_mian);
		findView();
		playerVideo();
	}

	/**
	 * 控件ID
	 */
	private void findView() {
		backImage = (ImageView) findViewById(R.id.myvideo_play_back);
		playVideo = (VideoView) findViewById(R.id.myvideo_play_videoview);
		videoFm = (FrameLayout) findViewById(R.id.myvideo_play_frameLayout);
		videoLinear = (LinearLayout) findViewById(R.id.myvideo_play_linear);

		backImage.setOnClickListener(clickListener);
		videoFm.setOnClickListener(clickListener);
	}

	/**
	 * 点击事件
	 */
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myvideo_play_back:
				finish();
				break;
			case R.id.myvideo_play_frameLayout:
				if (isFm) {
					videoLinear.setVisibility(View.VISIBLE);
					isFm = false;
				} else {
					videoLinear.setVisibility(View.GONE);
					isFm = true;
				}
				break;
			default:
				break;
			}

		}
	};

	/**
	 * 播放视频
	 */
	private void playerVideo() {
		Uri uri = Uri.parse("http://192.168.15.222/Public/image/VTS.mp4");
		playVideo.setMediaController(new MediaController(this));
		playVideo.setVideoURI(uri);
		playVideo.start();
		playVideo.requestFocus();
	}
}
