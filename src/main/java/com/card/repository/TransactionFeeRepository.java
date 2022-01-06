package com.card.repository;

import com.card.entity.Account;
import com.card.entity.TransactionFee;
import com.card.entity.enums.TransactionType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@ApplicationScoped
public class TransactionFeeRepository {
    @Inject
    EntityManager em;

    public TransactionFee find(TransactionType type, Account account) {
        try {
            return (TransactionFee) em.createQuery("select fee from TransactionFee fee where fee.type=?1 and fee.account=?2")
                    .setParameter(1, type).setParameter(2, account).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
