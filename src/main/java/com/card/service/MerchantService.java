package com.card.service;

import com.card.entity.Merchant;
import com.card.repository.MerchantRepository;
import com.card.service.exception.MerchantException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MerchantService {
    @Inject
    MerchantRepository merchantRepository;

    public Merchant findById(Long id) throws MerchantException {
        final var merchant = merchantRepository.find(id);

        if(merchant==null) throw new MerchantException("Merchant does not exist");

        return merchant;
    }
}
