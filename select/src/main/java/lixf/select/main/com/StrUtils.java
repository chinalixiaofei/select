package lixf.select.main.com;

/**
 * @author lixf
 */
public class StrUtils {


	private StrUtils() {

		throw new IllegalAccessError("Utils classes");
	}


	/**
	 * 判断字符串是否为空或空串，两边空格会被截掉
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {

		return str == null || "".equals(str.trim());
	}


}