package com.skybay666.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;



@Entity
@Table(name="promotions")
@Getter @Setter @NoArgsConstructor
public class Promotion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="promotion_id")
	private Integer promotionId;
    
  	@Column(name="details")
	private String details;
    
  	@Column(name="discount_percentage")
	private double discountPercentage;
    
  	@Column(name="valid_until")
	private Date validUntil;
    
	




}
