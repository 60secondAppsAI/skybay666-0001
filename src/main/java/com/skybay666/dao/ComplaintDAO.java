package com.skybay666.dao;

import java.util.List;

import com.skybay666.dao.GenericDAO;
import com.skybay666.domain.Complaint;





public interface ComplaintDAO extends GenericDAO<Complaint, Integer> {
  
	List<Complaint> findAll();
	






}


