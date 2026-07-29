package com.golfclub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "members")
@Getter
@Setter


public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String memberName;
    private String memberAddress;

    @Email
    private String memberEmailAddress;
    private String memberPhoneNumber;
    private LocalDate membershipStartDate;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;
}