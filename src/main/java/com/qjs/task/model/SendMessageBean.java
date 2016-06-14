package com.qjs.task.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
 
public class SendMessageBean {
	
  
	private String projectId; // 标号
	
	private String jkusername;// 借款人姓名
	
	private String dueDay; //到期日
	
	private String  dueDay7; //到期前7日
	
	public String getDueDay7() {
		return dueDay7;
	}

	public void setDueDay7(String dueDay7) {
		this.dueDay7 = dueDay7;
	}

	private String acount;// 金额 （应还本息金额）
	private String email;// 借款人邮件地址

	private String phone;// 借款人手机号

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getJkusername() {
		return jkusername;
	}

	public void setJkusername(String jkusername) {
		this.jkusername = jkusername;
	}

	public String getDueDay() {
		return dueDay;
	}

	public void setDueDay(String dueDay) {
		this.dueDay = dueDay;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
   }