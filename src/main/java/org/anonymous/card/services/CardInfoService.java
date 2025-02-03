package org.anonymous.card.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;
import org.anonymous.card.controllers.CardSearch;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.QCardEntity;
import org.anonymous.card.exceptions.CardNotFoundException;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.paging.Pagination;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class CardInfoService {

    private final CardRepository cardRepository;
    private final JPAQueryFactory queryFactory;
    private final HttpServletRequest request;

    public CardEntity getCardInfo (Long seq) {

        CardEntity card = cardRepository.findById(seq).orElseThrow(CardNotFoundException::new);

        if (card.getDeletedAt() != null) {
            throw new CardNotFoundException();
        }

        return card;
    }

    /**
     * 목록 조회 이거 알아서 빼라고 해야할듯.
     * @param search
     * @return
     */
    public ListData<CardEntity> cardList (CardSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;
        int offset = (page - 1) * limit;
        QCardEntity cardEntity = QCardEntity.cardEntity;

        BooleanBuilder andBuilder = new BooleanBuilder();
        String skey = search.getSkey();

        /**
         * 검색하게 될 경우
         */
        if (StringUtils.hasText(skey)) {
            andBuilder.and(cardEntity.cardDescription
                    .concat(cardEntity.cardName)
                    .contains(skey));
        }

        List<String> cardName = search.getCardName();

        // 카드 이름으로 검색

        if (cardName != null && !cardName.isEmpty()) {
            andBuilder.and(cardEntity.cardName.in(cardName));
        }

        // 은행 이름으로 검색

        List<BankName> bankName = search.getBankName();

        if (bankName != null && !bankName.isEmpty()) {
            andBuilder.and(cardEntity.bankName.in(bankName));
        }

        // 카드 한도로 검색

        int cardLimitMax = search.getCardLimitMax();
        int cardLimitMin = search.getCardLimitMin();
        cardLimitMax = cardLimitMax < 1 ? 100000000 : cardLimitMax;
        cardLimitMin = cardLimitMin < 1 ? 1000000 : cardLimitMin;

        andBuilder.and(cardEntity.limit.between(cardLimitMin, cardLimitMax));

        // 카테고리로 검색
        List<Category> categories = search.getCategories();
        if (categories != null && !categories.isEmpty()) {
            andBuilder.and(cardEntity.category.in(categories));
        }

        List<CardType> cardTypes = search.getCardTypes();
        if (cardTypes != null && !cardTypes.isEmpty()) {
            andBuilder.and(cardEntity.cardType.in(cardTypes));
        }

        String dateType = search.getDateType();
        dateType = StringUtils.hasText(dateType) ? dateType : "createdAt"; // 생성날짜 기준
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();
        DateTimePath<LocalDateTime> condition;
        if (dateType.equals("deletedAt")) condition = cardEntity.deletedAt; // 삭제일 기준
        else condition = cardEntity.createdAt; // 생성날짜 기준

        if (sDate != null) {
            andBuilder.and(condition.after(sDate.atStartOfDay()));
        }

        if (eDate != null) {
            andBuilder.and(condition.before(eDate.atTime(LocalTime.of(23,59,59))));
        }

        List<CardEntity> items = queryFactory.selectFrom(cardEntity)
                .where(andBuilder)
                .orderBy(cardEntity.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        long total = cardRepository.count(andBuilder);

        Pagination pagination = new Pagination(page, (int) total, 10, limit, request);

        return new ListData<>(items, pagination);
    }
}





















