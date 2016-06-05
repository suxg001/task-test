package com.qjs.task.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Administrator
 *
 */
 
public class SendMessageBean {
	
    private String sched_name;
	public String getSched_name() {
		return sched_name;
	}

	public void setSched_name(String sched_name) {
		this.sched_name = sched_name;
	}

	private String browid; // 标号
	
 
	private String email;// 邮件地址

	private String phone;// 手机号

	private String ckname;// 催款人姓名

	private String jkname;// 借款人姓名
   
	private String maturityDate;//到期时间
	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	private String jkgs;// 借款人公司
	private String sex;// 性别

	private String acount;// 金额

	public String getBrowid() {
		return browid;
	}

	public void setBrowid(String browid) {
		this.browid = browid;
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

	public String getCkname() {
		return ckname;
	}

	public void setCkname(String ckname) {
		this.ckname = ckname;
	}

	public String getJkname() {
		return jkname;
	}

	public void setJkname(String jkname) {
		this.jkname = jkname;
	}

	public String getJkgs() {
		return jkgs;
	}

	public void setJkgs(String jkgs) {
		this.jkgs = jkgs;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

}