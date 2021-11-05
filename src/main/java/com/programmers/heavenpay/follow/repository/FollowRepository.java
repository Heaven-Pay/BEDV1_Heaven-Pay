package com.programmers.heavenpay.follow.repository;

import com.programmers.heavenpay.follow.entity.Follow;
import com.programmers.heavenpay.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Override
    Follow save(Follow entity);

    void deleteByToMemberAndFromMember(Member toMember, Member fromMember);

    Page<Follow> findByToMember(Member member, Pageable pageable);

    Page<Follow> findByFromMember(Member member, Pageable pageable);
}
