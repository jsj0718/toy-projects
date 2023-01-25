package com.elevenhelevenm.practice.board.domain.membership.controller;

import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipDetailResponseDto;
import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipRequestDto;
import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipAddResponseDto;
import com.elevenhelevenm.practice.board.domain.membership.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MembershipController {

    private static final String USER_ID_HEADER = "X-USER-ID";

    private final MembershipService membershipService;

    @PostMapping("/api/v1/memberships")
    public ResponseEntity<MembershipAddResponseDto> saveMembership(
            @RequestHeader(USER_ID_HEADER) String userId,
            @RequestBody @Valid MembershipRequestDto membershipRequestDto) {

        MembershipAddResponseDto responseDto = membershipService.addMembership(userId, membershipRequestDto.getMembershipType(), membershipRequestDto.getPoint());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping("/api/v1/memberships")
    public ResponseEntity<List<MembershipDetailResponseDto>> getMemberships(@RequestHeader(USER_ID_HEADER) String userId) {

        List<MembershipDetailResponseDto> memberships = membershipService.getMemberships(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberships);
    }

    @GetMapping("/api/v1/memberships/{membershipId}")
    public ResponseEntity<MembershipDetailResponseDto> getMembership(
            @RequestHeader(USER_ID_HEADER) String userId,
            @PathVariable("membershipId") Long membershipId) {

        MembershipDetailResponseDto responseDto = membershipService.getMembership(membershipId, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @DeleteMapping("/api/v1/memberships/{membershipId}")
    public ResponseEntity<Void> removeMembership(
            @RequestHeader(USER_ID_HEADER) String userId,
            @PathVariable("membershipId") Long membershipId
    ) {
        membershipService.removeMembership(membershipId, userId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/api/v1/memberships/accumulate/{membershipId}")
    public ResponseEntity<Void> accumulateMembership(
            @RequestHeader(USER_ID_HEADER) String userId,
            @PathVariable("membershipId") Long membershipId,
            @RequestBody @Valid MembershipRequestDto requestDto
    ) {
        membershipService.accumulateMembershipPoint(membershipId, userId, requestDto.getPoint());

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
