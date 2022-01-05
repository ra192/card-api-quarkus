package com.card.resource;

import com.card.resource.dto.CreateTokenDto;
import com.card.resource.dto.TokenDto;
import com.card.service.MerchantService;
import com.card.service.TokenService;
import com.card.service.exception.MerchantException;
import com.card.service.exception.TokenException;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.security.NoSuchAlgorithmException;

@Path("/api/token")
public class TokenResource {
    private static final Logger logger = Logger.getLogger(TokenResource.class);

    @Inject
    MerchantService merchantService;

    @Inject
    TokenService tokenService;

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public TokenDto create(CreateTokenDto requestObj) throws MerchantException, TokenException, NoSuchAlgorithmException {
        logger.info("Create token method was called with params:");
        logger.info(requestObj.toString());

        final var merchant = merchantService.findById(requestObj.getMerchantId());
        final var token = tokenService.create(merchant, requestObj.getSecret());

        return new TokenDto(token);
    }
}
