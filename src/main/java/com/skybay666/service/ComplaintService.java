package com.skybay666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skybay666.domain.Complaint;
import com.skybay666.dto.ComplaintDTO;
import com.skybay666.dto.ComplaintSearchDTO;
import com.skybay666.dto.ComplaintPageDTO;
import com.skybay666.dto.ComplaintConvertCriteriaDTO;
import com.skybay666.service.GenericService;
import com.skybay666.dto.common.RequestDTO;
import com.skybay666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ComplaintService extends GenericService<Complaint, Integer> {

	List<Complaint> findAll();

	ResultDTO addComplaint(ComplaintDTO complaintDTO, RequestDTO requestDTO);

	ResultDTO updateComplaint(ComplaintDTO complaintDTO, RequestDTO requestDTO);

    Page<Complaint> getAllComplaints(Pageable pageable);

    Page<Complaint> getAllComplaints(Specification<Complaint> spec, Pageable pageable);

	ResponseEntity<ComplaintPageDTO> getComplaints(ComplaintSearchDTO complaintSearchDTO);
	
	List<ComplaintDTO> convertComplaintsToComplaintDTOs(List<Complaint> complaints, ComplaintConvertCriteriaDTO convertCriteria);

	ComplaintDTO getComplaintDTOById(Integer complaintId);







}





