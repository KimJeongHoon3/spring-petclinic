package org.springframework.samples.petclinic.owner.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "owners")
@Access(AccessType.FIELD) // 이를 생략하게되면 @Id나 @EmbeddedId가 어디에 위치했느냐에 따라 접근 방식을 결정. 즉, 필드에 해당 어노테이션이 있으면 필드접근방식, 메서드에 위치하면 메서드 접근방식
public class Owner {
	@EmbeddedId
	private OwnerId id;
	@Embedded
	private OwnerName name;
	private String address;
	private String city;
	private String telephone;
	private List<Pet> pet;
}
