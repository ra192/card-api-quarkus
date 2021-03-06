package com.card.repository;

import com.card.entity.Card;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class CardRepository {
    @Inject
    EntityManager em;

    public Card find(Long id) {
        return em.find(Card.class, id);
    }
}
