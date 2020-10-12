package lixf.select.main.com;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixf
 */
public class BeanUtil {

	public static <T, V> T mapToObject(Map<String, V> map, Class<T> beanClass) throws Exception {

		if (map == null) {
			return null;
		}

		T obj = beanClass.newInstance();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}

			field.setAccessible(true);
			field.set(obj, map.get(field.getName()));
		}

		return obj;
	}

	public static <V> Map<String, V> objectToMap(Object obj, Boolean fieldNotNull, Class<V> valueTypeClass) throws Exception {

		if (obj == null) {
			return null;
		}

		Map<String, V> map = new HashMap<>();

		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			String fname = field.getName();
			if ("serialVersionUID".equals(fname)) {
				continue;
			}
			field.setAccessible(true);
			Object gobj = field.get(obj);
			if (fieldNotNull && gobj == null) {
				continue;
			} else {
				map.put(fname, (V) gobj);
			}
		}
		return map;
	}



	/**
	 * 根据字段名称取值
	 *
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getClassValue(Object obj, String fieldName) {

		Object objValue = null;

		if (obj == null) {
			return null;
		}
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			if (fieldName.equals(field.getName())) {
				try {
					field.setAccessible(true);
					objValue = field.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				break;
			}

		}
		return objValue;
	}
}