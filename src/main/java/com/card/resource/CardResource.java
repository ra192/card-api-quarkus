package com.card.resource;

import com.card.entity.Transaction;
import com.card.resource.dto.CreateCardTransactionDto;
import com.card.resource.dto.TransactionDto;
import com.card.service.CardService;
import com.card.service.exception.AccountException;
import com.card.service.exception.CardException;
import com.card.service.exception.MerchantException;
import com.card.service.exception.TransactionException;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;

@Path("/api/card")
public class CardResource extends WithAuthMerchantResource {
    private static final Logger logger = Logger.getLogger(CardResource.class);

    @Inject
    CardService cardService;

    @POST
    @Path("/deposit")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public TransactionDto deposit(@HeaderParam("Authorization") String authHeader, CreateCardTransactionDto requestObj) throws CardException, TransactionException, AccountException, MerchantException {
        logger.info("Card deposit method was called with params:");
        logger.info(requestObj.toString());

        validateToken(authHeader);

        final var card = cardService.findById(requestObj.getCardId());
        final Transaction transaction = cardService.deposit(card, requestObj.getAmount(), requestObj.getOrderId());

        return new TransactionDto(transaction.getId(), transaction.getStatus());
    }
}