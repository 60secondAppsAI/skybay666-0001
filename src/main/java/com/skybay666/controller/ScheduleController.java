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

import com.skybay666.domain.Schedule;
import com.skybay666.dto.ScheduleDTO;
import com.skybay666.dto.ScheduleSearchDTO;
import com.skybay666.dto.SchedulePageDTO;
import com.skybay666.service.ScheduleService;
import com.skybay666.dto.common.RequestDTO;
import com.skybay666.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/schedule")
@RestController
public class ScheduleController {

	private final static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

	@Autowired
	ScheduleService scheduleService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Schedule> getAll() {

		List<Schedule> schedules = scheduleService.findAll();
		
		return schedules;	
	}

	@GetMapping(value = "/{scheduleId}")
	@ResponseBody
	public ScheduleDTO getSchedule(@PathVariable Integer scheduleId) {
		
		return (scheduleService.getScheduleDTOById(scheduleId));
	}

 	@RequestMapping(value = "/addSchedule", method = RequestMethod.POST)
	public ResponseEntity<?> addSchedule(@RequestBody ScheduleDTO scheduleDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = scheduleService.addSchedule(scheduleDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/schedules")
	public ResponseEntity<SchedulePageDTO> getSchedules(ScheduleSearchDTO scheduleSearchDTO) {
 
		return scheduleService.getSchedules(scheduleSearchDTO);
	}	

	@RequestMapping(value = "/updateSchedule", method = RequestMethod.POST)
	public ResponseEntity<?> updateSchedule(@RequestBody ScheduleDTO scheduleDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = scheduleService.updateSchedule(scheduleDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
