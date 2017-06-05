package com.commons.pinyin;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author jiayongming 有关汉语拼音的工具类
 */
@Log4j2
public final class PinyinUtil {
	private PinyinUtil(){}
	/**
	 * 静态初始化代码块中加载多音字信息
	 */
	private static Properties polyphone;
	static {
		polyphone = new Properties();
		try {
			@Cleanup InputStreamReader in = new InputStreamReader(Object.class.getResourceAsStream("/polyphone.properties"), "UTF-8");
			polyphone.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 全部小写字母
	 */
	public static final String[] MINUSCULES = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	/**
	 * 全部大写字母
	 */
	public static final String[] MAJUSCULES = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * @param hanzi
	 * @return 传入汉字返回汉语拼音首字母 如果是英文字符，直接返回首字母 true 小写字母 false 大写字母
	 */
	public static String getFirstPinyin(String hanzi, boolean isLower) {
		Enumeration<?> enu = polyphone.propertyNames();
		while(enu.hasMoreElements()){
		    String key = (String)enu.nextElement();
		    if (StringUtils.startsWithIgnoreCase(hanzi, key)) {
		    	if (isLower) {
					return StringUtils.substring(polyphone.getProperty(key), 0, 1);
				}
				return StringUtils.upperCase(StringUtils.substring(polyphone.getProperty(key), 0, 1));
			}
		}

		char[] t1 = hanzi.toCharArray();
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder t4 = new StringBuilder();
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					String[] t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					t4.append(t2[0]) ;// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					t4.append(String.valueOf(t1[i])) ;
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		String result = StringUtils.substring(t4.toString(), 0, 1);
		if (isLower) {
			return result;
		}
		return StringUtils.upperCase(result);
	}

	/**
	 * 提取每个汉字的首字母
	 * 
	 * @param str
	 * @return String
	 */
	public static String getPinYinHeadChar(String str) {
		StringBuilder convert = new StringBuilder();
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert.append(pinyinArray[0].charAt(0)) ;
			} else {
				convert.append(word);
			}
		}
		return convert.toString();
	}
	/**
	 * 将字符串转换成ASCII码
	 * 
	 * @param cnStr
	 * @return String
	 */
	public static String getCnASCII(String cnStr) {
		StringBuilder strBuf = new StringBuilder();
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// 将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static void main(String[] args) {
		String firstPinyin = getFirstPinyin("重庆银行",false);
		log.info(firstPinyin);
	}
}
