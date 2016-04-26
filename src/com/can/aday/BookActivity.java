package com.can.aday;

import com.can.aday.data.Book.Stage;
import com.can.aday.tools.DensityUtil;
import com.can.aday.view.BookMusicView;
import com.can.aday.view.BookView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 读者界面,包括音乐读物,和杂志之类的
 * 
 * @author kk0927
 *
 */
public class BookActivity extends Activity {
	/**
	 * 返回按钮
	 */
	ImageView toReturn;
	/**
	 * 收藏按钮
	 */
	View collection;
	/**
	 * 评论按钮
	 */
	View comment;
	/**
	 * 分享按钮
	 */
	View share;
	/**
	 * 音乐播放时间
	 */
	TextView currutTime;
	/**
	 * 音乐时长
	 */
	TextView musicTime;
	/**
	 * 进度条
	 */
	View progress;
	/**
	 * 音乐模块进度条布局(普通杂志文章隐藏)
	 */
	View progressLayout;

	/**
	 * 播放暂停按钮(普通杂志隐藏此)
	 */
	ImageView playOrPause;

	/**
	 * 日期图片
	 */
	ImageView dateImage;
	/**
	 * 文章或音乐类杂志标题
	 */
	TextView artTitle;

	/**
	 * 作者
	 */
	TextView artAthor;
	/**
	 * 作者简介
	 */
	TextView athorDetail;
	/**
	 * 正文布局
	 */
	LinearLayout contentLayout;

	/**
	 * 喜欢图标
	 */
	View likeBtn;

	/**
	 * 喜欢板块布局
	 */
	LinearLayout likeLayout;

	/**
	 * 评论日期标签
	 */
	TextView commentDate;
	/**
	 * 查看更多评论
	 */
	View moreComment;

	/**
	 * 评论布局
	 */
	LinearLayout commentLayout;

	Intent intent;

	AdayApplication app;

	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.more_comment:
				intent.setClass(BookActivity.this, CommentActivity.class);
				startActivity(intent);
				break;
			case R.id.title_left:
				finish();
				break;
			default:
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);
		app = (AdayApplication) getApplication();
		intent = getIntent();
		initTitleView();
		findView();
		initView();
		loadData();

	}

	/**
	 * 初始化标题栏
	 */
	@SuppressLint("InflateParams")
	private void initTitleView() {
		toReturn = (ImageView) findViewById(R.id.title_left);
		View v = getLayoutInflater().inflate(R.layout.function_icon_layout, null);
		findViewById(R.id.title_right_btn).setVisibility(View.GONE);
		LinearLayout tl = (LinearLayout) findViewById(R.id.title_right_layout);
		tl.addView(v);
		toReturn.setImageResource(R.drawable.return_icon);
		collection = v.findViewById(R.id.function_collection);
		comment = v.findViewById(R.id.function_comment);
		share = v.findViewById(R.id.function_share);
	}

	private void findView() {
		currutTime = (TextView) findViewById(R.id.currut_time);
		musicTime = (TextView) findViewById(R.id.music_time_length);
		progress = findViewById(R.id.progress_image);
		progressLayout = findViewById(R.id.progress_layout);
		playOrPause = (ImageView) findViewById(R.id.play_or_pause);
		dateImage = (ImageView) findViewById(R.id.date_image);
		artTitle = (TextView) findViewById(R.id.arc_title);
		artAthor = (TextView) findViewById(R.id.article_athor);
		athorDetail = (TextView) findViewById(R.id.athor_details);
		contentLayout = (LinearLayout) findViewById(R.id.article_centent_layout);
		likeBtn = findViewById(R.id.like_btn);
		likeLayout = (LinearLayout) findViewById(R.id.like_layout);
		commentDate = (TextView) findViewById(R.id.comment_date);
		moreComment = findViewById(R.id.more_comment);
		commentLayout = (LinearLayout) findViewById(R.id.comment_layout);
	}

	private void initView() {
		moreComment.setOnClickListener(click);
		toReturn.setOnClickListener(click);

	}

	private void loadData() {
		artTitle.setText(app.currentBook.getTitle());
		artAthor.setText(app.currentBook.getAuthor());
		athorDetail.setText("\t\t" + app.currentBook.getAuthordescrip());

		// >>>>>>>
		loadBookLikeData();
		loadBookData();
		setCommentData();
	}

	/**
	 * 普通读物杂志加载模块
	 */
	public void loadBookData() {
		progressLayout.setVisibility(View.GONE);
		playOrPause.setVisibility(View.GONE);
		BookView bv;

		for (Stage mS : app.currentBook.getContent()) {
			bv = new BookView(this);
			if (mS.img != null && mS.img.startsWith("http")) {
				bv.setData(new String[] { mS.mContent }, mS.motto, mS.img);
			} else if (mS.img != null) {
				bv.setData(new String[] { mS.mContent }, mS.motto, AdayApplication.SERVICE_BOOK + mS.img);
			} else {
				bv.setData(new String[] { mS.mContent }, mS.motto, mS.img);
			}
			contentLayout.addView(bv.getView(),-2,-2);
		}

		/*
		 * bv = new BookView(this); bv.setData( new String[] {
		 * "好女人的爱情字典里容不得沙子，所以好女人的爱情世界里一般不会和金钱权势搭上关系。许多的“好”男人以为凭金钱和权势就能打动好女人的芳心，结果总是事与愿违，因为好女人最反感最看不起这种世俗的铜臭味，好女人认为他们简直就是弱智；好女人清高、淡泊、如平静的西湖，渴望杨柳在平湖里有一个深情的摇曳，她马上就会泛起朵朵的涟漪；好女人的爱情是需要患难与共的感觉，好女人相信贫穷的爱情与艰涩的眼泪，好女人渴望有一位勇士敢跳进自己平湖里溅一声清脆，这是她苦苦等了一万年才迎来的邂逅……"
		 * }, "在对待女人的态度上，好男人通常表现的清高，清高的男人总是射不中春天里的美。",
		 * BitmapFactory.decodeResource(getResources(),
		 * R.drawable.content_image_1)); contentLayout.addView(bv.getView()); bv
		 * = new BookView(this); bv.setData( new String[] {
		 * "好女人的爱情字典里容不得沙子，所以好女人的爱情世界里一般不会和金钱权势搭上关系。许多的“好”男人以为凭金钱和权势就能打动好女人的芳心，结果总是事与愿违，因为好女人最反感最看不起这种世俗的铜臭味，好女人认为他们简直就是弱智；好女人清高、淡泊、如平静的西湖，渴望杨柳在平湖里有一个深情的摇曳，她马上就会泛起朵朵的涟漪；好女人的爱情是需要患难与共的感觉，好女人相信贫穷的爱情与艰涩的眼泪，好女人渴望有一位勇士敢跳进自己平湖里溅一声清脆，这是她苦苦等了一万年才迎来的邂逅……"
		 * }, null, BitmapFactory.decodeResource(getResources(),
		 * R.drawable.content_image_2)); contentLayout.addView(bv.getView());
		 * 
		 * bv = new BookView(this); bv.setData( new String[] {
		 * "好女人认为他们简直就是弱智；好女人清高、淡泊、如平静的西湖，渴望杨柳在平湖里有一个深情的摇曳，她马上就会泛起朵朵的涟漪；好女人的爱情是需要患难与共的感觉，好女人相信贫穷的爱情与艰涩的眼泪，好女人渴望有一位勇士敢跳进自己平湖里溅一声清脆，这是她苦苦等了一万年才迎来的邂逅……"
		 * }, null, (String) null);
		 */

	}

	/**
	 * 加载点赞数据
	 */
	public void loadBookLikeData() {
		LayoutParams params = new LayoutParams(DensityUtil.dip2px(this, 45), DensityUtil.dip2px(this, 45));
		params.setMargins(0, 0, DensityUtil.dip2px(this, 11.5f), 0);

		ImageView v = new ImageView(this);
		v.setImageResource(R.drawable.user_header_1);
		likeLayout.addView(v, params);
		v = new ImageView(this);
		v.setImageResource(R.drawable.user_header_2);
		likeLayout.addView(v, params);
		v = new ImageView(this);
		v.setImageResource(R.drawable.user_header_3);
		likeLayout.addView(v, params);
		v = new ImageView(this);
		v.setImageResource(R.drawable.user_header_4);
		likeLayout.addView(v, params);
		v = new ImageView(this);
		v.setImageResource(R.drawable.user_header_5);
		likeLayout.addView(v, params);
	}

	/**
	 * 音乐杂志的加载模块
	 */
	public void loadBookMusicData() {
		progressLayout.setVisibility(View.VISIBLE);
		playOrPause.setVisibility(View.VISIBLE);
		BookMusicView cv = new BookMusicView(this);
		cv.setData("选取音乐:大幻想取《诺玛的回忆》节选", null, null);
		contentLayout.addView(cv.getView());
		cv = new BookMusicView(this);
		cv.setData("内容介绍:",
				new String[] {
						"2002年9月28日周杰伦于台北市立体育场举办《THEONE演唱会》，创下了台湾演唱会近十年的优质纪录。[33]  2003年7月16日，全亚洲超过五十家电台同步首播新专辑《叶惠美》中的主打歌曲《以父之名》，据统计有八亿人收听，因此将每年的7月16日定为“周杰伦日”。[34]  同年因《东风破》而被华语音乐传媒大赏评为最佳作曲人。[35]  同年，荣登美国《时代》周刊亚洲版封面人物”[3]  。周杰伦是继王菲、张惠妹之后，第3位出现在《时代》杂志封面的华人歌手。",
						"2004年周杰伦以《龙拳》亮相春晚[37]  ；2005年6月23日主演处女作《头文字D》上映，同年获得香港电影金像奖最佳新人奖；[38-39]  2007年11月24日周杰伦在上海八万人体育场开唱。" },
				BitmapFactory.decodeResource(getResources(), R.drawable.book_music_cotent_image));
		contentLayout.addView(cv.getView());
		cv = new BookMusicView(this);
		cv.setData("内容截取:",
				new String[] {
						"2009年7月，周杰伦澳洲巡演，悉尼演唱会票房空降美国公告牌（Billboard）第二，力压碧昂斯，成为当年全球单场演唱会票房收入第二的好成绩，打破了华人在澳洲开唱的票房纪录；[50]  2009年12月，美国CNN评选\"亚洲最具影响力的25位人物”，周杰伦入选，并被CNN网站形容为“非凡艺人”。" },
				null);
		contentLayout.addView(cv.getView());
	}

	/**
	 * 添加评论数据
	 */
	@SuppressLint("InflateParams")
	public void setCommentData() {
		for (int i = 0; i < 3; i++) {
			View view = getLayoutInflater().inflate(R.layout.book_comment_layout, null);
			commentLayout.addView(view);
			ImageView v = (ImageView) view.findViewById(R.id.user_head_image);
			TextView name = (TextView) view.findViewById(R.id.user_name);
			TextView likeCount = (TextView) view.findViewById(R.id.like_count);
			TextView comTime = (TextView) view.findViewById(R.id.com_time);
			TextView content = (TextView) view.findViewById(R.id.com_content);
			if (i == 0)
				v.setImageResource(R.drawable.head_image);
			else if (i == 1)
				v.setImageResource(R.drawable.comment_comuser_header);
			else
				v.setImageResource(R.drawable.comment_user_header_3);
			name.setText("大傻哥");
			likeCount.setText("" + 134 + i);
			comTime.setText("3分钟前");
			content.setText("一栏，就可以找到Windows保存的用户凭据了。单击小箭头就可以展开或合起用户凭据信息。");

		}

	}

}
