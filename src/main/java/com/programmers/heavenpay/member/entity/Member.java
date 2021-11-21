package com.programmers.heavenpay.member.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.member.entity.vo.GenderType;

import javax.persistence.*;

@Entity
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

    public Member(){
    }

    public Member(Long id, String email, String name, String phoneNumber, String birth, GenderType gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
    }

    public Member(String email, String name, String phoneNumber, String birth, GenderType gender) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
    }

    public void changeValues(String email, String name, String phoneNumber, String birth, String gender) {
        this.email = email.isBlank() ? this.email : email;
        this.name = name.isBlank() ? this.name : name;
        this.phoneNumber = phoneNumber.isBlank() ? this.phoneNumber : phoneNumber;
        this.birth = birth.isBlank() ? this.birth : birth;
        this.gender = gender.isBlank() ? this.gender : GenderType.of(gender);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirth() {
        return birth;
    }

    public GenderType getGender() {
        return gender;
    }

    public static Member.MemberBuilder builder() {
        return new Member.MemberBuilder();
    }

    public static class MemberBuilder {
        private Long id;
        private String email;
        private String name;
        private String phoneNumber;
        private String birth;
        private GenderType gender;

        private MemberBuilder() {
        }

        public Member.MemberBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Member.MemberBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public Member.MemberBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public Member.MemberBuilder phoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Member.MemberBuilder birth(final String birth) {
            this.birth = birth;
            return this;
        }

        public Member.MemberBuilder gender(final GenderType gender) {
            this.gender = gender;
            return this;
        }

        public Member build() {
            return new Member(this.id, this.email, this.name, this.phoneNumber, this.birth, this.gender);
        }
    }
}