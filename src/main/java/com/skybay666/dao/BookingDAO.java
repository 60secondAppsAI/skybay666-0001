package com.skybay666.dao;

import java.util.List;

import com.skybay666.dao.GenericDAO;
import com.skybay666.domain.Booking;





public interface BookingDAO extends GenericDAO<Booking, Integer> {
  
	List<Booking> findAll();
	






}


