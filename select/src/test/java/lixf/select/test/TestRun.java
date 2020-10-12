package lixf.select.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lixf.select.main.SelectUtil;
import lixf.select.main.com.DateUtil;
import lixf.select.main.pavo.GroupBy;
import lixf.select.main.pavo.Limit;
import lixf.select.main.pavo.OrderBy;
import lixf.select.main.pavo.Where;

public class TestRun {


	private static final Date baseDate = new Date();

	private static final List<Person> persons = new ArrayList<>();
	static {
		int i = 1;
		persons.add(new Person("" + i++, "小红", 10 + i, i + 20.0, 10, "乒乓球", "女", DateUtil.getAfterDate(baseDate, i - 1)));
		persons.add(new Person("" + i++, "小花", 10 + i, i + 20.0, 10, "篮球", "女", DateUtil.getAfterDate(baseDate, i - 1)));
		persons.add(new Person("" + i++, "小明", 10 + i, i + 20.0, 10, "篮球", "男", DateUtil.getAfterDate(baseDate, i - 1)));
		persons.add(new Person("" + i++, "大狗", 10 + i, i + 20.0, 10, "游泳", "男", DateUtil.getAfterDate(baseDate, i - 1)));
		persons.add(new Person("" + i++, "二狗", 10 + i, i + 20.0, 11, "游泳", "男", DateUtil.getAfterDate(baseDate, i - 1)));
		persons.add(new Person("" + i++, "二狗2", 10 + i, i + 20.0, 11, "游泳", "男", DateUtil.getAfterDate(baseDate, i - 1)));
		persons.add(new Person("" + i++, "翠花", 10 + i, i + 20.0, 11, "唱歌", "女", DateUtil.getAfterDate(baseDate, i - 1)));
		persons.add(new Person("" + i++, "翠花2", 10 + i, i + 20.0, 11, "唱歌", "女", DateUtil.getAfterDate(baseDate, i - 1)));
	}

	public static void main(String[] args) throws Exception {

		List<Map<String, Object>> res = null;
		System.out.println("测试数据总共:" + persons.size() + "条。");
		{
			System.out.println("");
			System.out.println("----测试排序：按sex降序,id升序----");
			res = SelectUtil.query(persons, null, null, OrderBy.orderBy("sex", true).andOrderBy("id", false), null);
			for (Map<String, Object> temp : res) {
				System.out.println(temp);
			}
		}

		{
			System.out.println("");
			System.out.println("----测试分页：按id升序,取2至4条----");
			res = SelectUtil.query(persons, null, null, OrderBy.orderBy("id", false), new Limit(2, 2));
			for (Map<String, Object> temp : res) {
				System.out.println(temp);
			}
		}

		{
			System.out.println("");
			System.out.println("----测试简单条件：birthday<=" + DateUtil.getAfterDate(baseDate, 3) + "----");
			res = SelectUtil.query(persons, Where.lte("birthday", DateUtil.getAfterDate(baseDate, 3)), null, OrderBy.orderBy("birthday", false), new Limit(0, persons.size()));
			for (Map<String, Object> temp : res) {
				System.out.println(temp);
			}
		}


		{
			System.out.println("");
			System.out.println("----测试复杂条件：喜欢篮球或乒乓球的女生,或者喜欢游泳的大于10岁的男生 ----");
			List<Comparable<String>> likes = new ArrayList<>();
			likes.add("篮球");
			likes.add("乒乓球");
			Where<?> where1 = Where.and(Where.in("like", likes), Where.eg("sex", "女"));
			Where<?> where2 = Where.and(Where.eg("like", "游泳"), Where.gt("age", 10));
			res = SelectUtil.query(persons, Where.or(where1, where2), null, OrderBy.orderBy("id", false), new Limit(0, persons.size()));
			for (Map<String, Object> temp : res) {
				System.out.println(temp);
			}
		}

		{
			System.out.println("");
			System.out.println("----测试id小于等于6，并按性别分组 ----");
			res = SelectUtil.query(persons, Where.lte("id", "6"), new GroupBy("sex"), OrderBy.orderBy("sex", false), new Limit(0, persons.size()));
			for (Map<String, Object> temp : res) {
				System.out.println(temp);
			}
		}

	}

}
