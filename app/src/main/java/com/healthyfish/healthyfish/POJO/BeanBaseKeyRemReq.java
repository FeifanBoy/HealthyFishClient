package com.healthyfish.healthyfish.POJO;

public class BeanBaseKeyRemReq extends BeanBaseReq {
	private String key;
	
	public BeanBaseKeyRemReq(){super(BeanBaseKeyRemReq.class.getSimpleName());}
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
}
