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

import com.skybay666.domain.Promotion;
import com.skybay666.dto.PromotionDTO;
import com.skybay666.dto.PromotionSearchDTO;
import com.skybay666.dto.PromotionPageDTO;
import com.skybay666.service.PromotionService;
import com.skybay666.dto.common.RequestDTO;
import com.skybay666.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/promotion")
@RestController
public class PromotionController {

	private final static Logger logger = LoggerFactory.getLogger(PromotionController.class);

	@Autowired
	PromotionService promotionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Promotion> getAll() {

		List<Promotion> promotions = promotionService.findAll();
		
		return promotions;	
	}

	@GetMapping(value = "/{promotionId}")
	@ResponseBody
	public PromotionDTO getPromotion(@PathVariable Integer promotionId) {
		
		return (promotionService.getPromotionDTOById(promotionId));
	}

 	@RequestMapping(value = "/addPromotion", method = RequestMethod.POST)
	public ResponseEntity<?> addPromotion(@RequestBody PromotionDTO promotionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = promotionService.addPromotion(promotionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/promotions")
	public ResponseEntity<PromotionPageDTO> getPromotions(PromotionSearchDTO promotionSearchDTO) {
 
		return promotionService.getPromotions(promotionSearchDTO);
	}	

	@RequestMapping(value = "/updatePromotion", method = RequestMethod.POST)
	public ResponseEntity<?> updatePromotion(@RequestBody PromotionDTO promotionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = promotionService.updatePromotion(promotionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
