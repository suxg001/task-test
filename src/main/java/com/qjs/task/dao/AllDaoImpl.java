package com.qjs.task.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qjs.task.base.dao.CommonDAO;
import com.qjs.task.base.dao.EntityMap;
import com.qjs.task.base.quartz.ScheduleJob;
import com.qjs.task.model.JobResultBean;
import com.qjs.task.model.OperUser;
import com.qjs.task.model.ScheduleJobBean;
import com.qjs.task.model.SendMessageBean;


@Component
public class AllDaoImpl {
	
	@Autowired
	private CommonDAO commonDAO;
	
	public OperUser getOperUserByUserNameAndPassoword(String username,String password){
		
		List<OperUser> operUsers = commonDAO.getEntityManager().createQuery("select o from OperUser o where o.operUserName=?1 and o.operPassword=?2", OperUser.class)
    			.setParameter(1, username)
    			.setParameter(2, password)
    			.getResultList();

    	if(operUsers.size() > 0){
    		return operUsers.get(0);
    	}
    	return null;
    }
	 
	public OperUser getOperUserByTelphone(String telphone){
    	List<OperUser> operUsers = commonDAO.getEntityManager().createQuery("select o from OperUser o where o.operPhone=:telphone ", OperUser.class)
    			.setParameter("telphone", telphone)
    			.getResultList();

    	if(operUsers.size() > 0){
    		return operUsers.get(0);
    	}
    	return null;
    }
    
	@SuppressWarnings("unchecked")
	public List<ScheduleJobBean> findJobsByList(List<ScheduleJob> jobs ) {
		List<Integer> ids = new ArrayList<Integer>();
		List<ScheduleJobBean> dataBeans = new ArrayList<ScheduleJobBean>();
		for (ScheduleJob job : jobs) {
			ids.add(Integer.valueOf(job.getJobId()));
		}
		if(ids.isEmpty()){
			return dataBeans;
		}
		return commonDAO.getEntityManager().createQuery("from ScheduleJobBean where Id in (:ids ) ").setParameter("ids", ids).getResultList();
	}
	
	//获取某一job的resultSize条执行记录
	public List<JobResultBean> getJobResultBeansById(String myJobid, int resultSize){
		String sql = "select o from JobResultBean o where o.jobId=?1 order by o.createTime desc";
		List<JobResultBean> list = commonDAO.getEntityManager().createQuery(sql,JobResultBean.class)
				.setParameter(1, myJobid)
				.setMaxResults(resultSize)
				.getResultList();
		return list;
	}

	//取得前n条执行记录
	public List<JobResultBean> getTopnJobResultBeans(int resultSize){
		String sql = "select o from JobResultBean o order by o.createTime desc";
		List<JobResultBean> list = commonDAO.getEntityManager().createQuery(sql,JobResultBean.class)
				.setMaxResults(resultSize)
				.getResultList();
		return list;
	}
	
	public ScheduleJobBean find(Integer id) {
		if (id == null)
			return null;
		return commonDAO.getEntityManager().find(ScheduleJobBean.class, id);
	}
	
	public JobResultBean findJobResultByID(Integer id) {
		if (id == null)
			return null;
		return commonDAO.getEntityManager().find(JobResultBean.class, id);
	}
	
	public List<SendMessageBean> querySendMessageList(String today,String lastDay){
//		String sql="select sched_name, sched_name  ckname,trigger_name   FROM qrtz_cron_triggers ";
		StringBuffer strBuffer=new StringBuffer();
		strBuffer.append(" SELECT a.project_id projectId, c.contact_person jkusername,(DATE_ADD(a.due_day, INTERVAL -7 DAY)) dueDay7,a.due_day dueDay,a.due_capital_interest  acount,");
		strBuffer.append("	c.register_phone phone,c.company_email email"); 
		strBuffer.append(" FROM ");
		strBuffer.append(" jk_repayment a INNER JOIN  jk_project b ON a.project_id=b.project_id ");   
		strBuffer.append(" INNER JOIN  jk_third_company c ON  b.borrow_company_id=c.company_id ");
		System.out.println("##########################"+strBuffer.toString());
		List<SendMessageBean> messageList = commonDAO.getEntityList(strBuffer.toString(),SendMessageBean.class);
	
		return messageList;
	}

	public void savaPhoneMessage(String phone, String context) {
		// TODO Auto-generated method stub
		
		String sql="INSERT INTO sys_send_sms (send_type,send_mobile,send_text,status,create_time) VALUES ('0', '"+phone+"','"+context+"','0', NOW())";
		commonDAO.saveEntity(sql);
	}
}
