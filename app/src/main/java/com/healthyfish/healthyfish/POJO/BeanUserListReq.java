package com.healthyfish.healthyfish.POJO;

public class BeanUserListReq extends BeanBaseReq {
	private String prefix;
	int	from;
	int num;
	int to;

	public BeanUserListReq(){super(BeanUserListReq.class.getSimpleName());}
	
	public String getPrefix() {return prefix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}
	public int getFrom() {return from;}
	public void setFrom(int from) {this.from = from;}
	public int getNum() {return num;}
	public void setNum(int num) {this.num = num;}
	public int getTo() {return to;}
	public void setTo(int to) {this.to = to;}
}
