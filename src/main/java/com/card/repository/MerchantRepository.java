package com.card.repository;

import com.card.entity.Merchant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class MerchantRepository {
    @Inject
    EntityManager em;

    public Merchant find(Long id) {
        return em.find(Merchant.class, id);
    }
}
