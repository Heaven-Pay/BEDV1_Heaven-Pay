package com.programmers.heavenpay.follow.repository;

import com.programmers.heavenpay.follow.entity.Follow;
import com.programmers.heavenpay.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Override
    Follow save(Follow entity);
    
    void deleteByToMemberAndFromMember(Member toMember, Member fromMember);
}
