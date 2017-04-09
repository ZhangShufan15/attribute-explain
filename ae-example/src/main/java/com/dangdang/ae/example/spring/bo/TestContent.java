package com.dangdang.ae.example.spring.bo;

import com.dangdang.ae.base.AttributeExplain;

public class TestContent {
	@AttributeExplain(explaintInfo="标题")
	String title;
	@AttributeExplain(explaintInfo="时间")
	String pubTime;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
}
