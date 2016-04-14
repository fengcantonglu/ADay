package com.can.aday.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class LRCTools {

	private List<String> mTexts = new ArrayList<String>();
	private List<Integer> mTime = new ArrayList<Integer>();

	public void readLRC(String uri) {

		File file = new File(uri);
		try {
			// 获取输入流
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String s = "";
			while ((s = bufferedReader.readLine()) != null) {
				addTimeToList(s);
				if ((s.indexOf("[ar:") != -1) || (s.indexOf("[ti:") != -1) || (s.indexOf("[by:") != -1)) {
					s = s.substring(s.indexOf(":") + 1, s.indexOf("]"));
				} else {
					String ss = s.substring(s.indexOf("["), s.indexOf("]") + 1);
					s = s.replace(ss, "");
				}
				mTexts.add(s);
			}
			bufferedReader.close();
			inputStreamReader.close();
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将时间从文本中提出
	public int timeTransformation(String string) {
		// 将时间中的点替换为冒号
		string = string.replace(".", ":");
		string = string.replace("]", "");
		String timeData[] = string.split(":");

		// 分离出时分秒并转化为整型
		int minute = Integer.parseInt(timeData[0]);
		int second = Integer.parseInt(timeData[1]);
		Log.i("timeData", timeData[2] + "");
		int millisecond = Integer.parseInt(timeData[2]);
		// 计算上一行与下一行的时间
		int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;
		return currentTime;
	}

	public List<String> getTexts() {
		return mTexts;
	}

	public List<Integer> getTime() {
		return mTime;
	}

	private void addTimeToList(String s) {
		Matcher matcher = Pattern.compile("\\[\\d{1,2}:\\d{1,2}([\\.:]\\d{1,2})?\\]").matcher(s);
		if (matcher.find()) {
			String str = matcher.group();
			mTime.add(timeTransformation(str.substring(1, str.length())));
		}
	}
}
