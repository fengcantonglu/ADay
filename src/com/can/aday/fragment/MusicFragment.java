package com.can.aday.fragment;

import com.can.aday.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MusicFragment extends Fragment {
	View mView;
	View titleLayout;
	TextView title;
	TextView musicName;
	TextView content;
	View likeBtn;
	View downloadBtn;
	View textContentLayout;
	ImageView contentImage;
	RadioGroup musicGroup;
	RadioButton[] musicBtn = new RadioButton[3];

	public MusicFragment(View titleLayout) {
		this.titleLayout = titleLayout;
		titleLayout.setBackgroundColor(Color.parseColor("#40000000"));
		title = (TextView) titleLayout.findViewById(R.id.title_center_text);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout lay = new LinearLayout(getActivity());
		lay.setLayoutParams(new LayoutParams(-1, -1));
		mView = inflater.inflate(R.layout.music_layout, lay);
		findView();
		initView();
		return mView;
	}

	private void findView() {
		musicName = (TextView) mView.findViewById(R.id.music_title);
		content = (TextView) mView.findViewById(R.id.content_text);
		likeBtn = mView.findViewById(R.id.like_btn);
		downloadBtn = mView.findViewById(R.id.download_btn);
		textContentLayout = mView.findViewById(R.id.text_content_layout);
		contentImage = (ImageView) mView.findViewById(R.id.content_image);
		musicGroup = (RadioGroup) mView.findViewById(R.id.music_group);
		musicBtn[0] = (RadioButton) mView.findViewById(R.id.music_story);
		musicBtn[1] = (RadioButton) mView.findViewById(R.id.music_lyric);
		musicBtn[2] = (RadioButton) mView.findViewById(R.id.music_details);
	}

	private void initView() {
		title.setText("Love Hurts");
	}

	@Override
	public void onDestroyView() {
		titleLayout.setBackgroundColor(0);
		title.setText("");
		super.onDestroyView();
	}
}
