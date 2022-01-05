package com.card.resource;

import com.card.entity.Merchant;
import com.card.service.MerchantService;
import com.card.service.TokenService;
import com.card.service.exception.MerchantException;
import org.jboss.logging.Logger;

import javax.inject.Inject;

public abstract class WithAuthMerchantResource {
    private static final Logger logger= Logger.getLogger(WithAuthMerchantResource.class);

    @Inject
    MerchantService merchantService;

    @Inject
    TokenService tokenService;

    protected Merchant validateToken(String authHeader) throws MerchantException {
        logger.infof("Authorization header: %s", authHeader);
        final var token = authHeader.replaceFirst("Bearer","").trim();
        return merchantService.findById(tokenService.validate(token));
    }
}
