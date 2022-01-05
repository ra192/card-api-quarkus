package com.card.repository;

import com.card.entity.Transaction;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class TransactionRepository {
    @Inject
    EntityManager em;

    public void save(Transaction transaction) {
        em.persist(transaction);
    }
}
