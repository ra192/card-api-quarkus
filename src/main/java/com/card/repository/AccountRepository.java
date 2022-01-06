package com.card.repository;

import com.card.entity.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class AccountRepository {
    @Inject
    EntityManager em;

    public Account find(Long id, Boolean active) {
        return (Account) em.createQuery("select acc from Account acc where acc.id=?1 and acc.active=?2")
                .setParameter(1, id).setParameter(2, active).getSingleResult();
    }
}
