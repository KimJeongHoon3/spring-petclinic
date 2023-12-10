package org.springframework.samples.petclinic.owner.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.owner.PetType;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Pet {
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@OneToOne/*(fetch = FetchType.LAZY)*/
	@JoinColumn(name = "type_id") // type_id 라는 컬럼이 없는데, 알아서 생성되나?
	private PetType type;

//	@ElementCollection(fetch = FetchType.EAGER) // @Embeddable 선언한곳에는 ElementCollection을 사용할 수 없다.. jpa 명세..
//	@CollectionTable(name = "visits_ddd")
//	@OrderBy("visit_date ASC")
//	private Set<Visit> visits;
}
