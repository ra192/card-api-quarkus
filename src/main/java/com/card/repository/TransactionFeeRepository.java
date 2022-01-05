package com.card.repository;

import com.card.entity.Account;
import com.card.entity.TransactionFee;
import com.card.entity.enums.TransactionType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class TransactionFeeRepository {
    @Inject
    EntityManager em;

    public TransactionFee find(TransactionType type, Account account) {
        final var fee = new TransactionFee();
        fee.setType(type);
        fee.setAccount(account);

        return em.find(TransactionFee.class, fee);
    }
}
