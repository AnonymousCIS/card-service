package org.anonymous.card.services.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;
import org.anonymous.card.controllers.CardSearch;
import org.anonymous.card.controllers.RecommendCardSearch;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.QCardEntity;
import org.anonymous.card.entities.QRecommendCard;
import org.anonymous.card.entities.RecommendCard;
import org.anonymous.card.exceptions.CardNotFoundException;
import org.anonymous.card.repositories.RecommendCardRepository;
import org.anonymous.global.exceptions.BadRequestException;
import org.anonymous.global.libs.Utils;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.paging.Pagination;
import org.anonymous.global.rests.JSONData;
import org.anonymous.member.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Lazy
@Service
@RequiredArgsConstructor
public class RecommendInfoService {

    private final RecommendCardRepository recommendCardRepository;
    private final JPAQueryFactory queryFactory;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;
    private final Utils utils;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;

    public RecommendCard getRecommendCard(Long seq) {
        RecommendCard card = recommendCardRepository.findBySeq(seq).orElseThrow(CardNotFoundException::new);

        ResponseEntity<JSONData> responseEntity = utils.returnData();

        try {
            String email = om.writeValueAsString(Objects.requireNonNull(responseEntity.getBody()).getData());

            if (!email.equals(card.getEmail())) {
                throw new CardNotFoundException();
            }

        } catch (JsonProcessingException e) {
            throw new BadRequestException();
        }

        return card;
    }

    public ListData<RecommendCard> cardList (RecommendCardSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;
        int offset = (page - 1) * limit;
        QRecommendCard recommendCard = QRecommendCard.recommendCard;
        QCardEntity cardEntity = QCardEntity.cardEntity;

        BooleanBuilder andBuilder = new BooleanBuilder();
        String skey = search.getSkey();

        /**
         * 검색하게 될 경우
         */
        if (StringUtils.hasText(skey)) {
            andBuilder.and(recommendCard.card.cardDescription
                    .concat(recommendCard.card.cardName)
                    .contains(skey));
        }

        List<String> cardName = search.getCardName();

        // 카드 이름으로 검색

        if (cardName != null && !cardName.isEmpty()) {
            andBuilder.and(recommendCard.card.cardName.in(cardName));
        }

        // 은행 이름으로 검색

        List<BankName> bankName = search.getBankName();

        if (bankName != null && !bankName.isEmpty()) {
            andBuilder.and(recommendCard.card.bankName.in(bankName));
        }

        // 카드 한도로 검색

        int cardLimitMax = search.getCardLimitMax();
        int cardLimitMin = search.getCardLimitMin();
        cardLimitMax = cardLimitMax < 1 ? 100000000 : cardLimitMax;
        cardLimitMin = cardLimitMin < 1 ? 1000000 : cardLimitMin;

        andBuilder.and(recommendCard.card.limit.between(cardLimitMin, cardLimitMax));

        // 카테고리로 검색
        List<Category> categories = search.getCategories();
        if (categories != null && !categories.isEmpty()) {
            andBuilder.and(recommendCard.card.category.in(categories));
        }

        List<CardType> cardTypes = search.getCardTypes();
        if (cardTypes != null && !cardTypes.isEmpty()) {
            andBuilder.and(recommendCard.card.cardType.in(cardTypes));
        }


        String dateType = search.getDateType();
        dateType = StringUtils.hasText(dateType) ? dateType : "createdAt"; // 생성날짜 기준
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();
        DateTimePath<LocalDateTime> condition;
        if (dateType.equals("deletedAt")) condition = recommendCard.card.deletedAt; // 삭제일 기준
        else condition = recommendCard.card.createdAt; // 생성날짜 기준

        if (sDate != null) {
            andBuilder.and(condition.after(sDate.atStartOfDay()));
        }

        if (eDate != null) {
            andBuilder.and(condition.before(eDate.atTime(LocalTime.of(23,59,59))));
        }

        List<String> email = search.getEmail();
        if (email != null && !email.isEmpty()) {
            andBuilder.and(recommendCard.email.in(email));
        }

        if (!memberUtil.isAdmin()) {
            andBuilder.and(cardEntity.isOpen);
        }

        List<RecommendCard> items = queryFactory.selectFrom(recommendCard)
                .leftJoin(recommendCard.card, cardEntity)
                .fetchJoin()
                .where(andBuilder)
                .orderBy(recommendCard.card.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        long total = recommendCardRepository.count(andBuilder);

        Pagination pagination = new Pagination(page, (int) total, 10, limit, request);

        return new ListData<>(items, pagination);
    }
}
