package org.anonymous.card.validators;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;
import org.anonymous.card.controllers.RequestCard;
import org.anonymous.card.repositories.CardRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

@Lazy
@Component
@RequiredArgsConstructor
public class CardValidator implements Validator {

    private final CardRepository cardRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestCard.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (errors.hasErrors()) return;

        RequestCard requestCard = (RequestCard) target;

        String cardName = requestCard.getCardName();
        int annualFee = requestCard.getAnnualFee();
        Long limit = requestCard.getLimit();
        CardType cardType = requestCard.getCardType();
        BankName bankName = requestCard.getBankName();
        Category category = requestCard.getCategory();

        if (cardRepository.exists(cardName)) {
            errors.rejectValue("cardName", "Duplicated");
            return;
        }

        if (annualFee <= 0) {
            errors.rejectValue("annualFee", "Limit");
            return;
        }

        if (limit <= 0L) {
            errors.rejectValue("limit", "Limit");
            return;
        }
    }
}


















