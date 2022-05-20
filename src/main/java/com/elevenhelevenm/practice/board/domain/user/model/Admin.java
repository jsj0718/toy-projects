package com.elevenhelevenm.practice.board.domain.user.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue("ADMIN")
@Entity
public class Admin extends UserV2 {

}
