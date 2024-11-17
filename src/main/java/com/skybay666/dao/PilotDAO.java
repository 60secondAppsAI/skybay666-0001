package com.skybay666.dao;

import java.util.List;

import com.skybay666.dao.GenericDAO;
import com.skybay666.domain.Pilot;





public interface PilotDAO extends GenericDAO<Pilot, Integer> {
  
	List<Pilot> findAll();
	






}


