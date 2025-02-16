package org.anonymous.card.validators;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;
import org.anonymous.card.controllers.RequestCard;
import org.anonymous.card.repositories.CardRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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

        RequestCard form = (RequestCard) target;

        String mode = form.getMode();

        mode = StringUtils.hasText(mode) ? mode : "add";

        String cardName = form.getCardName();
        int annualFee = form.getAnnualFee();
        Long limit = form.getLimit();
//        CardType cardType = form.getCardType();
//        BankName bankName = form.getBankName();
//        Category category = form.getCategory();

        if (mode.equals("add") && cardRepository.exists(cardName)) {
            errors.rejectValue("cardName", "Duplicated");
            return;
        }

        if (annualFee < 1000 || annualFee > 30000) {
            errors.rejectValue("annualFee", "Limit");
            return;
        }

        if (limit < 1000000L || limit > 100000000L) {
            errors.rejectValue("limit", "Limit");
            return;
        }
    }
}


















