package org.springframework.samples.petclinic.owner.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@ToString
public class OwnerName {
	private String firstName;
	private String lastName;
}
