package com.unioncast.db.nosql.elasticsearch;

public class UserModel {

	private Long id;
	
	private String name;
	
	private String age;
	
	private String sex;
	
	private String tel;
	
	public UserModel(){
		
	}

	public UserModel(long id, String name, String age, String sex, String tel) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.tel = tel;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
