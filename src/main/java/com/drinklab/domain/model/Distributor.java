package com.drinklab.domain.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "distributors")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Distributor {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String document;

    @Embedded
    private Address address;

    private String contact;

    private String email;

    private Boolean active = true;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "distributor_users",
            joinColumns = @JoinColumn(name = "distributor_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "distributor_payments",
            joinColumns = @JoinColumn(name = "distributor_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id")
    )
    private Set<Payment> payments = new HashSet<>();
}
