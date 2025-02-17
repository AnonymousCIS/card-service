package org.anonymous.card.services;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.QTrainLog;
import org.anonymous.card.entities.TrainLog;
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

    public TrainLog get(Long seq) {
        return trainLogRepository.findById(seq).orElseThrow(TrainLogNotFoundException::new);
    }

    public List<TrainLog> getList(List<Long> seqs) {
        List<TrainLog> trainLogs = new ArrayList<>();

        for (Long seq : seqs) {
            TrainLog log = get(seq);

            if (log != null) {
                trainLogs.add(log);
            }
        }

        return trainLogs;
    }
    public ListData<TrainLog> getList(CommonSearch search) {

        int page = Math.max(search.getPage(), 1);

        int rowsPerPage = 0;

        int limit = search.getLimit() > 0 ? search.getLimit() : rowsPerPage;

        int offset = (page - 1) * limit;

        QTrainLog trainLog = QTrainLog.trainLog;

        JPAQuery<TrainLog> query = queryFactory.selectFrom(trainLog)
                .offset(offset)
                .limit(limit);

        query.orderBy(trainLog.createdAt.desc());
        List<TrainLog> items = query.fetch();
        long total = trainLogRepository.count();
        int ranges = utils.isMobile() ? 5 : 10;
        Pagination pagination = new Pagination(page, (int)total, ranges, limit, request);

        return new ListData<>(items, pagination);
    }
}
