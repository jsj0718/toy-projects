package com.elevenhelevenm.practice.board.domain.membership.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Membership {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    @Setter
    @Column(nullable = false)
    @ColumnDefault(value = "0")
    private int point;

}
