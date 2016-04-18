package com.can.aday.view;

import java.io.IOException;
import java.util.Set;

import com.can.aday.tools.LRCInfo;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class LRCView extends View {
	private LRCInfo mLrc;
	private int currentTime;
	private int currentIndex;
	private MediaPlayer mPlayer;
	private SeekBar mSeekBar;
	private TextView currentTimeText;
	private TextView musicTimeText;
	private OnCompletionListener playComletion;
	private boolean isChanging;
	private float mX;
	private float mY;
	private float mMiddleY;
	/**
	 * 动画偏移过后的middleY;
	 */
	private float offedMiddleY;
	/**
	 * 移动过后的
	 */
	private float moveOffY;
	private float space;

	public LRCView(Context context) {
		super(context);
		p1.setTextSize(dip2px(13));
		p2.setTextSize(dip2px(16));
		p1.setColor(Color.WHITE);
		p2.setColor(Color.YELLOW);
		space = p1.getTextSize() * 2.3f;
	}

	public LRCView(Context context, AttributeSet attrs) {
		super(context, attrs);
		p1.setTextSize(dip2px(13));
		p2.setTextSize(dip2px(16));
		p1.setColor(Color.WHITE);
		p2.setColor(Color.YELLOW);
		space = p1.getTextSize() * 2.3f;
	}

	public LRCView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		p1.setTextSize(dip2px(13));
		p2.setTextSize(dip2px(16));
		p1.setColor(Color.WHITE);
		p2.setColor(Color.YELLOW);
		space = p1.getTextSize() * 2.3f;
	}

	@SuppressLint("NewApi")
	public LRCView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		p1.setTextSize(dip2px(13));
		p2.setTextSize(dip2px(16));
		p1.setColor(Color.WHITE);
		p2.setColor(Color.YELLOW);
		space = p1.getTextSize() * 2.3f;
	}

	// >>>>>>>>>>
	public void setLRC(LRCInfo lrc) {
		mLrc = lrc;
	}

	public float getTextSize() {
		return p1.getTextSize();
	}

	public void setTextSize(float textSize) {
		p1.setTextSize(textSize);

	}

	public float getCurrentTextSize() {
		return p2.getTextSize();
	}

	public void setCurrentTextSize(float currentTextSize) {
		p2.setTextSize(currentTextSize);
	}

	public int getTextColor() {
		return p1.getColor();
	}

	public void setTextColor(int textColor) {
		p1.setColor(textColor);
	}

	public int getCurrentLrcTextColor() {
		return p2.getColor();
	}

	public void setCurrentLrcTextColor(int currentTextColor) {
		p2.setColor(currentTextColor);
	}

	public float getSpace() {
		return space - p1.getTextSize();
	}

	public void setLineSpace(float space) {
		this.space = p1.getTextSize() * space;
	}

	Paint p1 = new Paint();
	Paint p2 = new Paint();

	protected void onDraw(Canvas canvas) {
		if (super.getVisibility() == View.GONE) {
			return;
		}
		super.onDraw(canvas);
		if (mLrc == null)
			return;
		Set<Integer> key = mLrc.getInfos().keySet();
		float _mY = offedMiddleY + moveOffY;
		drawOneLrc(currentTime, canvas, p2, _mY);
		int i = 0;
		int alpha = 0;
		float y;
		for (int k : key) {
			if (i < currentIndex) {
				y = _mY - (currentIndex - i) * space;
				if (y > space) {
					if (y > 1.5f * space) {
						p1.setAlpha(255);
					} else {
						p1.setAlpha(155);
					}
					drawOneLrc(k, canvas, p1, y);
				}

			} else if (i > currentIndex) {
				y = _mY + (i - currentIndex) * space;

				if (y <= mY) {
					float bt = mY - y;
					if (bt > 2.5f * space) {
						alpha = 255;
					} else if (bt > 1.2f * space) {
						alpha = 160;
					} else {
						alpha = 68;
					}
					p1.setAlpha(alpha);
					drawOneLrc(k, canvas, p1, y);
				}
			}
			i++;
		}
	}

	public MediaPlayer getPlayer() {
		return mPlayer;
	}

	public void setPlayMusic(String path)
			throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		if (mPlayer == null) {
			mPlayer = new MediaPlayer();
			mPlayer.setOnCompletionListener(playComletion);
			mPlayer.reset();
		}
		if (mPlayer.isPlaying()) {
			mPlayer.stop();
			mPlayer.reset();
		}
		mPlayer.setDataSource(path);
		mPlayer.prepare();
		if (musicTimeText != null)
			musicTimeText.setText(parseTimeToString(mPlayer.getDuration()));
	}

	public void setPlayMusic(Uri uri)
			throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		if (mPlayer == null) {
			mPlayer = new MediaPlayer();
			mPlayer.setOnCompletionListener(playComletion);
			mPlayer.reset();
		}
		if (mPlayer.isPlaying()) {
			mPlayer.stop();
			mPlayer.reset();
		}
		mPlayer.setDataSource(getContext(), uri);
		mPlayer.prepare();
		if (musicTimeText != null)
			musicTimeText.setText(parseTimeToString(mPlayer.getDuration()));

	}

	public void setLRCPath(String path) throws IOException {

		LRCInfo lrc = LRCInfo.loadLRCFile(path);
		setLRC(lrc);
	}

	private String getOneLrc(int time) {
		String c = mLrc.getInfos().get(time);
		if (c == null) {
			return "";
		} else {
			return c;
		}
	}

	private void drawOneLrc(int time, Canvas canvas, Paint p, float y) {
		String c = getOneLrc(time);
		float offx = p.measureText(c) / 2f;
		canvas.drawText(c, mX - offx, y, p);
	}

	ValueAnimator animation;
	private int nextTime = -1;
	private float animOff;
	private float lastOff;
	private boolean isCancelAnim;
	private AnimatorUpdateListener ainimationListener = new AnimatorUpdateListener() {

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			animOff = (Float) animation.getAnimatedValue();
			offedMiddleY = mMiddleY + animOff * (1 + lastOff) - lastOff * space;
			invalidate();
		}
	};
	private AnimatorListener aListener = new AnimatorListener() {

		public void onAnimationStart(Animator animation) {

		}

		public void onAnimationRepeat(Animator animation) {
		}

		public void onAnimationEnd(Animator anim) {
			animation = (ValueAnimator) anim;
			if (!isCancelAnim) {
				lastOff = 0;
			} else {
				isCancelAnim = false;
			}
		}

		public void onAnimationCancel(Animator anima) {

		}
	};

	public void setCurrentTime(int time) {
		if (mSeekBar != null && !isChanging)
			mSeekBar.setProgress(time);
		if (currentTimeText != null) {
			String t = parseTimeToString(time);
			if (!currentTimeText.getText().toString().equals(t)) {
				currentTimeText.setText(t);
			}
		}
		if (mLrc == null)
			return;
		Set<Integer> key = mLrc.getInfos().keySet();
		int ct = 0;
		int i = 0;
		int md = 0;
		for (int k : key) {
			if (k <= time) {
				ct = k;
				currentIndex = i;
			} else {
				nextTime = k;
				md = k - time;
				break;
			}
			i++;
		}
		if (currentTime != ct) {
			currentTime = ct;
			invalidate();
			if (mPlayer.isPlaying())
				animotion(md);
		}
	}

	Runnable mRun = new Runnable() {
		public void run() {
			mHandler.sendEmptyMessage(mPlayer.getCurrentPosition());
		}
	};
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (mPlayer.isPlaying()) {
				setCurrentTime(msg.what);
				mHandler.postDelayed(mRun, 10);
			}
		}
	};
	private OnSeekBarChangeListener l = new OnSeekBarChangeListener() {

		public void onStopTrackingTouch(SeekBar seekBar) {
			mPlayer.seekTo(seekBar.getProgress());
			if (!mPlayer.isPlaying()) {
				setCurrentTime(seekBar.getProgress());
			}
			isChanging = false;
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
			isChanging = true;
		}

		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

		}
	};

	public void play() {
		if (animation != null && nextTime - mPlayer.getCurrentPosition() > 0) {
			animotion(nextTime - mPlayer.getCurrentPosition());
		}
		mPlayer.start();
		if (mSeekBar != null)
			mSeekBar.setMax(mPlayer.getDuration());
		mHandler.postDelayed(mRun, 10);

	}

	public void pause() {
		mPlayer.pause();
		if (animation != null) {
			animation.cancel();
			isCancelAnim = true;
			lastOff = animOff / space;
		}
	}

	public void bindSeekBar(SeekBar seekBar) {
		mSeekBar = seekBar;
		mSeekBar.setOnSeekBarChangeListener(l);
	}

	private void animotion(int duration) {
		if (animation == null)
			animation = ValueAnimator.ofFloat(0, -space);

		if (animation.isRunning()) {
			animation.cancel();
		}
		animation.setDuration(duration);
		animation.addUpdateListener(ainimationListener);
		animation.addListener(aListener);
		animation.setInterpolator(new LinearInterpolator());
		animation.start();

	};

	/**
	 * 须在设置完MediaPlay后执行此方法
	 * 
	 * @param play
	 * @param currentT
	 * @param lengthT
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("NewApi")
	public void bindPlayBtnAndTimeText(View play, TextView currentT, TextView lengthT) {
		musicTimeText = lengthT;
		if (musicTimeText != null && mPlayer != null) {
			musicTimeText.setText(parseTimeToString(mPlayer.getDuration()));
		}
		currentTimeText = currentT;
		if (currentTimeText != null&& mPlayer != null) {
			currentTimeText.setText(parseTimeToString(mPlayer.getCurrentPosition()));
		}
	}

	@SuppressLint("DefaultLocale")
	private String parseTimeToString(int time) {
		int s = time / 1000;
		int m = s / 60;
		int ss = s % 60;
		String str = String.format("%02d:%02d", m, ss);
		return str;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		mX = w * 0.5f;
		mY = h;
		mMiddleY = h * 0.4f;
		offedMiddleY = mMiddleY;

	}

	/**
	 * 用户是否执行了翻页歌词
	 */
	private boolean isDoMove;

	/**
	 * 用户按下时的xy坐标
	 */
	private float onDownY;

	private float offY;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float off = event.getY() - onDownY;
			moveOffY = offY + off;
			invalidate();
			if (Math.abs(off) > space * 2) {
				isDoMove = true;
			}
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			offY = moveOffY;
			onDownY = event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (!isDoMove) {
				moveOffY = 0;
			} else {
				isDoMove = false;
				new Thread() {

					public void run() {
						try {
							sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (!isDoMove) {
							moveOffY = 0;
						}
					}
				}.start();
			}
		}
		super.onTouchEvent(event);
		return true;
	}

	public int dip2px(float spValue) {
		final float fontScale = getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 
	 * @param listener
	 */
	public void setOnCompletionListener(OnCompletionListener listener) {
		playComletion = listener;
		if (mPlayer != null) {
			mPlayer.setOnCompletionListener(playComletion);
			Log.i("music", "set listener");
		}
	}

}
