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
        final var account = new Account();
        account.setId(id);
        account.setActive(active);

        return em.find(Account.class,account);
    }
}
