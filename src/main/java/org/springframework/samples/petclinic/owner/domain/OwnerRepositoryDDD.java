package org.springframework.samples.petclinic.owner.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;


public interface OwnerRepositoryDDD extends JpaRepository<Owner, OwnerId> {
	default OwnerId nextOwnerId() {
		int randomNo = ThreadLocalRandom.current().nextInt(900000) + 100000;
		String number = String.format("%tY%<tm%<td%<tH-%d", new Date(), randomNo); // "<" 요건 앞서 사용한 인덱스와 같은 인덱스를 사용하겠다는것.. 즉, new Data 요거
		return new OwnerId(number);
	}
}
