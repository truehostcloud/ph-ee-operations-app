package org.apache.fineract.audit.events;

import lombok.Getter;
import org.apache.fineract.organisation.user.AppUser;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewAuditEvent extends ApplicationEvent {
    private final Integer resourceId;

    private final String actionName;

    private final String entityName;

    private final String resourceGetUrl;

    private final String dataAsJson;

    private final AppUser maker;

    private final String processingResult;

    public NewAuditEvent(Object source, Integer resourceId, String actionName, String entityName, String resourceGetUrl, String dataAsJson, AppUser maker, String processingResult) {
        super(source);
        this.resourceId = resourceId;
        this.actionName = actionName;
        this.entityName = entityName;
        this.resourceGetUrl = resourceGetUrl;
        this.dataAsJson = dataAsJson;
        this.maker = maker;
        this.processingResult = processingResult;
    }
}
