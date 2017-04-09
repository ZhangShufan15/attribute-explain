package com.dangdang.ae.example.java;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.dangdang.ae.base.AttributeExplain;
import com.dangdang.ae.base.AttributeExplainPrinter;
import com.dangdang.ae.base.convertor.HtmlMarkConvertor;
import com.dangdang.ae.base.convertor.PlainMarkConvertor;

/**
 * 1.当前必须有setter方法，但是实际上是不必要的；
 * 2.还有没有完善的集合类型；
 * 
 * @author zhangxiansheng
 *
 */
public class UseExplainer{

	@AttributeExplain(explaintInfo = "姓名")
	private String name = "shufan";
	
	@AttributeExplain(explaintInfo = "下一级节点")
	private List<UseExplainer> children = new ArrayList<>();
	
	public List<UseExplainer> getChildren() {
		return children;
	}

	public void setChildren(List<UseExplainer> children) {
		this.children = children;
	}

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	static class TestObjectChild extends UseExplainer{
		private String addr = "北京";

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		UseExplainer test = new UseExplainer();
		UseExplainer test2 = new UseExplainer();
		UseExplainer test3 = new UseExplainer();
		test.getChildren().add(test2);
		test.getChildren().add(test3);

		
		TestObjectChild child = new TestObjectChild();
		System.out.println(new AttributeExplainPrinter(new HtmlMarkConvertor()).getExplainInfo(child));
		System.out.println(new AttributeExplainPrinter(new PlainMarkConvertor()).getExplainInfo(child));
	}
}
