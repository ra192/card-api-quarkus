package com.card.service;

import com.card.entity.*;
import com.card.entity.enums.TransactionStatus;
import com.card.entity.enums.TransactionType;
import com.card.repository.TransactionFeeRepository;
import com.card.repository.TransactionItemRepository;
import com.card.repository.TransactionRepository;
import com.card.service.exception.TransactionException;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TransactionService {

    private static final Logger logger = Logger.getLogger(TransactionService.class);

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    TransactionItemRepository transactionItemRepository;

    @Inject
    TransactionFeeRepository transactionFeeRepository;

    public Transaction deposit(Account srcAccount, Account destAccount, Account feeAccount, Long amount,
                               TransactionType type, String orderId, Card card) throws TransactionException {
        final var feeAmount = calculateFee(amount, type, destAccount);
        if (sumByAccount(srcAccount) - amount < 0)
            throw new TransactionException("Source account does not have enough funds");

        final var transaction = createTransaction(srcAccount, destAccount, amount, type, orderId, card);
        if (feeAmount > 0)
            transactionItemRepository.save(new TransactionItem(transaction, feeAmount, destAccount, feeAccount, null));

        logger.infof("transaction %s was created", transaction.getType());

        return transaction;
    }

    public Transaction withdraw(Account srcAccount, Account destAccount, Account feeAccount, Long amount,
                                TransactionType type, String orderId, Card card) throws TransactionException {
        final var feeAmount = calculateFee(amount, type, srcAccount);
        if (sumByAccount(srcAccount) - amount - feeAmount < 0)
            throw new TransactionException("Source account does not have enough funds");

        final var transaction = createTransaction(srcAccount, destAccount, amount, type, orderId, card);
        if (feeAmount > 0)
            transactionItemRepository.save(new TransactionItem(transaction, feeAmount, srcAccount, feeAccount, null));

        logger.infof("transaction %s was created", transaction.getType());

        return transaction;
    }

    private Transaction createTransaction(Account srcAccount, Account destAccount, Long amount, TransactionType type, String orderId, Card card) throws TransactionException {
        if (!srcAccount.getCurrency().equals(destAccount.getCurrency()))
            throw new TransactionException("Source account currency doesn't match destination account currency");

        final var transaction = new Transaction(orderId, type, TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);

        transactionItemRepository.save(new TransactionItem(transaction, amount, srcAccount, destAccount, card));

        return transaction;
    }

    private long calculateFee(Long amount, TransactionType type, Account destAccount) {
        final TransactionFee fee = transactionFeeRepository.find(type, destAccount);
        if (fee != null) return amount * fee.getRate().longValue();
        else return 0L;
    }

    private Long sumByAccount(Account account) {
        return transactionItemRepository.sumByDestAccount(account) - transactionItemRepository.sumBySrcAccount(account);
    }
}
