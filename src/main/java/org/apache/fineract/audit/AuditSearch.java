package org.apache.fineract.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class AuditSearch {
    private final String actionName;
    private final String entityName;
    private final Long resourceId;
    private final Long makerId;
    private final Date makerDateTimeFrom;
    private final Date makerDateTimeTo;
    private final String processingResult;
}
