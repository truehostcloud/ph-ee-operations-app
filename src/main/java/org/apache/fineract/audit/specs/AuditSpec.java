package org.apache.fineract.audit.specs;

import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.audit.data.AuditSearch;
import org.apache.fineract.audit.data.AuditSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.data.jpa.domain.Specification.where;
/**
 * A component that generates specifications for querying audit entries based on provided search criteria.
 */
@Component
@Slf4j
public class AuditSpec extends BaseSpecification<AuditSource, AuditSearch> {
    /**
     * Generates a specification for filtering audit entries based on the provided search criteria.
     *
     * @param request The audit search criteria.
     * @return A specification for filtering audit entries.
     */
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
    /**
     * Gets a date adjusted to a specific time of day.
     *
     * @param date The input date.
     * @param hour The target hour.
     * @param minute The target minute.
     * @param seconds The target second.
     * @return The adjusted date with the specified time of day.
     */

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
    /**
     * Generates a specification for filtering audit entries based on the provided attribute and string value.
     *
     * @param attribute The attribute to filter on.
     * @param value The string value to match.
     * @return A specification for filtering audit entries based on the string attribute and value.
     */
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
    /**
     * Generates a specification for filtering audit entries based on the provided attribute and long value.
     *
     * @param attribute The attribute to filter on.
     * @param value The long value to match.
     * @return A specification for filtering audit entries based on the long attribute and value.
     */
    private Specification<AuditSource> fieldContains(String attribute, Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
        };
    }

    /**
     * Generates a specification for filtering audit entries based on the provided date range.
     *
     * @param field The field to filter on.
     * @param start The start date of the date range.
     * @param end The end date of the date range.
     * @return A specification for filtering audit entries within the specified date range.
     */
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
