package com.card.service;

import com.card.entity.Account;
import com.card.repository.AccountRepository;
import com.card.service.exception.AccountException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccountService {
    @Inject
    AccountRepository accountRepository;

    public Account findActiveById(Long accountId) throws AccountException {
        final Account account = accountRepository.find(accountId, true);
        if (account == null) throw new AccountException("Account does not exist");
        return account;
    }
}
