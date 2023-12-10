package org.springframework.samples.petclinic.owner.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity(name = "owner_ddd")
@Table(name = "owners_ddd")
@Access(AccessType.FIELD) // 이를 생략하게되면 @Id나 @EmbeddedId가 어디에 위치했느냐에 따라 접근 방식을 결정. 즉, 필드에 해당 어노테이션이 있으면 필드접근방식, 메서드에 위치하면 메서드 접근방식
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Owner {
	@EmbeddedId
	private OwnerId id;
	@Embedded
	private OwnerName name;
	private String address;
	private String city;
	private String telephone;

	@ElementCollection
	@CollectionTable(name = "pets_ddd")
	@OrderColumn(name = "pet_idx")
	private List<Pet> pets = Collections.emptyList();

	@Builder
	public Owner(OwnerId id, OwnerName name, String address, String city, String telephone) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.telephone = telephone;
	}

	public void changePets(List<Pet> newPets) {
		this.pets.clear();
		pets.addAll(newPets);
	}

//	public List<Pet> getPets() {
//		return pets.isEmpty() ? pets : pets.to;
//	}
}
