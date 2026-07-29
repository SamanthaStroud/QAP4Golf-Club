package com.golfclub.controller;

import com.golfclub.model.Member;
import com.golfclub.model.MembershipType;
import com.golfclub.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    };
    @PostMapping
    public ResponseEntity<Member> addMember(@Valid @RequestBody Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.addMember(member));
    };
    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    };
    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    };
    @GetMapping("/search/name")
    public List<Member> searchByName(@RequestParam String name) {
        return memberService.searchByName(name);
    };
    @GetMapping("/search/membership-type")
    public List<Member> searchByMembershipType(@RequestParam MembershipType type) {
        return memberService.searchByMembershipType(type);
    };
    @GetMapping("/search/phone")
    public List<Member> searchByPhoneNumber(@RequestParam String phoneNumber) {
        return memberService.searchByPhoneNumber(phoneNumber);
    };
    @GetMapping("/search/tournament-start-date")
    public List<Member> searchByTournamentStartDate(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso =
                    org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return memberService.searchByTournamentStartDate(startDate);
    };
};