package com.skybay666.dao;

import java.util.List;

import com.skybay666.dao.GenericDAO;
import com.skybay666.domain.Terminal;





public interface TerminalDAO extends GenericDAO<Terminal, Integer> {
  
	List<Terminal> findAll();
	






}


