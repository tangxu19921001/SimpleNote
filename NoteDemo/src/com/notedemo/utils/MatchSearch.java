package com.notedemo.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

public class MatchSearch {

	public static boolean containsPinyin(String name, String search) {
		String pinyin = "";
		char[] ch = name.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			String[] chs = PinyinHelper.toHanyuPinyinStringArray(ch[i]);
			if (chs == null)
				continue;
			String result = chs[0];
			result = result.substring(0, result.length() - 1);
			pinyin += result;
		}
		// System.out.println("pinyin==>"+pinyin);
		if (pinyin.contains(search)) {
			return true;
		}
		return false;
	}

	public static boolean containsUpLetters(String name, String search) {
		String upLetters = "";
		char[] ch = name.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			String[] chs = PinyinHelper.toHanyuPinyinStringArray(ch[i]);
			if (chs == null)
				continue;
			String result = chs[0].substring(0, 1);
			upLetters += result;
		}
		// System.out.println("upLetter==>"+upLetters);
		if (upLetters.contains(search)) {
			return true;
		}
		return false;
	}
}
