package com.elevenhelevenm.practice.board.domain.membership.repository;

import com.elevenhelevenm.practice.board.domain.membership.model.Membership;
import com.elevenhelevenm.practice.board.domain.membership.model.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Optional<Membership> findByUserIdAndMembershipType(String userId, MembershipType membershipType);

    List<Membership> findAllByUserId(String userId);
}
