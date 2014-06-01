package com.mangal.module;

import java.util.LinkedList;
import java.util.List;

public class LrcInfo {
	private String ti = null;//������  
    private String ar = null;//�ݳ���  
    private String al = null;//ר��
    private String by = null;//������
	
	//���ʱ�������
	List<Long> timeMills = new LinkedList<Long>();
	//���ʱ�������Ӧ�ĸ��
	List<String> messages = new LinkedList<String>();
	
  	public String getTi() {
		return ti;
	}
	public void setTi(String ti) {
		this.ti = ti;
	}
	public String getAr() {
		return ar;
	}
	public void setAr(String ar) {
		this.ar = ar;
	}
	public String getAl() {
		return al;
	}
	public void setAl(String al) {
		this.al = al;
	}
	public String getBy() {
		return by;
	}
	public void setBy(String by) {
		this.by = by;
	}
	public Long getTimeMills(int i) {
		return timeMills.get(i);
	}
	
	public void setTimeMills(Long currentTime) {
		timeMills.add(currentTime);
	}
	public String getMessages(int i) {
		return messages.get(i);
	}
	public void setMessages(String currentContent) {
		messages.add(currentContent);
	}
	
	public Integer getTimeMillsSize() {
		return timeMills.size();
	}
	
	@Override
	public String toString() {
		return "LrcInfo [ti=" + ti + ", ar=" + ar + ", al=" + al + ", by=" + by
				+ ", timeMills=" + timeMills + ", messages=" + messages + "]";
	}
  	
  	
}