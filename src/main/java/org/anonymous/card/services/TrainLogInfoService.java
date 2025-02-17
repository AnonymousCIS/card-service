package org.anonymous.card.services;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.QTrainCardLog;
import org.anonymous.card.entities.TrainCardLog;
import org.anonymous.card.exceptions.TrainLogNotFoundException;
import org.anonymous.card.repositories.TrainLogRepository;
import org.anonymous.global.libs.Utils;
import org.anonymous.global.paging.CommonSearch;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.paging.Pagination;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class TrainLogInfoService {
    private final TrainLogRepository trainLogRepository;
    private final HttpServletRequest request;
    private final JPAQueryFactory queryFactory;
    private final Utils utils;

    public TrainCardLog get(Long seq) {
        return trainLogRepository.findById(seq).orElseThrow(TrainLogNotFoundException::new);
    }

    public List<TrainCardLog> getList(List<Long> seqs) {
        List<TrainCardLog> trainLogs = new ArrayList<>();

        for (Long seq : seqs) {
            TrainCardLog log = get(seq);

            if (log != null) {
                trainLogs.add(log);
            }
        }

        return trainLogs;
    }
    public ListData<TrainCardLog> getList(CommonSearch search) {

        int page = Math.max(search.getPage(), 1);

        int rowsPerPage = 0;

        int limit = search.getLimit() > 0 ? search.getLimit() : rowsPerPage;

        int offset = (page - 1) * limit;

        QTrainCardLog trainCardLog = QTrainCardLog.trainCardLog;

        JPAQuery<TrainCardLog> query = queryFactory.selectFrom(trainCardLog)
                .offset(offset)
                .limit(limit);

        query.orderBy(trainCardLog.createdAt.desc());
        List<TrainCardLog> items = query.fetch();
        long total = trainLogRepository.count();
        int ranges = utils.isMobile() ? 5 : 10;
        Pagination pagination = new Pagination(page, (int)total, ranges, limit, request);

        return new ListData<>(items, pagination);
    }
}
