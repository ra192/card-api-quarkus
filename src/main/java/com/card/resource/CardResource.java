package com.card.resource;

import com.card.entity.Transaction;
import com.card.resource.dto.CreateCardTransactionDto;
import com.card.resource.dto.TransactionDto;
import com.card.service.CardService;
import com.card.service.exception.AccountException;
import com.card.service.exception.CardException;
import com.card.service.exception.TransactionException;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/api/card")
public class CardResource {
    private static final Logger logger = Logger.getLogger(CardResource.class);

    @Inject
    CardService cardService;

    @POST
    @Path("/deposit")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public TransactionDto deposit(CreateCardTransactionDto requestObject) throws CardException, TransactionException, AccountException {
        logger.info("Card deposit method was called with params:");
        logger.info(requestObject.toString());

        final var card = cardService.findById(requestObject.getCardId());
        final Transaction transaction = cardService.deposit(card, requestObject.getAmount(), requestObject.getOrderId());

        return new TransactionDto(transaction.getId(), transaction.getStatus());
    }
}