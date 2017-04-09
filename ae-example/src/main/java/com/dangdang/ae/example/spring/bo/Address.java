package com.dangdang.ae.example.spring.bo;

import com.dangdang.ae.base.AttributeExplain;

public class Address {
	@AttributeExplain(explaintInfo="所在楼号")
	private String buiding;
	@AttributeExplain(explaintInfo="所在楼层")
	private String floor;
	@AttributeExplain(explaintInfo="教室编号")
	private String door;
	public String getBuiding() {
		return buiding;
	}
	public void setBuiding(String buiding) {
		this.buiding = buiding;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getDoor() {
		return door;
	}
	public void setDoor(String door) {
		this.door = door;
	}
}
