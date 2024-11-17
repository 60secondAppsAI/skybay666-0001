package com.skybay666.dao;

import java.util.List;

import com.skybay666.dao.GenericDAO;
import com.skybay666.domain.Flight;





public interface FlightDAO extends GenericDAO<Flight, Integer> {
  
	List<Flight> findAll();
	






}


