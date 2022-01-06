package com.card.repository;

import com.card.entity.Account;
import com.card.entity.TransactionItem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class TransactionItemRepository {

    @Inject
    EntityManager em;

    @Transactional
    public void save(TransactionItem transactionItem) {
        em.persist(transactionItem);
    }

    public Long sumByDestAccount(Account account) {
        final var res = em.createQuery("select sum(itm.amount) from TransactionItem itm where itm.destAccount=?1")
                .setParameter(1, account).getSingleResult();
        return res != null ? (Long) res : 0L;
    }

    public Long sumBySrcAccount(Account account) {
        final var res = em.createQuery("select sum(itm.amount) from TransactionItem itm where itm.srcAccount=?1")
                .setParameter(1, account).getSingleResult();
        return res != null ? (Long) res : 0L;
    }
}
