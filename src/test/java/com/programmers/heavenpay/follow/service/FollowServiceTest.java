package com.programmers.heavenpay.follow.service;

import com.programmers.heavenpay.follow.entity.Follow;
import com.programmers.heavenpay.follow.repository.FollowRepository;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import com.programmers.heavenpay.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
    private final Long MEMBER_ID = 1L;
    private final Long FOLLOWER_ID = 1L;

    @InjectMocks
    private FollowService followService;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private MemberRepository memberRepository;

    private Member member = Member.builder()
            .id(MEMBER_ID)
            .email("wrjs@naver.com")
            .name("김동건")
            .phoneNumber("01031829709")
            .birth("19970908")
            .gender(GenderType.MALE)
            .build();

    private Member follower = Member.builder()
            .id(FOLLOWER_ID)
            .id(MEMBER_ID)
            .email("wrjss@naver.com")
            .name("김동")
            .phoneNumber("01031829809")
            .birth("19970908")
            .gender(GenderType.MALE)
            .build();

    private Follow follow = new Follow(member, member);

    @Test
    void 친구추가() {
        // when
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

        // given
        followService.follow(MEMBER_ID, FOLLOWER_ID);

        // then
        verify(followRepository).save(follow);
    }

    @Test
    void 친구삭제() {
        // when
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

        // given
        followService.unfollow(MEMBER_ID, FOLLOWER_ID);

        // then
        verify(followRepository).deleteByToMemberAndFromMember(member, member);
    }
}