package com.programmers.heavenpay.member.dto.response;

import com.programmers.heavenpay.member.entity.vo.GenderType;

public class MemberGetOneResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String birth;
    private final GenderType gender;

    public MemberGetOneResponse(Long id, String email, String name, String phoneNumber, String birth, GenderType gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
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

    public static MemberGetOneResponse.MemberGetOneResponseBuilder builder() {
        return new MemberGetOneResponse.MemberGetOneResponseBuilder();
    }

    public static class MemberGetOneResponseBuilder {
        private Long id;
        private String email;
        private String name;
        private String phoneNumber;
        private String birth;
        private GenderType gender;

        private MemberGetOneResponseBuilder() {
        }

        public MemberGetOneResponse.MemberGetOneResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public MemberGetOneResponse.MemberGetOneResponseBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public MemberGetOneResponse.MemberGetOneResponseBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public MemberGetOneResponse.MemberGetOneResponseBuilder phoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public MemberGetOneResponse.MemberGetOneResponseBuilder birth(final String birth) {
            this.birth = birth;
            return this;
        }

        public MemberGetOneResponse.MemberGetOneResponseBuilder gender(final GenderType gender) {
            this.gender = gender;
            return this;
        }

        public MemberGetOneResponse build() {
            return new MemberGetOneResponse(this.id, this.email, this.name, this.phoneNumber, this.birth, this.gender);
        }
    }
}
