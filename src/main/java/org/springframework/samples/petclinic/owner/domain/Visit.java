package org.springframework.samples.petclinic.owner.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Embeddable
public class Visit {
	@Column(name = "visit_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotEmpty
	private String description;
}
