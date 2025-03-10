package com.skybay666.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AircraftPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<AircraftDTO> aircrafts;
}





