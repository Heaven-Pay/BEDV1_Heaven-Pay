package com.programmers.heavenpay.pointHistory.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointHistory extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "point_history_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "point_history_used_app", nullable = false)
    @Enumerated(EnumType.STRING)
    private UsedAppType usedApp;

    @Column(name = "point_history_description", nullable = false)
    private String description;

    @Column(name = "point_history_use_point", nullable = false)
    private Integer usePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    public void updateData(String description, Integer usePoint) {
        this.description = description.isBlank() ? this.description : description;
        this.usePoint = usePoint == 0 ? this.usePoint : usePoint;
    }
}
