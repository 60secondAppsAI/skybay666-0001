package com.skybay666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skybay666.domain.Maintenance;
import com.skybay666.dto.MaintenanceDTO;
import com.skybay666.dto.MaintenanceSearchDTO;
import com.skybay666.dto.MaintenancePageDTO;
import com.skybay666.dto.MaintenanceConvertCriteriaDTO;
import com.skybay666.service.GenericService;
import com.skybay666.dto.common.RequestDTO;
import com.skybay666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MaintenanceService extends GenericService<Maintenance, Integer> {

	List<Maintenance> findAll();

	ResultDTO addMaintenance(MaintenanceDTO maintenanceDTO, RequestDTO requestDTO);

	ResultDTO updateMaintenance(MaintenanceDTO maintenanceDTO, RequestDTO requestDTO);

    Page<Maintenance> getAllMaintenances(Pageable pageable);

    Page<Maintenance> getAllMaintenances(Specification<Maintenance> spec, Pageable pageable);

	ResponseEntity<MaintenancePageDTO> getMaintenances(MaintenanceSearchDTO maintenanceSearchDTO);
	
	List<MaintenanceDTO> convertMaintenancesToMaintenanceDTOs(List<Maintenance> maintenances, MaintenanceConvertCriteriaDTO convertCriteria);

	MaintenanceDTO getMaintenanceDTOById(Integer maintenanceId);







}





