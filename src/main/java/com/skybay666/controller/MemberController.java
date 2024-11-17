package com.skybay666.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.skybay666.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.skybay666.domain.Member;
import com.skybay666.dto.MemberDTO;
import com.skybay666.dto.MemberSearchDTO;
import com.skybay666.dto.MemberPageDTO;
import com.skybay666.service.MemberService;
import com.skybay666.dto.common.RequestDTO;
import com.skybay666.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/member")
@RestController
public class MemberController {

	private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberService memberService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Member> getAll() {

		List<Member> members = memberService.findAll();
		
		return members;	
	}

	@GetMapping(value = "/{memberId}")
	@ResponseBody
	public MemberDTO getMember(@PathVariable Integer memberId) {
		
		return (memberService.getMemberDTOById(memberId));
	}

 	@RequestMapping(value = "/addMember", method = RequestMethod.POST)
	public ResponseEntity<?> addMember(@RequestBody MemberDTO memberDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = memberService.addMember(memberDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/members")
	public ResponseEntity<MemberPageDTO> getMembers(MemberSearchDTO memberSearchDTO) {
 
		return memberService.getMembers(memberSearchDTO);
	}	

	@RequestMapping(value = "/updateMember", method = RequestMethod.POST)
	public ResponseEntity<?> updateMember(@RequestBody MemberDTO memberDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = memberService.updateMember(memberDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
