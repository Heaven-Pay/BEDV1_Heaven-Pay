package com.programmers.heavenpay.follow.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Follow extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "follow_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_to_member")
    private Member toMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_from_member")
    private Member fromMember;

    public Follow(Member toMember, Member fromMember) {
        this.toMember = toMember;
        this.fromMember = fromMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(id, follow.id) && Objects.equals(toMember, follow.toMember) && Objects.equals(fromMember, follow.fromMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, toMember, fromMember);
    }

    public Long getId() {
        return id;
    }

    public Member getFromMember() {
        return fromMember;
    }
}
