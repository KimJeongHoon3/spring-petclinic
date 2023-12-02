package org.springframework.samples.petclinic.owner.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.owner.PetType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Embeddable
public class Pet {
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@OneToOne
	@JoinColumn(name = "type_id") // type_id 라는 컬럼이 없는데, 알아서 생성되나?
	private PetType type;

//	@ElementCollection(fetch = FetchType.EAGER)
//	@CollectionTable(name = "visits", joinColumns = @JoinColumn(name = "pet_idx")) // 요거 좀 말이 안될듯.. join을 owner_id도 같이 걸어야하나?
//	@OrderBy("visit_date ASC")
//	private Set<Visit> visits;
}
