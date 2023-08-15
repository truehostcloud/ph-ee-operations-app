package org.apache.fineract.audit.specs;

import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.audit.data.AuditSearch;
import org.apache.fineract.audit.data.AuditSource;
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

    public static Date getExactDate(Date date, int hour, int minute, int seconds) {
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    private Specification<AuditSource> fieldContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
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
            if (value == null) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
        };
    }

    private Specification<AuditSource> dateBetween(String field, Date start, Date end) {
        return (root, query, cb)
                -> {
            if (start == null) {
                return null;
            }
            return cb.between(root.get(field), start, end);
        };
    }
}
