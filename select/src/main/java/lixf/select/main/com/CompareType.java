package lixf.select.main.com;

/**
 * 比较类型枚举
 *
 * @author squirrel
 */
public enum CompareType {



	//@formatter:off
	lt(-2, "小于"),
	lte(-1, "小于等于"),
	eg(0, "相等"),
	gte(1, "大于等于"),
	gt(2, "大于"),
	ne(3, "不等于"),
	in(4, "在之内"),
	notin(5, "不在之内"),
	endWith(6, "以之结尾"),
	startsWith(7, "以之开头"),
	contains(8, "字符串包含"),
	isNull(9, "为空"),
	isNotNull(10, "不为空"),
	;
	//@formatter:on
	public final Integer code;

	public final String name;

	CompareType(Integer code, String name) {

		this.code = code;
		this.name = name;
	}


	@Override
	public String toString() {

		return "{" + "code:" + code + ", name:" + name + "}";
	}

	public static CompareType resolve(String cardTypeCode) {

		CompareType cardTypeMatch = null;

		for (CompareType cardType : values()) {
			if (cardType.code.equals(cardTypeCode)) {
				cardTypeMatch = cardType;
				break;
			}
		}
		return cardTypeMatch;
	}
}
