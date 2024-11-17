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
import com.skybay666.dao.PassengerDAO;
import com.skybay666.domain.Passenger;
import com.skybay666.dto.PassengerDTO;
import com.skybay666.dto.PassengerSearchDTO;
import com.skybay666.dto.PassengerPageDTO;
import com.skybay666.dto.PassengerConvertCriteriaDTO;
import com.skybay666.dto.common.RequestDTO;
import com.skybay666.dto.common.ResultDTO;
import com.skybay666.service.PassengerService;
import com.skybay666.util.ControllerUtils;





@Service
public class PassengerServiceImpl extends GenericServiceImpl<Passenger, Integer> implements PassengerService {

    private final static Logger logger = LoggerFactory.getLogger(PassengerServiceImpl.class);

	@Autowired
	PassengerDAO passengerDao;

	


	@Override
	public GenericDAO<Passenger, Integer> getDAO() {
		return (GenericDAO<Passenger, Integer>) passengerDao;
	}
	
	public List<Passenger> findAll () {
		List<Passenger> passengers = passengerDao.findAll();
		
		return passengers;	
		
	}

	public ResultDTO addPassenger(PassengerDTO passengerDTO, RequestDTO requestDTO) {

		Passenger passenger = new Passenger();

		passenger.setPassengerId(passengerDTO.getPassengerId());


		passenger.setName(passengerDTO.getName());


		passenger.setPassportNumber(passengerDTO.getPassportNumber());


		passenger.setContactInfo(passengerDTO.getContactInfo());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		passenger = passengerDao.save(passenger);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Passenger> getAllPassengers(Pageable pageable) {
		return passengerDao.findAll(pageable);
	}

	public Page<Passenger> getAllPassengers(Specification<Passenger> spec, Pageable pageable) {
		return passengerDao.findAll(spec, pageable);
	}

	public ResponseEntity<PassengerPageDTO> getPassengers(PassengerSearchDTO passengerSearchDTO) {
	
			Integer passengerId = passengerSearchDTO.getPassengerId(); 
 			String name = passengerSearchDTO.getName(); 
 			String passportNumber = passengerSearchDTO.getPassportNumber(); 
 			String contactInfo = passengerSearchDTO.getContactInfo(); 
 			String sortBy = passengerSearchDTO.getSortBy();
			String sortOrder = passengerSearchDTO.getSortOrder();
			String searchQuery = passengerSearchDTO.getSearchQuery();
			Integer page = passengerSearchDTO.getPage();
			Integer size = passengerSearchDTO.getSize();

	        Specification<Passenger> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, passengerId, "passengerId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, passportNumber, "passportNumber"); 
			
			spec = ControllerUtils.andIfNecessary(spec, contactInfo, "contactInfo"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("passportNumber")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("contactInfo")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Passenger> passengers = this.getAllPassengers(spec, pageable);
		
		//System.out.println(String.valueOf(passengers.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(passengers.getTotalPages()));
		
		List<Passenger> passengersList = passengers.getContent();
		
		PassengerConvertCriteriaDTO convertCriteria = new PassengerConvertCriteriaDTO();
		List<PassengerDTO> passengerDTOs = this.convertPassengersToPassengerDTOs(passengersList,convertCriteria);
		
		PassengerPageDTO passengerPageDTO = new PassengerPageDTO();
		passengerPageDTO.setPassengers(passengerDTOs);
		passengerPageDTO.setTotalElements(passengers.getTotalElements());
		return ResponseEntity.ok(passengerPageDTO);
	}

	public List<PassengerDTO> convertPassengersToPassengerDTOs(List<Passenger> passengers, PassengerConvertCriteriaDTO convertCriteria) {
		
		List<PassengerDTO> passengerDTOs = new ArrayList<PassengerDTO>();
		
		for (Passenger passenger : passengers) {
			passengerDTOs.add(convertPassengerToPassengerDTO(passenger,convertCriteria));
		}
		
		return passengerDTOs;

	}
	
	public PassengerDTO convertPassengerToPassengerDTO(Passenger passenger, PassengerConvertCriteriaDTO convertCriteria) {
		
		PassengerDTO passengerDTO = new PassengerDTO();
		
		passengerDTO.setPassengerId(passenger.getPassengerId());

	
		passengerDTO.setName(passenger.getName());

	
		passengerDTO.setPassportNumber(passenger.getPassportNumber());

	
		passengerDTO.setContactInfo(passenger.getContactInfo());

	

		
		return passengerDTO;
	}

	public ResultDTO updatePassenger(PassengerDTO passengerDTO, RequestDTO requestDTO) {
		
		Passenger passenger = passengerDao.getById(passengerDTO.getPassengerId());

		passenger.setPassengerId(ControllerUtils.setValue(passenger.getPassengerId(), passengerDTO.getPassengerId()));

		passenger.setName(ControllerUtils.setValue(passenger.getName(), passengerDTO.getName()));

		passenger.setPassportNumber(ControllerUtils.setValue(passenger.getPassportNumber(), passengerDTO.getPassportNumber()));

		passenger.setContactInfo(ControllerUtils.setValue(passenger.getContactInfo(), passengerDTO.getContactInfo()));



        passenger = passengerDao.save(passenger);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PassengerDTO getPassengerDTOById(Integer passengerId) {
	
		Passenger passenger = passengerDao.getById(passengerId);
			
		
		PassengerConvertCriteriaDTO convertCriteria = new PassengerConvertCriteriaDTO();
		return(this.convertPassengerToPassengerDTO(passenger,convertCriteria));
	}







}
