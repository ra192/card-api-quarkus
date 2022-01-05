package com.card.service;

import com.card.entity.Card;
import com.card.entity.Transaction;
import com.card.entity.enums.TransactionType;
import com.card.repository.CardRepository;
import com.card.service.exception.AccountException;
import com.card.service.exception.CardException;
import com.card.service.exception.TransactionException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CardService {
    private static final Long CARD_ACCOUNT_ID = 2L;
    private static final Long FEE_ACCOUNT_ID = 3L;

    @Inject
    AccountService accountService;

    @Inject
    CardRepository cardRepository;

    @Inject
    TransactionService transactionService;

    public Transaction deposit(Card card, Long amount, String orderId) throws TransactionException, AccountException {
        return transactionService.withdraw(card.getAccount(), accountService.findActiveById(CARD_ACCOUNT_ID),
                accountService.findActiveById(FEE_ACCOUNT_ID), amount,
                TransactionType.VIRTUAL_CARD_DEPOSIT, orderId, card);
    }

    public Card findById(Long id) throws CardException {
        final var card = cardRepository.find(id);
        if (card == null) throw new CardException("Card does not exist");
        return card;
    }
}
