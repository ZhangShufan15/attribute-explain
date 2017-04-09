package com.dangdang.ae.example.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dangdang.ae.example.spring.bo.Address;
import com.dangdang.ae.example.spring.bo.Clazz;
import com.dangdang.ae.example.spring.bo.Student;
import com.dangdang.ae.example.spring.bo.Teacher;
import com.dangdang.ae.example.spring.bo.TestContent;

@Controller
public class NegotiatingContentController {

	//http://127.0.0.1:8080/ae-example/getContent.ae
	//http://127.0.0.1:8080/ae-example/getContent.json
	@RequestMapping(value="/getContent")
	public TestContent getContent(){
		TestContent tc = new TestContent();
		tc.setTitle("这是一个测试的类");
		tc.setPubTime("2017-04-08");
		
		return tc;
	}
	//http://127.0.0.1:8080/ae-example/clazzInfo.ae
	//http://127.0.0.1:8080/ae-example/clazzInfo.json
	@RequestMapping(value="/clazzInfo")
	public Clazz clazzInfo(){
		Clazz cls = new Clazz();
		cls.setName("三年级八班");
		
		//普通对象
		Address addr = new Address();
		addr.setBuiding("第二教学楼");
		addr.setFloor("三层");
		addr.setDoor("305");
		cls.setAddress(addr);
		
		//数组类型
		List<Student> studentArray = new ArrayList<>();
		for(int i=0; i < 30; i++){
			Student s = new Student();
			s.setName(String.valueOf(i));
			s.setAge("19");
			s.setGender(String.valueOf(i%2==1? "男":"女"));
			studentArray.add(s);
		}
		cls.setStudents(studentArray);
		
		//Map类型
		Map<String, Teacher> teachers = new HashMap<>();
		Teacher tMath = new Teacher();
		tMath.setName("叶雯雯");
		tMath.setGender("女");
		tMath.setExperience("5年");
		Teacher tEnglish = new Teacher();
		tEnglish.setName("王立福");
		tEnglish.setGender("男");
		tEnglish.setExperience("5年");
		teachers.put("数学", tMath);
		teachers.put("英语", tEnglish);
		cls.setTeachers(teachers);
		
		
		return cls;
	}
}
