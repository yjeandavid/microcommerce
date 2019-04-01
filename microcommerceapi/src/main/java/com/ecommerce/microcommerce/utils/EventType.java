package com.ecommerce.microcommerce.utils;

public enum EventType {

	ADD("add"), EDIT("edit"), DELETE("delete");

	private String val;

	private EventType(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}
}
