package org.apache.fineract.audit.events;


import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.audit.service.AuditService;
import org.apache.fineract.audit.data.AuditSource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
/**
 * Audit event listener class that gets called on each new event.
 */
@Component
@Slf4j
public class AuditEventListener {
    private AuditService auditService;

    public AuditEventListener(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * Creates new audit entries in async mode.
     */
    @Async
    @EventListener
    public void handleNewAuditEvent(NewAuditEvent event) {
        log.debug("Auditing changes to ".concat(event.getEntityName()));
        AuditSource auditSource = auditService.createNewEntry(event);
        log.debug("Audited changes to ".concat(event.getEntityName()).concat(" with ID ").concat(auditSource.getId().toString()));
    }

}
