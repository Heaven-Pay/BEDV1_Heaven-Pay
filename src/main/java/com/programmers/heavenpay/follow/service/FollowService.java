package com.programmers.heavenpay.follow.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.follow.dto.response.FollowFindResponse;
import com.programmers.heavenpay.follow.dto.response.FollowResponse;
import com.programmers.heavenpay.follow.entity.Follow;
import com.programmers.heavenpay.follow.entity.vo.FollowStatus;
import com.programmers.heavenpay.follow.repository.FollowRepository;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public FollowResponse follow(Long memberId, Long followerId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        Member follower = memberRepository.findById(followerId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );

        Follow followInstance = new Follow(member, follower);
        followRepository.save(followInstance);
        return new FollowResponse(FollowStatus.FOLLOWING, follower.getName());
    }

    @Transactional
    public FollowResponse unfollow(Long memberId, Long followerId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        Member follower = memberRepository.findById(followerId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        followRepository.deleteByToMemberAndFromMember(member, follower);
        return new FollowResponse(FollowStatus.UNFOLLOWING, follower.getName());
    }

    @Transactional
    public Page<FollowFindResponse> findFollow(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        return followRepository.findByToMember(member, pageable)
                .map(
                        follow -> new FollowFindResponse(
                                follow.getFromMember().getId(),
                                follow.getFromMember().getName()
                        )
                );
    }

    @Transactional
    public Page<FollowFindResponse> findFollower(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        return followRepository.findByFromMember(member, pageable)
                .map(
                        follow -> new FollowFindResponse(
                                follow.getFromMember().getId(),
                                follow.getFromMember().getName()
                        )
                );
    }
}
