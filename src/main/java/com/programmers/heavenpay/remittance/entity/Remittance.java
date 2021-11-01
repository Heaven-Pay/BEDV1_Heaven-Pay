package com.programmers.heavenpay.remittance.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Remittance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "remittance_id", unique = true, nullable = false)
    private Long id;

}
