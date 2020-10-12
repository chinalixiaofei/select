package lixf.select.main.pavo;

import java.util.ArrayList;
import java.util.List;

import lixf.select.main.com.StrUtils;

/**
 * @author lixf
 */
public class GroupBy {

	private final List<String> fields;

	public GroupBy(String... fields) {

		if (fields != null && fields.length > 0) {
			this.fields = new ArrayList<>();
			for (String field : fields) {
				if (!StrUtils.isEmpty(field)) {
					this.fields.add(field.trim());
				}
			}
		} else {
			this.fields = null;
		}
	}

	public String[] fields() {
		if (fields == null || fields.size() == 0) {
			return null;
		}
		return fields.toArray(new String[fields.size()]);
	}

	public boolean contains(String field) {
		if(this.fields!=null&&!StrUtils.isEmpty(field)) {
			for(String temp:this.fields) {
				if (temp.equals(field)) {
					return true;
				}
			}
		}
		return false;
	}
}
