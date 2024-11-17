package com.skybay666.dao;

import java.util.List;

import com.skybay666.dao.GenericDAO;
import com.skybay666.domain.Schedule;





public interface ScheduleDAO extends GenericDAO<Schedule, Integer> {
  
	List<Schedule> findAll();
	






}


