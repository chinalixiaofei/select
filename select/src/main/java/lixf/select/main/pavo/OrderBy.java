package lixf.select.main.pavo;

import java.util.ArrayList;
import java.util.List;

import lixf.select.main.com.SelectRuntimeException;
import lixf.select.main.com.StrUtils;

/**
 * @author lixf
 */
public class OrderBy {

	public final String field;

	public final Boolean isDesc;

	private List<OrderBy> fields;

	private OrderBy(String field, Boolean isDesc) {

		this.field = field;
		this.isDesc = isDesc;
	}

	/**
	 * 以某字段新建排序
	 *
	 * @param field
	 *            排序字段名
	 * @param isDesc
	 *            是否倒序
	 * @return
	 */
	public static final OrderBy orderBy(String field, Boolean isDesc) {

		if (StrUtils.isEmpty(field)) {
			throw new SelectRuntimeException(" orderBy  field is empty ");
		}
		OrderBy orderBy = new OrderBy(field.trim(), isDesc);
		orderBy.fields = new ArrayList<>();
		orderBy.fields.add(orderBy);
		return orderBy;

	}

	/**
	 * 已有排序字段的情况下，增加排序字段
	 *
	 * @param field
	 *            排序字段名
	 * @param isDesc
	 *            是否倒序
	 * @return
	 */
	public OrderBy andOrderBy(String field, Boolean isDesc) {

		if (StrUtils.isEmpty(field)) {
			throw new SelectRuntimeException(" orderBy  field is empty ");
		}
		for (OrderBy temp : fields) {
			if (field.trim().equals(temp.field)) {
				throw new SelectRuntimeException(" orderBy  field [" + temp.field + "]  has exist ");
			}
		}
		OrderBy orderBy = new OrderBy(field, isDesc);
		fields.add(orderBy);
		return this;

	}

	public OrderBy[] fields() {

		if (fields == null || fields.size() == 0) {
			return null;
		}
		return fields.toArray(new OrderBy[fields.size()]);
	}


}
