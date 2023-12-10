package org.springframework.samples.petclinic.owner.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Transactional
class OwnerRepositoryDDDTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private OwnerRepositoryDDD ownerRepositoryDDD;

	OwnerId ownerId;
	@BeforeEach
	void init() {
		PetType cat = new PetType(); // petclinic에서 제공해준대로 따라가지만, enum으로 만들어서 관리하는게 더 좋을듯
		cat.setName("cat");
		PetType dog = new PetType();
		dog.setName("dog");

		entityManager.persist(cat);
		entityManager.persist(dog);

		ownerId = ownerRepositoryDDD.nextOwnerId();

		Owner owner = Owner.builder()
			.id(ownerId)
			.telephone("010")
			.city("yong-in")
			.address("gyung-gi")
			.name(new OwnerName("jeonghun","kim"))
			.build();

		ownerRepositoryDDD.save(owner);
		ownerRepositoryDDD.flush();
		entityManager.clear(); // 1차 캐시가 아닌, DB에서 가져오게하기위해 영속성 캐시 클리어~

	}

	@Test
	void test_owner_조회() {

		Owner find = ownerRepositoryDDD.findById(ownerId).get();

		System.out.println(find);
		assertThat(ownerId).isEqualTo(find.getId());
	}

	@Test
	void test_owner_pet추가() {
		Owner owner = ownerRepositoryDDD.getById(ownerId);
		addPets(owner);

		Owner findOwner = ownerRepositoryDDD.findById(ownerId).get();

		assertFalse(owner == findOwner);
		List<Pet> findOwnerPets = findOwner.getPets();
		assertThat(findOwnerPets).hasSize(4);
		assertThat(findOwnerPets).extracting(Pet::getName)
			.contains("개냥이", "강아지", "강아지2", "강아지3");
	}

	private void addPets(Owner owner) {
		List<Pet> pets = makePets();

		owner.changePets(pets);

		ownerRepositoryDDD.flush();
		entityManager.clear();
	}

	private List<Pet> makePets() {
		Pet pet1 = Pet.builder()
			.birthDate(LocalDate.of(2023, 10, 11))
			.type(entityManager.find(PetType.class, 1))
			.name("개냥이")
			.build();
		Pet pet2 = Pet.builder()
			.birthDate(LocalDate.of(2023, 10, 13))
			.type(entityManager.find(PetType.class, 2))
			.name("강아지")
			.build();
		Pet pet3 = Pet.builder()
			.birthDate(LocalDate.of(2023, 1, 13))
			.type(entityManager.find(PetType.class, 2))
			.name("강아지2")
			.build();
		Pet pet4 = Pet.builder()
			.birthDate(LocalDate.of(2023, 2, 13))
			.type(entityManager.find(PetType.class, 2))
			.name("강아지3")
			.build();

		return Arrays.asList(pet1, pet2, pet3, pet4);
	}

	@Test
	// delete 쿼리 3번 + update 쿼리 1번 실행됨..
	// 단, pets_ddd 테이블에 별도 idx를 추가하지않으면(pets_ddd 테이블에 pk가 owner_ddd_id 하나) 모두 delete 날리고 하나만 추가.. 무엇을 제거해야하는지 알 수 없으니 당연하겠지
	void test_owner_pet변경_기존에_데이터_있는_상태에서_Pet변경() {
		Owner owner = ownerRepositoryDDD.getById(ownerId);
		addPets(owner);

		Owner findOwner = ownerRepositoryDDD.findById(ownerId).get();

		Pet pet1 = Pet.builder()
			.birthDate(LocalDate.of(2023, 1, 11))
			.type(entityManager.find(PetType.class, 1))
			.name("개냥이")
			.build();

		findOwner.changePets(Arrays.asList(pet1));
//		findOwner.changePets(Collections.emptyList()); // 모두 제거할때는 한번에 모두 Delete 쿼리 한번만 날림..

		ownerRepositoryDDD.flush();
		entityManager.clear();

		List<Pet> findOwnerPets = ownerRepositoryDDD.findById(ownerId).get().getPets();
		assertThat(findOwnerPets).hasSize(1);
		assertThat(findOwnerPets).extracting(Pet::getName)
			.contains("개냥이");
		assertThat(findOwnerPets).extracting(Pet::getBirthDate)
			.contains(LocalDate.of(2023,1,11));

	}

	@Test
	void test_owner_제거시_elementCollection선언된_대상_모두_지워진다() {
		Owner owner = ownerRepositoryDDD.findById(ownerId).get();
		addPets(owner);

		Owner findOwner = ownerRepositoryDDD.findById(ownerId).get();

		ownerRepositoryDDD.delete(findOwner);
		ownerRepositoryDDD.flush(); // pets_ddd와 owners_ddd 모두 owners_ddd의 id 조건으로 지운다.. delete 쿼리는 테이블당 한번씩. 총 두번
		entityManager.clear();

		assertThat(ownerRepositoryDDD.findById(ownerId)).isEmpty();
	}

}
