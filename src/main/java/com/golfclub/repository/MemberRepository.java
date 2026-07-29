package com.golfclub.repository;

import com.golfclub.model.Member;
import com.golfclub.model.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByMemberNameContainingIgnoreCase(String memberName);
    List<Member> findByMembershipType(MembershipType membershipType);
    List<Member> findByMemberPhoneNumber(String memberPhoneNumber);

    @Query("SELECT m FROM Tournament t JOIN t.participatingMembers m WHERE t.startDate = :startDate")
    List<Member> findByTournamentStartDate(@Param("startDate") LocalDate startDate);
};