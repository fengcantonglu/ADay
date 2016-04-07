package com.can.aday.fragment;

import com.can.aday.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 王科
 *
 */
public class BookFragment extends Fragment {
	View mView;
	/**
	 * 正文图片
	 */
	View contentImage;
	/**
	 * 日期图片
	 */
	ImageView dateImage;
	/**
	 * 正文标题
	 */
	TextView artTitle;
	/**
	 * 文章作者
	 */
	TextView artAuthor;
	/**
	 * 文章内容
	 */
	TextView artContent;
	/**
	 * 阅读数(格式:阅读数\t2.1W)
	 */
	TextView readerCount;
	/**
	 * 点赞数
	 */
	TextView likeCount;
	/**
	 * 评论数
	 */
	TextView commentCount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout lay = new LinearLayout(getActivity());
		lay.setLayoutParams(new LayoutParams(-1, -1));
		mView = inflater.inflate(R.layout.book_layout, lay);
		findView();
		initView();
		loadData();
		return mView;
	}

	private void findView() {
		contentImage = mView.findViewById(R.id.book_content_image);
		dateImage = (ImageView) mView.findViewById(R.id.book_image_date);
		artTitle = (TextView) mView.findViewById(R.id.article_title);
		artAuthor = (TextView) mView.findViewById(R.id.article_athor);
		artContent = (TextView) mView.findViewById(R.id.article_content);
		readerCount = (TextView) mView.findViewById(R.id.reader_count);
		likeCount = (TextView) mView.findViewById(R.id.like_count);
		commentCount = (TextView) mView.findViewById(R.id.comment_count);
	}

	/**
	 * 点击绑定
	 */
	private void initView() {

	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		String content = "大师与我,位置各自不同。伯格曼宛如神灵黑泽明和阿巴斯是师长，小津则像纯真的母亲温和的仁慈永照心间；戈达尔和特吕弗如童年伙伴，布列松是那若即若离谱素庄重的情人。";
		String read = "2.1W";
		int like = 234;
		int com = 456;
		contentImage.setBackgroundResource(R.drawable.book_flower_image);
		dateImage.setImageResource(R.drawable.date_image);
		artTitle.setText("时间的观察者和雕刻者");
		artAuthor.setText("郑立");
		artContent.setText("\t\t" + content);
		readerCount.setText("阅读数 " + read);
		likeCount.setText("" + like);
		commentCount.setText("" + com);
	}
}
