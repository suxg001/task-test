package com.qjs.task.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	  * @param emailAddress
	  * @param message
	  * @param setSubject
	  */
	public void sendMail(String emailAddress, String message,String setSubject) {
		// TODO Auto-generated method stub
		PropertyConfig.init("email.properties");

		String springmailhost = PropertyConfig.getProperty(QjsConstants.SPRINGMAILHOST, "");
		String springmailport = PropertyConfig.getProperty(QjsConstants.SPRINGMAILPORT, "");
		String springmailprotocol = PropertyConfig.getProperty(QjsConstants.SPRINGMAILPROTOCOL, "");
		String springmailusername = PropertyConfig.getProperty(QjsConstants.SPRINGMAILUSERNAME, "");
		String springmailpassword = PropertyConfig.getProperty(QjsConstants.SPRINGMAILPASSWORD, "");
		String springmailpropertiesmailtransportprotocol = PropertyConfig
				.getProperty(QjsConstants.SPRINGMAILPROPERTIESMAILTRANSPORTPROTOCOL, "");
		String springmailpropertiesmailsmtpsauth = PropertyConfig
				.getProperty(QjsConstants.SPRINGMAILPROPERTIESMAILSMTPSAUTH, "");
		String springmailpropertiesmailsmtpsstarttlsenable = PropertyConfig
				.getProperty(QjsConstants.SPRINGMAILPROPERTIESMAILSMTPSSTARTTLSENABLE, "");
		String springmailpropertiesmailsmtpstimeout = PropertyConfig
				.getProperty(QjsConstants.SPRINGMAILPROPERTIESMAILSMTPSTIMEOUT, "");
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost(springmailhost);
		// 建立邮件消息
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		// 设置收件人，寄件人 用数组发送多个邮件
		// String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
		// mailMessage.setTo(array);
		mailMessage.setTo(emailAddress);
		mailMessage.setFrom(springmailusername);
		mailMessage.setSubject(setSubject);
		mailMessage.setText(message);

		senderImpl.setUsername(springmailusername); // 根据自己的情况,设置username
		senderImpl.setPassword(springmailpassword); // 根据自己的情况, 设置password

		Properties prop = new Properties();
		prop.put(" mail.smtp.auth ", springmailpropertiesmailsmtpsauth); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
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
	@Transactional
	public void sendUserMessage() {
		// TODO Auto-generated method stub
		try {
			List<SendMessageBean> dqInfoList = dao.querySendMessageList("", "");

			if (dqInfoList.size() > 0) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				for (int i = 0; i < dqInfoList.size(); i++) {
					if (dqInfoList.get(i).getDueDay7() != null && String.valueOf(dqInfoList.get(i).getDueDay7())
							.indexOf(String.valueOf(simpleDateFormat.format(new Date()))) == 0) {
						String emailAddress = dqInfoList.get(i).getEmail();
						String userName = dqInfoList.get(i).getJkusername();
						String maturityDate = String.valueOf(dqInfoList.get(i).getDueDay()).substring(0,10);
						String amount = dqInfoList.get(i).getAcount();
						String projectId = dqInfoList.get(i).getProjectId();
						String jkphone = dqInfoList.get(i).getPhone();
						PropertyConfig.init("phone.properties");
						// 借款人短信发送
						String jkmessage = PropertyConfig.getProperty("jkmessage", "");
						Object[] jkfmtargs = { userName, maturityDate, amount };
						String jkphoneMessage = MessageFormat.format(jkmessage, jkfmtargs);
						String[] jkphones = { jkphone };
						this.savaPhoneMessage(jkphones, jkphoneMessage);
						// 借款人邮件发送
						this.sendMail(emailAddress, jkphoneMessage,"还款提醒");

						// 催款人短信发送
					
						String phones = PropertyConfig.getProperty("phone", "");
						String ckmessage = PropertyConfig.getProperty("ckmessage", "");
						Object[] ckfmtargs = { projectId, amount,maturityDate};
						String ckphoneMessage = MessageFormat.format(ckmessage, ckfmtargs);
						this.savaPhoneMessage(phones.split(","), ckphoneMessage);

						// 催款人邮件发送
						String ckemail = PropertyConfig.getProperty("ckemail", "");
						String[] ckemails = ckemail.split(",");
						for (int j = 0; j < ckemails.length; j++) {
							this.sendMail(ckemails[j], ckphoneMessage,"催款提醒");
						}
						// String uf8=new
						// String(phoneMessage.getBytes("gb2312"),"utf-8");

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
