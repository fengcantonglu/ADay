package com.can.aday.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class Book {
	int id;

	String objectId;
	String title;
	/**
	 * 文章内容,包含插图和座右铭
	 */
	Stage[] content;
	/**
	 * 文章简介
	 */
	String introduction;
	/**
	 * 文章标题图
	 */
	String articleimg;

	/**
	 * 文章标题图本地路径
	 */
	String articleimg_local_path;

	/**
	 * 发布时间
	 */
	String createdAt;

	/**
	 * 修改时间
	 */
	String updatedAt;
	/**
	 * 作者
	 */
	String author;
	/**
	 * 作者生平
	 */
	String authordescrip;
	
	/**
	 * 
	 * 非音乐类型文章为null
	 */
	String music;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Stage[] getContent() {
		return content;
	}

	public void setContent(Stage[] content) {
		this.content = content;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getArticleimg() {
		return articleimg;
	}

	public void setArticleimg(String articleimg) {
		this.articleimg = articleimg;
	}

	public String getArticleimg_local_path() {
		return articleimg_local_path;
	}

	public void setArticleimg_local_path(String articleimg_local_path) {
		this.articleimg_local_path = articleimg_local_path;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthordescrip() {
		return authordescrip;
	}

	public void setAuthordescrip(String authordescrip) {
		this.authordescrip = authordescrip;
	}

	public class Stage {
		public String mContent;
		public String motto;
		public String img;
	}

	public static Book parseJSONObject(JSONObject jo) {
		Book book = null;
		try {
			int id = jo.getInt("id");		
			book = new Book();
			book.objectId=jo.getString("objectId");
			book.id = id;
		} catch (JSONException e) {
			return null;
		}
		try {
			book.title = jo.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			book.introduction = jo.getString("introduction");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			book.articleimg = jo.getJSONObject("articleimg").getString("url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			book.createdAt = jo.getString("createdAt");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			book.author = jo.getString("author");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			book.authordescrip = jo.getString("authordescrip");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			book.updatedAt = jo.getString("updatedAt");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			String artContent = jo.getString("content");
			Log.i("Book", "content:" + artContent);
			book.parseStringToStage(artContent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			book.music=jo.getJSONObject("music").getString("url");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return book;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 将拆分好的正文内容组合成字符串。
	 * 
	 * @return
	 */
	public String getContentToString() {
		if (content != null) {
			StringBuilder sb = new StringBuilder();
			for (Stage mSt : content) {
				if (mSt.mContent != null)
					sb.append(mSt.mContent);
				if (mSt.motto != null)
					sb.append("&lt;motto+:" + mSt.motto + "+/&gt;");
				if (mSt.img != null)
					sb.append("&lt;img+:" + mSt.img + "+/&gt;");
				sb.append("\r\n");
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	/**
	 * 将正文内容按段落拆分
	 * 
	 * @param artContent
	 */
	public void parseStringToStage(String artContent) {
		if (!TextUtils.isEmpty(artContent)) {
			String stageStr[] = artContent.split("[[\r\n][\n]]");
			Pattern patternMotto = Pattern.compile("&lt;motto+:.+/&gt;");
			Pattern patternImg = Pattern.compile("&lt;img+:.+/&gt;");
			content = new Stage[stageStr.length];
			int i = 0;
			for (String stages : stageStr) {

				Stage data = new Stage();
				data.mContent = stages;
				Matcher matcherMotto = patternMotto.matcher(stages);
				boolean bl = matcherMotto.find();
				Matcher matcherImg = patternImg.matcher(stages);
				if (bl) {
					String motto = matcherMotto.group();
					data.mContent = data.mContent.replace(motto, "");
					Matcher mBug = Pattern.compile("/&gt;&lt;img:.+").matcher(motto);
					if (mBug.find()) {
						String mImg = mBug.group();
						motto = motto.replace(mImg, "");
					}
					data.motto = motto.replace("&lt;motto:", "").replace("/&gt;", "");
				}

				boolean bl1 = matcherImg.find();
				if (bl1) {
					String img = matcherImg.group();
					data.mContent = data.mContent.replace(img, "");
					Matcher mBug = Pattern.compile("/&gt;&lt;motto:.+").matcher(img);
					if (mBug.find()) {
						String mMot = mBug.group();
						img = img.replace(mMot, "");
					}
					data.img = img.replace("&lt;img:", "").replace("/&gt;", "");
					;
				}

				content[i] = data;
				i++;
				Log.i("Book", "con:" + data.mContent);
				Log.i("Book", "img:" + data.img);
				Log.i("Book", "motto:" + data.motto);
				Log.i("Book", "hasMotto:" + bl + " hasImg:" + bl1 + " ");
			}
		}

	}
}
