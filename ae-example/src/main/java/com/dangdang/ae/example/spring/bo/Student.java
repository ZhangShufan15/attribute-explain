package com.dangdang.ae.example.spring.bo;

import com.dangdang.ae.base.AttributeExplain;

public class Student {
	@AttributeExplain(explaintInfo="学生姓名")
	private String name;
	@AttributeExplain(explaintInfo="学生年龄")
	private String age;
	@AttributeExplain(explaintInfo="学生性别")
	private String gender;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
