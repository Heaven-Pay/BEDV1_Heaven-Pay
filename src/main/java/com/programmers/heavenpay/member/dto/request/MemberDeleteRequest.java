package com.programmers.heavenpay.member.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.NotNull;

public class MemberDeleteRequest {
    @NotNull(message = "아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private final Long id;

    public MemberDeleteRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static MemberDeleteRequest.MemberDeleteRequestBuilder builder() {
        return new MemberDeleteRequest.MemberDeleteRequestBuilder();
    }

    public static class MemberDeleteRequestBuilder {
        private Long id;

        private MemberDeleteRequestBuilder() {
        }

        public MemberDeleteRequest.MemberDeleteRequestBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public MemberDeleteRequest build() {
            return new MemberDeleteRequest(this.id);
        }
    }
}
