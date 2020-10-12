package lixf.select.test;

import java.util.Date;

public class Person {

	public Person() {

	}

	public Person(String id, String name, Integer height, Double weight, Integer age, String like, String sex, Date birthday) {

		super();
		this.id = id;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.age = age;
		this.like = like;
		this.sex = sex;
		this.birthday = birthday;
	}

	/**
	 * id
	 */
	private String id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 身高
	 */
	private Integer height;

	/**
	 * 体重
	 */
	private Double weight;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 爱好
	 */
	private String like;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 生日
	 */
	private Date birthday;

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Integer getHeight() {

		return height;
	}

	public void setHeight(Integer height) {

		this.height = height;
	}

	public Double getWeight() {

		return weight;
	}

	public void setWeight(Double weight) {

		this.weight = weight;
	}

	public Integer getAge() {

		return age;
	}

	public void setAge(Integer age) {

		this.age = age;
	}

	public String getLike() {

		return like;
	}

	public void setLike(String like) {

		this.like = like;
	}

	public String getSex() {

		return sex;
	}

	public void setSex(String sex) {

		this.sex = sex;
	}

	public Date getBirthday() {

		return birthday;
	}

	public void setBirthday(Date birthday) {

		this.birthday = birthday;
	}







}
