package com.qjs.task.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qjs.task.model.JobResultBean;

@Repository
public interface JobResultDao extends JpaRepository<JobResultBean, Serializable> {
 
 
}