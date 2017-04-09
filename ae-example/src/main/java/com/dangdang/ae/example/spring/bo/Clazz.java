package com.dangdang.ae.example.spring.bo;

import java.util.List;
import java.util.Map;

import com.dangdang.ae.base.AttributeExplain;

public class Clazz {
	@AttributeExplain(explaintInfo="班级名称")
	private String name;
	@AttributeExplain(explaintInfo="班级地址")
	private Address address;
	@AttributeExplain(explaintInfo="教师列表，key=》负责科目；value=》教师信息")
	private Map<String, Teacher> teachers;
	@AttributeExplain(explaintInfo="学生列表")
	private List<Student> students;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public Map<String, Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(Map<String, Teacher> teachers) {
		this.teachers = teachers;
	}
}
