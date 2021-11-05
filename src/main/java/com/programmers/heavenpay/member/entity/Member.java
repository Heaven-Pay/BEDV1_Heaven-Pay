package com.programmers.heavenpay.member.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "member_email", nullable = false)
    private String email;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "member_birth", nullable = false)
    private String birth;

    @Column(name = "member_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    public void changeValues(String email, String name, String phoneNumber, String birth, String gender) {
        this.email = email.isBlank() ? this.email : email;
        this.name = name.isBlank() ? this.name : name;
        this.phoneNumber = phoneNumber.isBlank() ? this.phoneNumber : phoneNumber;
        this.birth = birth.isBlank() ? this.birth : birth;
        this.gender = gender.isBlank() ? this.gender : GenderType.of(gender);
    }
}