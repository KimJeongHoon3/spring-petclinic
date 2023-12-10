package org.springframework.samples.petclinic.owner.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

//@Entity(name = "visit_ddd")
//@Table(name = "visits_ddd")
//public class Visit {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private String id;
//
////	private PetId petId;
//
//	@Embedded
//	private OwnerId ownerId;
//
//	@Column(name = "visit_date")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private LocalDate date;
//
//	@NotEmpty
//	private String description;
//
//}
