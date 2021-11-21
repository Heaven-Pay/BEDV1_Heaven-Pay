package com.programmers.heavenpay.member.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.NotNull;

public class MemberGetOneRequest {
    @NotNull(message = "아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private final Long id;

    public MemberGetOneRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static MemberGetOneRequest.MemberGetOneRequestBuilder builder() {
        return new MemberGetOneRequest.MemberGetOneRequestBuilder();
    }

    public static class MemberGetOneRequestBuilder {
        private Long id;

        private MemberGetOneRequestBuilder() {
        }

        public MemberGetOneRequest.MemberGetOneRequestBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public MemberGetOneRequest build() {
            return new MemberGetOneRequest(this.id);
        }
    }
}
