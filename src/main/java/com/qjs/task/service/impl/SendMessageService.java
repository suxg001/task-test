package com.qjs.task.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qjs.task.base.util.PropertyConfig;
import com.qjs.task.dao.AllDaoImpl;
import com.qjs.task.model.QjsConstants;
import com.qjs.task.model.SendMessageBean;
import com.qjs.task.service.ISendMessage;

@Service("sendMessageService")
@Scope("prototype")
@Transactional
public class SendMessageService implements ISendMessage {
	@Autowired
	private AllDaoImpl dao;

	/**
	 * 发送邮件
	 * 
	 * @param emailAddress
	 * @param userName
	 * @param maturityDate
	 * @param amount
	 */
	public void sendMail(String emailAddress, String userName, String maturityDate, String amount) {
		// TODO Auto-generated method stub
		PropertyConfig.init("email.properties");

		String springmailhost = PropertyConfig.getProperty(QjsConstants.SPRINGMAILHOST, "");
		String springmailport =PropertyConfig.getProperty(QjsConstants.SPRINGMAILPORT,"");
		String springmailprotocol = PropertyConfig.getProperty(QjsConstants.SPRINGMAILPROTOCOL,"");
		String springmailusername = PropertyConfig.getProperty(QjsConstants.SPRINGMAILUSERNAME,"");
		String springmailpassword = PropertyConfig.getProperty(QjsConstants.SPRINGMAILPASSWORD,"");
		String springmailpropertiesmailtransportprotocol =PropertyConfig.getProperty(QjsConstants.SPRINGMAILPROPERTIESMAILTRANSPORTPROTOCOL,"");
		String springmailpropertiesmailsmtpsauth =PropertyConfig.getProperty(QjsConstants.SPRINGMAILPROPERTIESMAILSMTPSAUTH,"");
		String springmailpropertiesmailsmtpsstarttlsenable =PropertyConfig.getProperty(QjsConstants.SPRINGMAILPROPERTIESMAILSMTPSSTARTTLSENABLE,"");
		String springmailpropertiesmailsmtpstimeout =PropertyConfig.getProperty(QjsConstants.SPRINGMAILPROPERTIESMAILSMTPSTIMEOUT,"");
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost(springmailhost);
		// 建立邮件消息
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		// 设置收件人，寄件人 用数组发送多个邮件
		// String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
		// mailMessage.setTo(array);
		mailMessage.setTo(" 362073004@qq.com ");
		mailMessage.setFrom(springmailusername);
		mailMessage.setSubject(" 测试简单文本邮件发送！ ");
		mailMessage.setText(" 测试我的简单邮件发送机制！！ ");

		senderImpl.setUsername(springmailusername); // 根据自己的情况,设置username
		senderImpl.setPassword(springmailpassword); // 根据自己的情况, 设置password

		Properties prop = new Properties();
		prop.put(" mail.smtp.auth ",   springmailpropertiesmailsmtpsauth); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put(" mail.smtp.timeout ", springmailpropertiesmailsmtpstimeout);
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println(" 邮件发送成功.. ");
	}

	public void savaPhoneMessage(String[] phone, String context) {
		// TODO Auto-generated method stub
		if (phone.length > 0) {
			for (int i = 0; i < phone.length; i++) {
				dao.savaPhoneMessage(phone[i], context);
			}
		}

	}

	@Override
	public void sendUserMessage() {
		// TODO Auto-generated method stub
		List<SendMessageBean> dqInfoList = dao.querySendMessageList("", "");

		if (dqInfoList.size() > 0) {
			System.out.println("========================" + dqInfoList.get(0).getSched_name());
			System.out.println("========================" + dqInfoList.get(0).getCkname());
			
			for(int i=0;i<dqInfoList.size();i++){
				String emailAddress=dqInfoList.get(i).getEmail(); 
				String userName=dqInfoList.get(i).getJkname();
				String maturityDate=dqInfoList.get(i).getMaturityDate();
				String amount=dqInfoList.get(i).getAcount();
			this.sendMail(emailAddress,userName,maturityDate,amount);
			PropertyConfig.init("phone.properties");
			String phones = PropertyConfig.getProperty("phone", "");
		    String ckmessage=PropertyConfig.getProperty("ckmessage", "");
			   Object [] ckfmtargs = {"xm0001","aaaaa", "8888888",};
		        String ckphoneMessage=MessageFormat.format (ckmessage, ckfmtargs);
//		        String uf8=new String(phoneMessage.getBytes("gb2312"),"utf-8");
				this.savaPhoneMessage(phones.split(","), ckphoneMessage);
				
				//借款人短信发送
			    String jkmessage=PropertyConfig.getProperty("jkmessage", "");
			    Object [] jkfmtargs = {"xm0001","aaaaa", "8888888",};
			    String jkphoneMessage=MessageFormat.format (ckmessage, ckfmtargs);
				this.savaPhoneMessage(phones.split(","), jkphoneMessage);
			}
			}
	}

}
