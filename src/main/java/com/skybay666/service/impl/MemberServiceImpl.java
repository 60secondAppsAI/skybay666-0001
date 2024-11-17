package com.skybay666.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.skybay666.dao.GenericDAO;
import com.skybay666.service.GenericService;
import com.skybay666.service.impl.GenericServiceImpl;
import com.skybay666.dao.MemberDAO;
import com.skybay666.domain.Member;
import com.skybay666.dto.MemberDTO;
import com.skybay666.dto.MemberSearchDTO;
import com.skybay666.dto.MemberPageDTO;
import com.skybay666.dto.MemberConvertCriteriaDTO;
import com.skybay666.dto.common.RequestDTO;
import com.skybay666.dto.common.ResultDTO;
import com.skybay666.service.MemberService;
import com.skybay666.util.ControllerUtils;





@Service
public class MemberServiceImpl extends GenericServiceImpl<Member, Integer> implements MemberService {

    private final static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Autowired
	MemberDAO memberDao;

	


	@Override
	public GenericDAO<Member, Integer> getDAO() {
		return (GenericDAO<Member, Integer>) memberDao;
	}
	
	public List<Member> findAll () {
		List<Member> members = memberDao.findAll();
		
		return members;	
		
	}

	public ResultDTO addMember(MemberDTO memberDTO, RequestDTO requestDTO) {

		Member member = new Member();

		member.setMemberId(memberDTO.getMemberId());


		member.setPoints(memberDTO.getPoints());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		member = memberDao.save(member);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Member> getAllMembers(Pageable pageable) {
		return memberDao.findAll(pageable);
	}

	public Page<Member> getAllMembers(Specification<Member> spec, Pageable pageable) {
		return memberDao.findAll(spec, pageable);
	}

	public ResponseEntity<MemberPageDTO> getMembers(MemberSearchDTO memberSearchDTO) {
	
			Integer memberId = memberSearchDTO.getMemberId(); 
  			String sortBy = memberSearchDTO.getSortBy();
			String sortOrder = memberSearchDTO.getSortOrder();
			String searchQuery = memberSearchDTO.getSearchQuery();
			Integer page = memberSearchDTO.getPage();
			Integer size = memberSearchDTO.getSize();

	        Specification<Member> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, memberId, "memberId"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Member> members = this.getAllMembers(spec, pageable);
		
		//System.out.println(String.valueOf(members.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(members.getTotalPages()));
		
		List<Member> membersList = members.getContent();
		
		MemberConvertCriteriaDTO convertCriteria = new MemberConvertCriteriaDTO();
		List<MemberDTO> memberDTOs = this.convertMembersToMemberDTOs(membersList,convertCriteria);
		
		MemberPageDTO memberPageDTO = new MemberPageDTO();
		memberPageDTO.setMembers(memberDTOs);
		memberPageDTO.setTotalElements(members.getTotalElements());
		return ResponseEntity.ok(memberPageDTO);
	}

	public List<MemberDTO> convertMembersToMemberDTOs(List<Member> members, MemberConvertCriteriaDTO convertCriteria) {
		
		List<MemberDTO> memberDTOs = new ArrayList<MemberDTO>();
		
		for (Member member : members) {
			memberDTOs.add(convertMemberToMemberDTO(member,convertCriteria));
		}
		
		return memberDTOs;

	}
	
	public MemberDTO convertMemberToMemberDTO(Member member, MemberConvertCriteriaDTO convertCriteria) {
		
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setMemberId(member.getMemberId());

	
		memberDTO.setPoints(member.getPoints());

	

		
		return memberDTO;
	}

	public ResultDTO updateMember(MemberDTO memberDTO, RequestDTO requestDTO) {
		
		Member member = memberDao.getById(memberDTO.getMemberId());

		member.setMemberId(ControllerUtils.setValue(member.getMemberId(), memberDTO.getMemberId()));

		member.setPoints(ControllerUtils.setValue(member.getPoints(), memberDTO.getPoints()));



        member = memberDao.save(member);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public MemberDTO getMemberDTOById(Integer memberId) {
	
		Member member = memberDao.getById(memberId);
			
		
		MemberConvertCriteriaDTO convertCriteria = new MemberConvertCriteriaDTO();
		return(this.convertMemberToMemberDTO(member,convertCriteria));
	}







}
