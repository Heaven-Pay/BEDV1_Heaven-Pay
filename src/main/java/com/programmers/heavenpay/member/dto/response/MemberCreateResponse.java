package com.programmers.heavenpay.member.dto.response;

public class MemberCreateResponse {
    private final Long id;

    public Long getId() {
        return id;
    }

    public MemberCreateResponse(Long id) {
        this.id = id;
    }

    public static MemberCreateResponse.MemberCreateResponseBuilder builder() {
        return new MemberCreateResponse.MemberCreateResponseBuilder();
    }

    public static class MemberCreateResponseBuilder {
        private Long id;

        private MemberCreateResponseBuilder() {
        }

        public MemberCreateResponse.MemberCreateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public MemberCreateResponse build() {
            return new MemberCreateResponse(this.id);
        }
    }
}
