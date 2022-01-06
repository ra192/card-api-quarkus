package com.card.repository;

import com.card.entity.Transaction;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class TransactionRepository {
    @Inject
    EntityManager em;

    @Transactional
    public void save(Transaction transaction) {
        em.persist(transaction);
    }
}
