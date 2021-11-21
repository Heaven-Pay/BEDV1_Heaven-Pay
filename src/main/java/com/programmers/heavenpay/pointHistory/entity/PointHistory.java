package com.programmers.heavenpay.pointHistory.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;

import javax.persistence.*;

@Entity
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

    protected PointHistory() {
    }

    public PointHistory(Long id, UsedAppType usedApp, String description, Integer usePoint, Member member) {
        this.id = id;
        this.usedApp = usedApp;
        this.description = description;
        this.usePoint = usePoint;
        this.member = member;
    }

    public void updateData(String description, Integer usePoint) {
        this.description = description.isBlank() ? this.description : description;
        this.usePoint = usePoint == 0 ? this.usePoint : usePoint;
    }

    public Long getId() {
        return id;
    }

    public UsedAppType getUsedApp() {
        return usedApp;
    }

    public String getDescription() {
        return description;
    }

    public Integer getUsePoint() {
        return usePoint;
    }

    public Member getMember() {
        return member;
    }

    public static PointHistory.PointHistoryBuilder builder() {
        return new PointHistory.PointHistoryBuilder();
    }

    public static class PointHistoryBuilder {
        private Long id;
        private UsedAppType usedApp;
        private String description;
        private Integer usePoint;
        private Member member;

        private PointHistoryBuilder() {
        }

        public PointHistory.PointHistoryBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointHistory.PointHistoryBuilder usedApp(final UsedAppType usedApp) {
            this.usedApp = usedApp;
            return this;
        }

        public PointHistory.PointHistoryBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public PointHistory.PointHistoryBuilder usePoint(final Integer usePoint) {
            this.usePoint = usePoint;
            return this;
        }

        public PointHistory.PointHistoryBuilder member(final Member member) {
            this.member = member;
            return this;
        }

        public PointHistory build() {
            return new PointHistory(this.id, this.usedApp, this.description, this.usePoint, this.member);
        }
    }
}
