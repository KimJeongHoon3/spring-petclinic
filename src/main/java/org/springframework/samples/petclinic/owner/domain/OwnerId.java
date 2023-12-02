package org.springframework.samples.petclinic.owner.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@ToString
public class OwnerId implements Serializable {

	private static final long serialVersionUID = 5423438863232356375L;

//	@Column(name = "owner_id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY) // 의미를 명확히 하기위해서 OwnerId 라는 값 객체를 별도로 두었으나, GenerateValue는 사용불가.. 외부에서 uuid를 만드는게 필요
	private String id;
}
