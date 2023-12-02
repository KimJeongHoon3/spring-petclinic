package org.springframework.samples.petclinic.owner.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OwnerRepositoryDDDTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private OwnerRepositoryDDD ownerRepositoryDDD;

//	@BeforeEach
//	void init() {
//
//	}

	@Test
	@Transactional
	void test_owner생성_저장_조회() {

		OwnerId ownerId = ownerRepositoryDDD.nextOwnerId();

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

		Owner find = ownerRepositoryDDD.getById(ownerId);

		System.out.println(find);
		assertThat(ownerId).isEqualTo(find.getId());
	}
}
