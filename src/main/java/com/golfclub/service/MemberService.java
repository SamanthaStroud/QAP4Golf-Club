package com.golfclub.service;

import com.golfclub.model.Member;
import com.golfclub.model.MembershipType;
import com.golfclub.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found: "
                        + id));
    }
    public List<Member> searchByName(String name) {
        return memberRepository.findByMemberNameContainingIgnoreCase(name);
    }
    public List<Member> searchByMembershipType(MembershipType type) {
        return memberRepository.findByMembershipType(type);
    }
    public List<Member> searchByPhoneNumber(String phoneNumber) {
        return memberRepository.findByMemberPhoneNumber(phoneNumber);
    }
    public List<Member> searchByTournamentStartDate(LocalDate startDate) {
        return memberRepository.findByTournamentStartDate(startDate);
    };
};