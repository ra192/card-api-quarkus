package com.card.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Merchant implements Serializable {
    @Id
    @SequenceGenerator(name = "merchant_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "merchant_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String secret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
