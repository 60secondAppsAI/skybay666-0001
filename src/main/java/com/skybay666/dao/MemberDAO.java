package com.skybay666.dao;

import java.util.List;

import com.skybay666.dao.GenericDAO;
import com.skybay666.domain.Member;





public interface MemberDAO extends GenericDAO<Member, Integer> {
  
	List<Member> findAll();
	






}


