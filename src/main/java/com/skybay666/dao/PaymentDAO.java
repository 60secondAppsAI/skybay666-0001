package com.skybay666.dao;

import java.util.List;

import com.skybay666.dao.GenericDAO;
import com.skybay666.domain.Payment;





public interface PaymentDAO extends GenericDAO<Payment, Integer> {
  
	List<Payment> findAll();
	






}


