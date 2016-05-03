package com.can.aday.fragment;

import java.io.File;
import java.io.IOException;

import com.can.aday.AdayApplication;
import com.can.aday.R;
import com.can.aday.data.Music;
import com.can.aday.data.Music.OnCacheMusicListener;
import com.can.aday.utils.CacheTools;
import com.can.aday.utils.FastBlur;
import com.can.aday.view.LRCView;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicFragment extends AdayFragment {
	View mView;
	/**
	 * 标题布局
	 */
	View titleLayout;
	/**
	 * 主显示区布局（覆盖标题布局）
	 */
	View mainLayout;
	/**
	 * 功能区布局（播放暂停区域）
	 */
	View functionLayout;
	/**
	 * 板块标题
	 */
	TextView title;
	/**
	 * 音乐标题
	 */
	TextView musicName;
	/**
	 * 第一段正文(边距窄与正文)
	 */
	TextView content_One;
	/**
	 * 正文,歌词显示或者详情
	 */
	TextView content;
	/**
	 * 点赞
	 */
	View likeBtn;
	/**
	 * 下载
	 */
	View downloadBtn;
	/**
	 * 正文布局
	 */
	View textContentLayout;
	/**
	 * 正文图片，与正文布局不能同时显示
	 */
	ImageView contentImage;
	/**
	 * 正文内容显示切换的radioGroup
	 */
	RadioGroup musicGroup;
	RadioButton[] musicBtn = new RadioButton[3];
	RadioButton musicDefBtn;
	/**
	 * 播放音乐的当前时间（格式:01:33）
	 */
	TextView currentTime;
	/**
	 * 播放音乐的长度时间（格式:04:33）
	 */
	TextView musicTime;

	/**
	 * 播放音乐的进度条
	 */
	SeekBar musicProgres;
	/**
	 * 播放与暂停
	 */
	ImageView playOrPause;
	/**
	 * 上一曲播放
	 */
	View backPlay;
	/**
	 * 下一曲播放
	 */
	View nextPlay;

	AdayApplication app;

	private int whitchPager;

	private OnCheckedChangeListener change = new OnCheckedChangeListener() {

		@Override
		@SuppressWarnings("deprecation")
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			whitchPager = 0;
			for (int i = 0; i < musicBtn.length; i++) {
				if (musicBtn[i].isChecked()) {
					musicBtn[i].setTextColor(getResources().getColor(R.color.main_text_color));
					whitchPager += (i + 1);
				} else {
					musicBtn[i].setTextColor(getResources().getColor(R.color.music_other_text_color));
				}
			}
			showWhichContent(whitchPager);
		}
	};
	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.play_btn:// 播放与暂停
				playOrPause();
				break;
			case R.id.back_btn:// 播放上一曲
				playOnSong();
				break;
			case R.id.next_btn:// 播放下一曲
				playDownSong();
				break;
			default:
				break;
			}

		}
	};
	/**
	 * 音乐播放完成的监听
	 */
	private OnCompletionListener listener = new OnCompletionListener() {

		public void onCompletion(MediaPlayer mp) {
			playOrPause.setImageResource(R.drawable.music_play_icon_in);
		}
	};

	public MusicFragment(View titleLayout) {
		this.titleLayout = titleLayout;
		titleLayout.setBackgroundColor(Color.parseColor("#40000000"));
		title = (TextView) titleLayout.findViewById(R.id.title_center_text);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout lay = new LinearLayout(getActivity());
		app = (AdayApplication) getActivity().getApplication();
		lay.setLayoutParams(new LayoutParams(-1, -1));
		mView = inflater.inflate(R.layout.music_layout, lay);
		findView();
		initView();
		loadData();
		return mView;
	}

	private void findView() {
		musicName = (TextView) mView.findViewById(R.id.music_title);
		content_One = (TextView) mView.findViewById(R.id.content_text_frist);
		content = (TextView) mView.findViewById(R.id.content_text);
		mContentScroll = (ScrollView) mView.findViewById(R.id.contentScroll);
		likeBtn = mView.findViewById(R.id.like_btn);
		downloadBtn = mView.findViewById(R.id.download_btn);
		mainLayout = mView.findViewById(R.id.main_layout);
		functionLayout = mView.findViewById(R.id.function_layout);
		textContentLayout = mView.findViewById(R.id.text_content_layout);
		contentImage = (ImageView) mView.findViewById(R.id.content_image);
		musicGroup = (RadioGroup) mView.findViewById(R.id.music_group);
		musicBtn[0] = (RadioButton) mView.findViewById(R.id.music_story);
		musicBtn[1] = (RadioButton) mView.findViewById(R.id.music_lyric);
		musicBtn[2] = (RadioButton) mView.findViewById(R.id.music_details);
		musicDefBtn = (RadioButton) mView.findViewById(R.id.music_default);
		currentTime = (TextView) mView.findViewById(R.id.currut_time);
		musicTime = (TextView) mView.findViewById(R.id.music_time_length);
		musicProgres = (SeekBar) mView.findViewById(R.id.progress_image);
		playOrPause = (ImageView) mView.findViewById(R.id.play_btn);
		backPlay = mView.findViewById(R.id.back_btn);
		nextPlay = mView.findViewById(R.id.next_btn);
		lrcView = (LRCView) mView.findViewById(R.id.music_lrc);
	}

	private void initView() {
		musicGroup.setOnCheckedChangeListener(change);
		playOrPause.setOnClickListener(click);
		backPlay.setOnClickListener(click);
		nextPlay.setOnClickListener(click);
		initLrcView();
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据板块
	String[] story;
	String summary;// 简介
	String[] lrc;
	String[] details;
	private int lastPosition;

	ScrollView mContentScroll;

	/**
	 * 加载数据
	 */
	private void loadData() {
		// 背景图
		Bitmap bkg = BitmapFactory.decodeResource(getResources(), R.drawable.music_backgroud_image);
		setBackGroud(bkg);
		musicName.setText(app.currentMusic.getSinger());

		/*
		 * story = new String[] {
		 * "神话是人类最早的幻想性口头散文作品， 是人类历史发展童年时期的产物，文学的先河。神话产生的基础是远古时代生产力水平低下和人们为争取生存、提高生产能力而产生的认识自然、支配自然的积极要求。"
		 * ,
		 * "神话产生于人类远古时代。作为民间文学的源头之一，有力的证明了劳动人民从来就是精神文明的创造者，也揭示了民间文学从一开始就与人民的生活和历史有着密切的联系。",
		 * "神话作为民间文学的一种形式，是远古时代的人民所创造的反映自然界、人与自然的关系以及社会形态的具有高度幻想性的故事。" };
		 * summary =
		 * "神话所反映的是原始人对客观世界的认识，是一种反映现实的观念形态，是产生在一定经济基础之上的上层建筑。只是由于神话反映客观世界是通过人类童年期自发的、幼稚的幻想的折光，因而呈现出独特的形态。";
		 * 
		 * 
		 * details = new String[] {
		 * "有的人虽然家财万贯，可是在茫茫人海中却没有遇到自己真正深爱的女孩，因此单纯以钱来评价一个人是否幸福是错误的。一个男孩，在茫茫人海中幸运的遇到了真正深爱的女孩，和这个女孩彼此相爱、彼此依恋、彼此关心、彼此知心、一起生活，快乐的、健康的相守到百岁，这才是真正的幸福。",
		 * "有一个心理测试：如果你深爱一个女孩，假如有两个选择，第一个选择：此生无缘遇到这个女孩，但是可以有百亿元的家产，第二个选择：此生能在茫茫人海中遇到这个深爱的女孩，但是一辈子只能和她过普通的生活，你会怎样选择？",
		 * "生活中需要思考：和什么人，在什么环境，用什么生活用具，过什么生活，有什么情感。幸福这种情感需要幸福的生活作为支撑，而幸福的生活也需要三个支撑：和喜欢的人，在喜欢的环境中，拥有喜欢的生活用具。情感、人、环境、生活用具、生活这五个基本方面需要协调，这五个方面之间的不协调（缺少支撑）是造成不幸的重要原因。"
		 * };
		 * 
		 */
		story = app.currentMusic.getStory().split("\r\n");

		Log.i("Story", "length=" + story.length);
		summary = app.currentMusic.getIntroduction();

		details = app.currentMusic.getIntroduce().split("\r\n");
		showWhichContent(whitchPager);
		cacheMusic();
	}

	@SuppressLint("NewApi")
	private void contentLoad(int i) {
		if (lastPosition != 0) {
			showContentAnimoution(i);
		} else {
			ObjectAnimator oa1 = ObjectAnimator.ofFloat(mContentScroll, "alpha", 0.3f, 1f);
			oa1.setDuration(100);
			switch (i) {
			case 1:// 显示故事
				showStory();
				break;
			case 2:// 显示歌词
				showLrc();
				break;
			case 3:// 显示详情
				showDetail();
				break;
			}
			AnimatorSet as = new AnimatorSet();
			as.playTogether(oa1);
			as.start();

		}
		lastPosition = i;
	}

	@SuppressLint("NewApi")
	private void showContentAnimoution(final int i) {
		ObjectAnimator oa = ObjectAnimator.ofFloat(mContentScroll, "alpha", 1f, 0f);
		ObjectAnimator oa1 = ObjectAnimator.ofFloat(mContentScroll, "alpha", 0f, 1f);
		oa1.setStartDelay(150);
		oa1.setDuration(300);
		oa.setDuration(150);

		oa.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				switch (i) {
				case 1:// 显示故事

					showStory();
					break;
				case 2:// 显示歌词
					showLrc();
					break;
				case 3:// 显示详情
					showDetail();
					break;
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		AnimatorSet as = new AnimatorSet();
		as.playTogether(oa, oa1);
		as.start();
	}

	/**
	 * 显示故事内容
	 */
	private void showStory() {
		StringBuilder sb = new StringBuilder();
		try {
			content_One.setText("\t\t" + summary + "\r\n");
			for (String s : story) {
				sb.append("\t\t" + s + "\r\n\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mContentScroll.fullScroll(View.FOCUS_UP);
		content.setText(sb.toString());
		lrcView.setVisibility(View.GONE);
		mContentScroll.setVisibility(View.VISIBLE);
	}

	LRCView lrcView;

	/**
	 * 显示歌词
	 */
	private void showLrc() {
		/*
		 * StringBuilder sb = new StringBuilder(); try {
		 * content_One.setText("\t\t" + summary + "\r\n"); for (String s : lrc)
		 * { sb.append("\t\t" + s + "\r\n"); } } catch (Exception e) {
		 * e.printStackTrace(); }
		 * mContentScroll.fullScroll(ScrollView.FOCUS_UP);
		 * content.setText(sb.toString());
		 */
		mContentScroll.setVisibility(View.GONE);
		lrcView.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示详情数据
	 */
	private void showDetail() {
		StringBuilder sb = new StringBuilder();
		try {
			content_One.setText("");
			for (String s : details) {
				sb.append("\t\t" + s + "\r\n\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mContentScroll.fullScroll(View.FOCUS_UP);
		content.setText(sb.toString());
		lrcView.setVisibility(View.GONE);
		mContentScroll.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示某个板块,0-中间原图显示,1-故事板块,2-歌词板块,3-详情板块
	 * 
	 * @param i
	 */
	@SuppressWarnings("deprecation")
	public void showWhichContent(int i) {
		if (i == 0) {
			contentImage.setVisibility(View.VISIBLE);
			textContentLayout.setVisibility(View.GONE);
			mainLayout.setBackgroundDrawable(new BitmapDrawable(mainBlugBkg));
			Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.image_scale);
			contentImage.startAnimation(anim);
			lastPosition = 0;
		} else {
			contentImage.setVisibility(View.GONE);
			textContentLayout.setVisibility(View.VISIBLE);
			mainLayout.setBackgroundDrawable(new BitmapDrawable(mainBkg));
			contentLoad(i);
		}
	}

	private Bitmap mainBkg;
	private Bitmap mainBlugBkg;
	private Bitmap bottomBkg;

	@SuppressWarnings("deprecation")
	private void setBackGroud(Bitmap bkg) {
		int w = bkg.getWidth();
		int mBh = (int) (bkg.getHeight() * 3.4f / 4.4f);
		int fBh = bkg.getHeight() - mBh;
		mainBkg = Bitmap.createBitmap(bkg, 0, 0, w, mBh);
		Bitmap fBkg = Bitmap.createBitmap(bkg, 0, mBh, w, fBh);
		bottomBkg = FastBlur.doBlur(fBkg, 24, false);
		mainBlugBkg = FastBlur.doBlur(mainBkg, 18, false);
		fBkg.recycle();
		bkg.recycle();
		if (musicDefBtn.isChecked()) {
			mainLayout.setBackgroundDrawable(new BitmapDrawable(mainBlugBkg));
		} else {
			mainLayout.setBackgroundDrawable(new BitmapDrawable(mainBkg));
		}

		functionLayout.setBackgroundDrawable(new BitmapDrawable(bottomBkg));
	}

	@Override
	public void onShow() {
		title.setText(app.currentMusic.getMusicname());
	}

	@Override
	public void onDismiss() {
		title.setText("");
		titleLayout.setBackgroundColor(0);
	}

	@Override
	public boolean finish() {
		if (musicDefBtn.isChecked()) {
			return true;
		} else {
			musicDefBtn.setChecked(true);
			return false;
		}
	}

	/**
	 * 用于上下一曲计数
	 */
	int musicCount;

	/**
	 * 播放上一曲
	 */
	public void playOnSong() {
		boolean isPlaying = false;
		if (lrcView.getPlayer() != null) {
			isPlaying = lrcView.getPlayer().isPlaying();
		}
		Music m = CacheTools.getLocalMusicData(getActivity(), ++musicCount);
		if (m != null) {
			app.currentMusic = m;
			loadData();
			prepareNewMusic();
			if (isPlaying) {
				play();
			}

		} else {
			--musicCount;
		}
		Log.i("PlayOnSong", "go to on Song" + m + " " + musicCount);
	}

	/**
	 * 播放下一曲
	 */
	public void playDownSong() {
		boolean isPlaying = false;
		if (lrcView.getPlayer() != null) {
			isPlaying = lrcView.getPlayer().isPlaying();
		}
		int i = --musicCount;

		if (i >= 0) {
			Music m = CacheTools.getLocalMusicData(getActivity(), i);
			app.currentMusic = m;
			loadData();
			prepareNewMusic();
			if (isPlaying) {
				play();
			}
		} else {
			musicCount = 0;
		}
		Log.i("PlayDownSong", "go to down Song  " + musicCount);
	}

	/**
	 * 播放与暂停
	 */
	public void playOrPause() {
		if (lrcView.getPlayer() == null) {
			start();
			Log.i("Music", "start");
		} else if (lrcView.getPlayer().isPlaying()) {
			pause();
			Log.i("Music", "pause");
		} else if (lrcView.getPlayer().getDuration() > 0) {
			play();
			Log.i("Music", "play");
		} else {
			start();
			Log.i("Music", "start2");
		}
	}

	public void start() {
		prepareNewMusic();
		play();
	}

	public void play() {
		lrcView.play();
		if (lrcView.getPlayer().isPlaying()) {
			playOrPause.setImageResource(R.drawable.music_pause_icon);
		}
	}

	public void pause() {
		lrcView.pause();
		if (!lrcView.getPlayer().isPlaying()) {
			playOrPause.setImageResource(R.drawable.music_play_icon_in);
		}
	}

	/**
	 * 缓存音乐
	 */
	public void cacheMusic() {
		OnCacheMusicListener cacheListener = new OnCacheMusicListener() {

			public void musicDownloadEnd(String path, boolean isDown) {
				if (isDown) {
					Music data = new Music();
					data.setId(app.currentMusic.getId());
					data.setMusicLocalPath(path);
					CacheTools.updateMusicData(getActivity(), data);
				}

			}

			public void musicCanRead(String path) {

			}

			public void lrcDownloadEnd(String path, boolean isDown) {
				if (isDown) {
					Music data = new Music();
					data.setId(app.currentMusic.getId());
					data.setSongWordsLocalPath(path);
					CacheTools.updateMusicData(getActivity(), data);
				}
				try {
					lrcView.setLRCPath(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		app.currentMusic.cacheMusicAndLrc(cacheListener);
	}

	/**
	 * lrcView绑定相关控件
	 */
	private void initLrcView() {
		lrcView.setOnCompletionListener(listener);
		lrcView.bindPlayBtnAndTimeText(playOrPause, currentTime, musicTime);
		lrcView.bindSeekBar(musicProgres);
	}

	/**
	 * 准备播放新的音乐
	 */
	private void prepareNewMusic() {
		try {
			if (app.currentMusic.getMusicLocalPath() == null
					|| !new File(app.currentMusic.getMusicLocalPath()).exists()) {
				String uri;
				if (app.currentMusic.getMusicpath().startsWith("http")) {
					uri = app.currentMusic.getMusicpath();
				} else {
					uri = AdayApplication.SERVICE_IP + app.currentMusic.getMusicpath();
				}
				Log.i("MusicUri", uri);
				lrcView.setPlayMusic(Uri.parse(uri));

			} else {
				lrcView.setPlayMusic(app.currentMusic.getMusicLocalPath());

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
