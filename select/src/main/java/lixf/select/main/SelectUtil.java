package lixf.select.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import lixf.select.main.com.BeanUtil;
import lixf.select.main.com.SelectRuntimeException;
import lixf.select.main.pavo.GroupBy;
import lixf.select.main.pavo.Limit;
import lixf.select.main.pavo.OrderBy;
import lixf.select.main.pavo.Where;

/**
 * @author lixf
 */
public class SelectUtil {


	public final static <T> List<Map<String, Object>> query(List<T> data, Where<?> where, GroupBy groupBy, OrderBy orderBy, Limit limit) throws Exception {

		if (where != null) {
			// 查询符合条件的数据
			data = query(data, where);
		}
		List<Map<String, Object>> newData = new ArrayList<>();
		if (data != null) {
			for (T temp : data) {
				newData.add(BeanUtil.objectToMap(temp, false, Object.class));
			}
		}
		if (groupBy != null) {
			// 分组并 包含 count(*) 字段
			newData = group(newData, groupBy);
		}
		if (orderBy != null) {
			List<Map<String, Object>> tempRes = new ArrayList<>();
			// 排序，并将结果放入tempRes
			order(tempRes, newData, orderBy.fields(), 0);
			newData = tempRes;
		}
		if (limit != null) {
			// 分页
			newData = limit(newData, limit);
		}
		return newData;
	}

	/**
	 * 查询符合条件的数据
	 */
	private final static <T> List<T> query(List<T> data, Where<?> where) {

		if (data != null && data.size() > 0) {
			List<T> res = new ArrayList<>();
			for (T temp : data) {
				if (isOk(temp, where)) {
					res.add(temp);
				}
			}
			return res;
		} else {
			return null;
		}

	}

	/**
	 * 查询符合条件的数据
	 */
	@SuppressWarnings("unchecked")
	private final static <T> Boolean isOk(T data, Where<?> where) {

		if (where.field != null) {
			Object obj = BeanUtil.getClassValue(data, where.field);
			Comparable<?> compareVal = where.values.get(0);
			Boolean res = false;

			switch (where.compareType) {
				case isNull:
					break;
				case isNotNull:
					break;
				default:
					if (obj != null) {
						if (obj instanceof Comparable<?>) {

						} else {
							throw new SelectRuntimeException(where.field + " 字段值没有实现 Comparable 接口,不能进行[" + where.compareType.name + "]比较");
						}
					}
					break;
			}

			switch (where.compareType) {
				case lt:
					if (obj != null && ((Comparable<Object>) obj).compareTo(compareVal) < 0) {
						res = true;
					}
					break;
				case lte:
					if (obj != null && ((Comparable<Object>) obj).compareTo(compareVal) <= 0) {
						res = true;
					}
					break;
				case eg:
					if (obj != null && ((Comparable<Object>) obj).compareTo(compareVal) == 0) {
						res = true;
					}
					break;
				case ne:
					res = true;
					if (obj != null && ((Comparable<Object>) obj).compareTo(compareVal) == 0) {
						res = false;
					}
					break;
				case gt:
					if (obj != null && ((Comparable<Object>) obj).compareTo(compareVal) > 0) {
						res = true;
					}
					break;
				case gte:
					if (obj != null && ((Comparable<Object>) obj).compareTo(compareVal) >= 0) {
						res = true;
					}
					break;
				case in:
					if (obj != null) {
						for (Comparable<?> compareVal_temp : where.values) {
							if (compareVal_temp != null && ((Comparable<Object>) obj).compareTo(compareVal_temp) == 0) {
								res = true;
								break;
							}
						}
					}
					break;
				case notin:
					res = true;
					if (obj != null) {
						for (Comparable<?> compareVal_temp : where.values) {
							if (compareVal_temp != null && ((Comparable<Object>) obj).compareTo(compareVal_temp) == 0) {
								res = false;
								break;
							}
						}
					}
					break;
				case endWith:
					if (obj != null) {
						if (obj instanceof String) {
							res = (obj + "").endsWith(compareVal + "");
						} else {
							throw new SelectRuntimeException(where.field + " 字段值 不是string 类型,不能进行[" + where.compareType.name + "]比较");
						}
					}
					break;
				case startsWith:
					if (obj != null) {
						if (obj instanceof String) {
							res = (obj + "").startsWith(compareVal + "");
						} else {
							throw new SelectRuntimeException(where.field + " 字段值 不是string 类型,不能进行[" + where.compareType.name + "]比较");
						}
					}
					break;
				case contains:
					if (obj != null) {
						if (obj instanceof String) {
							res = (obj + "").contains(compareVal + "");
						} else {
							throw new SelectRuntimeException(where.field + " 字段值 不是string 类型,不能进行[" + where.compareType.name + "]比较");
						}
					}
					break;
				case isNull:
					if (obj == null) {
						res = true;
					}
					break;
				case isNotNull:
					if (obj != null) {
						res = true;
					}
					break;
				default:
					break;
			}

			return res;
		}
		if (where.isOr) {
			Boolean res = false;
			for (Where<?> childWhere : where.wheres) {
				if (isOk(data, childWhere)) {
					res = true;
					break;
				}
			}
			return res;
		} else {
			Boolean res = true;
			for (Where<?> childWhere : where.wheres) {
				if (!isOk(data, childWhere)) {
					res = false;
					break;
				}
			}
			return res;
		}

	}

	/**
	 * 分组
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final static List<Map<String, Object>> group(List<Map<String, Object>> data, GroupBy groupBy) {

		Map<Object, Map> dataMap = new HashMap<>();
		String[] fields = groupBy.fields();
		for (Map<String, Object> obj : data) {
			Map lastLevel = dataMap;
			for (int i=0;i<fields.length;i++) {
				String field = fields[i];
				if (!obj.containsKey(field)) {
					throw new SelectRuntimeException(" 分组字段[" + field + "]不存在");
				}
				Object key = obj.get(field);
				if(i==fields.length-1) {
					if (!lastLevel.containsKey(key)) {
						lastLevel.put(key, new ArrayList<Map<String, Object>>());
					}
					List<Map<String, Object>> lastList = (List<Map<String, Object>>) lastLevel.get(key);
					lastList.add(obj);
				} else {
					if (!lastLevel.containsKey(key)) {
						lastLevel.put(key, new HashMap<>());
					}
					lastLevel = (Map) lastLevel.get(key);
				}
			}
		}
		List<Map<String, Object>> rows = new ArrayList<>();
		List<Object> curRowColumns = new ArrayList<>();
		// 用递归将 data 的多层map转为list ，并 包含 count(*) 字段
		convertGoupRes(fields, curRowColumns, dataMap, rows);
		return rows;
	}

	/**
	 * 用递归将 data 的多层map转为list ，并 包含 count(*) 字段
	 *
	 * @param fields
	 *            分组包含的的字段
	 * @param rows
	 *            行容器
	 * @param curRowColumns
	 *            当前行所含列临时容器
	 * @param dataMap
	 *            map 形式的 data
	 */
	private final static void convertGoupRes(String[] fields, List<Object> curRowColumns, Map dataMap, List<Map<String, Object>> rows) {


		for (Object e : dataMap.keySet()) {
			List<Object> columnsTemp = new ArrayList<>();
			columnsTemp.addAll(curRowColumns);
			columnsTemp.add(e);
			if (dataMap.get(e) instanceof List) {
				Map<String, Object> obj = new HashMap<>();
				for (int i = 0; i < fields.length; i++) {
					obj.put(fields[i], columnsTemp.get(i));
				}
				obj.put("count(*)", ((List) dataMap.get(e)).size());
				rows.add(obj);
			} else {
				convertGoupRes(fields, columnsTemp, (Map) dataMap.get(e), rows);
			}
			columnsTemp.clear();
		}

	}


	/**
	 * 排序
	 *
	 * @param res
	 *            排序结果集容器
	 * @param data
	 *            需要排序的数据
	 * @param fields
	 *            所有需要排序的字段
	 * @param fieldIndex
	 *            当前排序字段脚标
	 */
	private final static void order(List<Map<String, Object>> res, List<Map<String, Object>> data, OrderBy[] fields, int fieldIndex) {

		Map<Object, List<Map<String, Object>>> tempRes = new TreeMap<>();
		for (Map<String, Object> tempObj : data) {
			Object key = tempObj.get(fields[fieldIndex].field);
			if (key == null) {
				key = "";
			}
			if (!tempRes.containsKey(key)) {
				tempRes.put(key, new ArrayList<>());
			}
			tempRes.get(key).add(tempObj);
		}

		List<List<Map<String, Object>>> tempList = new ArrayList<>(tempRes.size());
		for (Entry<Object, List<Map<String, Object>>> entry : tempRes.entrySet()) {
			tempList.add(null);// 站位
		}

		int i = 0;
		for (Entry<Object, List<Map<String, Object>>> entry : tempRes.entrySet()) {
			if (fields[fieldIndex].isDesc) {
				tempList.set(tempRes.size() - 1 - i, entry.getValue());
			} else {
				tempList.set(i, entry.getValue());
			}
			i++;
		}

		if (fieldIndex + 1 < fields.length) {
			for (List<Map<String, Object>> tempChildList : tempList) {
				order(res, tempChildList, fields, fieldIndex + 1);
			}
		} else {
			for (List<Map<String, Object>> tempChildList : tempList) {
				res.addAll(tempChildList);
			}
		}
	}

	/**
	 * 分页
	 */
	private final static List<Map<String, Object>> limit(List<Map<String, Object>> data, Limit limit) {

		if (data != null && data.size() > limit.skip) {
			return data.subList(limit.skip, (data.size() >= limit.skip + limit.limit) ? limit.skip + limit.limit : data.size());
		} else {
			return null;
		}
	}



}
