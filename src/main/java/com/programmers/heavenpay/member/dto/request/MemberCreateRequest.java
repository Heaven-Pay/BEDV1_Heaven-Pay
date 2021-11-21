package com.programmers.heavenpay.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberCreateRequest {
    @NotBlank(message = "이메일은 공백이 아니여야 합니다")
    @Email
    private String email;

    @Pattern(regexp="^[ㄱ-ㅎ|가-힣|a-z|A-Z|]{2,20}$", message="이름은 공백없는 2~20자이어야 합니다")
    private String name;

    @Pattern(regexp="[0-9]{3}[0-9]{3,4}[0-9]{4}", message="'-'없이 입력해 주세요")
    private String phoneNumber;

    @Pattern(regexp="[0-9]{8}", message="'yyyymmdd'형식으로 작성해주세요")
    private String birth;

    @Pattern(regexp="^[ㄱ-ㅎ|가-힣|a-z|A-Z]{1,10}$", message="성별은 1~10자이어야 합니다")
    private String gender;

    public MemberCreateRequest(String email, String name, String phoneNumber, String birth, String gender) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
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

    public String getGender() {
        return gender;
    }

    public static MemberCreateRequest.MemberCreateRequestBuilder builder() {
        return new MemberCreateRequest.MemberCreateRequestBuilder();
    }

    public static class MemberCreateRequestBuilder {
        private String email;
        private String name;
        private String phoneNumber;
        private String birth;
        private String gender;

        private MemberCreateRequestBuilder() {
        }

        public MemberCreateRequest.MemberCreateRequestBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public MemberCreateRequest.MemberCreateRequestBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public MemberCreateRequest.MemberCreateRequestBuilder phoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public MemberCreateRequest.MemberCreateRequestBuilder birth(final String birth) {
            this.birth = birth;
            return this;
        }

        public MemberCreateRequest.MemberCreateRequestBuilder gender(final String gender) {
            this.gender = gender;
            return this;
        }

        public MemberCreateRequest build() {
            return new MemberCreateRequest(this.email, this.name, this.phoneNumber, this.birth, this.gender);
        }
    }
}
