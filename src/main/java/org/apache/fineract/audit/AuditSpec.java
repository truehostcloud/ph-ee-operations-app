package org.apache.fineract.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@Slf4j
public class AuditSpec extends BaseSpecification<AuditSource, AuditSearch> {
    @Override
    public Specification<AuditSource> getFilter(AuditSearch request) {
        return (root, query, cb) -> {
            query.distinct(true);
            return where(
                    where(dateBetween("madeOnDate", getExactDate(request.getMakerDateTimeFrom(), 0, 0, 0), getExactDate(request.getMakerDateTimeTo(), 23, 59, 59)))
            ).and(fieldContains("actionName", request.getActionName()))
                    .and(fieldContains("entityName", request.getEntityName()))
                    .and(fieldContains("processingResult", request.getProcessingResult()))
                    .and(fieldContains("maker", request.getMakerId()))
                    .and(fieldContains("resourceId", request.getResourceId()))
                    .toPredicate(root, query, cb);
        };
    }

    private Specification<AuditSource> dateBetween(String field, Date start, Date end) {
        return (root, query, cb)
                -> cb.between(root.get(field), start, end);
    }

    private Specification<AuditSource> fieldContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (cb == null) {
                return null;
            }
            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<AuditSource> fieldContains(String attribute, Long value) {
        return (root, query, cb) -> {
            if (cb == null) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
        };
    }

    public static Date getExactDate(Date endDate, int hour, int minute, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,seconds);
        return calendar.getTime();
    }
}
