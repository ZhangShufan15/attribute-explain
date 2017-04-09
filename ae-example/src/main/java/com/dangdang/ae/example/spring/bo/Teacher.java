package com.dangdang.ae.example.spring.bo;

import com.dangdang.ae.base.AttributeExplain;

public class Teacher {
	@AttributeExplain(explaintInfo="教师姓名")
	private String name;
	@AttributeExplain(explaintInfo="教师性别")
	private String gender;
	@AttributeExplain(explaintInfo="教师工作年龄")
	private String experience;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}

}
