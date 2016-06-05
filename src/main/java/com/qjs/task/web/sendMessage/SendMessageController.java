package com.qjs.task.web.sendMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qjs.task.base.log.ErrorLog;
import com.qjs.task.service.ISendMessage;
import com.qjs.task.task.MyTask;

@RequestMapping("/sendInfo")
@Controller("SendMessageController")
@Scope("prototype")
public class SendMessageController {
	@Autowired
	@Qualifier("sendMessageService")
	ISendMessage sendMessageService;
	private static Logger log = LoggerFactory.getLogger(MyTask.class);
	private static Logger error = LoggerFactory.getLogger(ErrorLog.class);
	/**
	 * 发送邮件与手机短信
	 */
	@RequestMapping("/send")
	@ResponseBody
	public void sendEmailPhoneMessage() {
		log.info("#######################task exec start ####################### ");
		try {
			sendMessageService.sendUserMessage();
			log.info("#######################task exec end ####################### ");
		} catch (Exception e) {
			error.info("#######################task exec error ####################### ");
			e.printStackTrace();
		}
	}
}