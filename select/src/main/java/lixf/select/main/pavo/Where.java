package lixf.select.main.pavo;

import java.util.ArrayList;
import java.util.List;

import lixf.select.main.com.CompareType;
import lixf.select.main.com.SelectRuntimeException;

/**
 * @author lixf
 */
public class Where<T> {

	public final String field;

	public final CompareType compareType;

	public final List<Comparable<T>> values;

	public final Where<?>[] wheres;

	public final Boolean isOr;

	private Where(Boolean isOr, Where<?>... wheres) {

		this.field = null;
		this.compareType = null;
		this.values = null;
		this.wheres = wheres;
		this.isOr = isOr;
	}

	private Where(String field, CompareType compareType, Comparable<T> value) {

		this.field = field;
		this.compareType = compareType;
		List<Comparable<T>> temps = new ArrayList<>();
		temps.add(value);
		this.values = temps;
		this.wheres = null;
		this.isOr = null;
	}

	private Where(String field, CompareType compareType, List<Comparable<T>> values) {

		this.field = field;
		this.compareType = compareType;
		this.values = values;

		this.wheres = null;
		this.isOr = null;
	}




	/**
	 * field对应值 等于 value
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final <T> Where<T> eg(String field, Comparable<T> value) {

		if (value == null) {
			throw new SelectRuntimeException(" value must not null ");
		}
		return new Where<>(field, CompareType.eg, value);
	}


	/**
	 * field对应值 不等于 value
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final <T> Where<T> ne(String field, Comparable<T> value) {

		if (value == null) {
			throw new SelectRuntimeException(" value must not null ");
		}
		return new Where<>(field, CompareType.ne, value);
	}

	/**
	 * field对应值 小于 value
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final <T> Where<T> lt(String field, Comparable<T> value) {

		if (value == null) {
			throw new SelectRuntimeException(" value must not null ");
		}
		return new Where<>(field, CompareType.lt, value);
	}

	/**
	 * field对应值 小于等于 value
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final <T> Where<T> lte(String field, Comparable<T> value) {

		if (value == null) {
			throw new SelectRuntimeException(" value must not null ");
		}
		return new Where<>(field, CompareType.lte, value);
	}

	/**
	 * field对应值 大于 value
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final <T> Where<T> gt(String field, Comparable<T> value) {

		if (value == null) {
			throw new SelectRuntimeException(" value must not null ");
		}
		return new Where<>(field, CompareType.gt, value);
	}

	/**
	 * field对应值 大于等于 value
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final <T> Where<T> gte(String field, Comparable<T> value) {

		if (value == null) {
			throw new SelectRuntimeException(" value must not null ");
		}
		return new Where<>(field, CompareType.gte, value);
	}

	/**
	 * field对应值 在 values 之中
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final <T> Where<T> in(String field, List<Comparable<T>> values) {

		if (values == null || values.size() == 0) {
			throw new SelectRuntimeException(" values must not null ");
		}
		return new Where<>(field, CompareType.in, values);
	}

	/**
	 * field对应值 在 values 之中
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final <T> Where<T> notin(String field, List<Comparable<T>> values) {
		if (values == null || values.size() == 0) {
			throw new SelectRuntimeException(" values must not null ");
		}
		return new Where<>(field, CompareType.notin, values);
	}



	/**
	 * field对应值 以value结尾
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final Where<String> endWith(String field, String value) {

		if (value == null) {
			throw new SelectRuntimeException(" value must not null ");
		}
		return new Where<>(field, CompareType.endWith, value);
	}

	/**
	 * field对应值 以value开始
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final Where<String> startsWith(String field, String value) {

		if (value == null) {
			throw new SelectRuntimeException(" value must not null ");
		}
		return new Where<>(field, CompareType.startsWith, value);
	}

	/**
	 * field对应值 包含 value
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final Where<String> contains(String field, String value) {

		if (value == null) {
			throw new SelectRuntimeException(" value must not null ");
		}
		return new Where<>(field, CompareType.contains, value);
	}

	/**
	 * field对应值 为空
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final Where<String> isNull(String field) {

		return new Where<>(field, CompareType.isNull, "");
	}

	/**
	 * field对应值 不为空
	 *
	 * @param field
	 *            字段
	 * @param value
	 *            参数
	 * @return
	 */
	public static final Where<String> isNotNull(String field) {

		return new Where<>(field, CompareType.isNotNull, "");
	}



	/**
	 * 同时满足所有条件
	 *
	 * @param wheres
	 *            where 的个数大于等于2
	 * @return
	 */

	public static final Where<?> and(Where<?>... wheres) {

		if (wheres == null || wheres.length < 2) {
			throw new SelectRuntimeException(" wheres size must >= 2 ");
		}
		return new Where<>(false, wheres);
	}

	/**
	 * 满足其中一个条件
	 *
	 * @param wheres
	 *            where 的个数大于等于2
	 * @return
	 */
	public static final Where<?> or(Where<?>... wheres) {

		if (wheres == null || wheres.length < 2) {
			throw new SelectRuntimeException(" wheres size must >= 2 ");
		}
		return new Where<>(true, wheres);
	}

}
