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

import com.skybay666.domain.Maintenance;
import com.skybay666.dto.MaintenanceDTO;
import com.skybay666.dto.MaintenanceSearchDTO;
import com.skybay666.dto.MaintenancePageDTO;
import com.skybay666.service.MaintenanceService;
import com.skybay666.dto.common.RequestDTO;
import com.skybay666.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/maintenance")
@RestController
public class MaintenanceController {

	private final static Logger logger = LoggerFactory.getLogger(MaintenanceController.class);

	@Autowired
	MaintenanceService maintenanceService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Maintenance> getAll() {

		List<Maintenance> maintenances = maintenanceService.findAll();
		
		return maintenances;	
	}

	@GetMapping(value = "/{maintenanceId}")
	@ResponseBody
	public MaintenanceDTO getMaintenance(@PathVariable Integer maintenanceId) {
		
		return (maintenanceService.getMaintenanceDTOById(maintenanceId));
	}

 	@RequestMapping(value = "/addMaintenance", method = RequestMethod.POST)
	public ResponseEntity<?> addMaintenance(@RequestBody MaintenanceDTO maintenanceDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenanceService.addMaintenance(maintenanceDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/maintenances")
	public ResponseEntity<MaintenancePageDTO> getMaintenances(MaintenanceSearchDTO maintenanceSearchDTO) {
 
		return maintenanceService.getMaintenances(maintenanceSearchDTO);
	}	

	@RequestMapping(value = "/updateMaintenance", method = RequestMethod.POST)
	public ResponseEntity<?> updateMaintenance(@RequestBody MaintenanceDTO maintenanceDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenanceService.updateMaintenance(maintenanceDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
