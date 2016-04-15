package com.can.aday.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类用来解析LRC文件 将解析完整的LRC文件放入一个LrcInfo对象中 并且返回这个LrcInfo对象s author:java_mzd
 */
public class LRCParser {

	/**
	 * 根据文件路径，读取文件，返回一个输入流
	 * 
	 * @param path
	 *            路径
	 * @return 输入流
	 * @throws FileNotFoundException
	 */
	private InputStream readLrcFile(String path) throws FileNotFoundException {
		File f = new File(path);
		InputStream ins = new FileInputStream(f);

		return ins;
	}

	public LRCInfo parser(String path) throws IOException {
		InputStream in = readLrcFile(path);
		LRCInfo lrcinfo = new LRCInfo();
		parser(in, lrcinfo);
		return lrcinfo;

	}

	/**
	 * 将输入流中的信息解析，返回一个LrcInfo对象
	 * 
	 * @param inputStream
	 *            输入流
	 * @return 解析好的LrcInfo对象
	 * @throws IOException
	 */
	private LRCInfo parser(InputStream inputStream, LRCInfo lrcinfo) throws IOException {
		// 三层包装
		InputStreamReader inr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inr);
		// 一行一行的读，每读一行，解析一行
		String line = null;
		while ((line = reader.readLine()) != null) {
			parserLine(line, lrcinfo);
		}

		return lrcinfo;
	}

	/**
	 * 利用正则表达式解析每行具体语句 并在解析完该语句后，将解析出来的信息设置在LrcInfo对象中
	 * 
	 * @param str
	 */
	private void parserLine(String str, LRCInfo lrcinfo) {
		// 取得歌曲名信息
		if (str.startsWith("[ti:")) {
			String title = str.substring(4, str.length() - 1);
			System.out.println("title--->" + title);
			lrcinfo.setTitle(title);

		} // 取得歌手信息
		else if (str.startsWith("[ar:")) {
			String singer = str.substring(4, str.length() - 1);
			System.out.println("singer--->" + singer);
			lrcinfo.setSinger(singer);

		} // 取得专辑信息
		else if (str.startsWith("[al:")) {
			String album = str.substring(4, str.length() - 1);
			System.out.println("album--->" + album);
			lrcinfo.setAlbum(album);

		} // 通过正则取得每句歌词信息
		else {
			// 设置正则规则
			String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2,})\\]";
			// 编译
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			int currentTime = -1;
			String currentContent = "";
			// 如果存在匹配项，则执行以下操作
			while (matcher.find()) {
				// 得到这个匹配项中的组数
				int groupCount = matcher.groupCount();

				// 得到每个组中内容
				for (int i = 0; i <= groupCount; i++) {
					String timeStr = matcher.group(i);
					if (i == 1) {
						// 将第二组中的内容设置为当前的一个时间点
						currentTime = strToLong(timeStr);
					}
				}

				// 得到时间点后的内容
				String[] content = pattern.split(str);

				// 输出数组内容
				for (int i = 0; i < content.length; i++) {
					if (i == content.length - 1) {
						// 将内容设置为当前内容
						currentContent = content[i];
					}
				}
				// 设置时间点和内容的映射
				lrcinfo.getInfos().put(currentTime, currentContent);
				System.out.println("put---currentTime--->" + currentTime + "----currentContent---->" + currentContent);
			}
		}
	}

	/**
	 * 将解析得到的表示时间的字符转化为Long型
	 * 
	 * @param group
	 *            字符形式的时间点
	 * @return int 形式的毫秒时间
	 */
	private int strToLong(String timeStr) {
		// 因为给如的字符串的时间格式为XX:XX.XX,返回的long要求是以毫秒为单位
		// 1:使用：分割 2：使用.分割
		String[] s = timeStr.split(":");
		int min = Integer.parseInt(s[0]);
		float sec = Float.parseFloat(s[1]);
		return (int) (min * 60 * 1000 + sec * 1000);
	}
}
