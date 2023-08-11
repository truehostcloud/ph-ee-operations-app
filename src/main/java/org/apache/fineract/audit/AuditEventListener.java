package org.apache.fineract.audit;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
public class AuditEventListener {
    private AuditService auditService;

    public AuditEventListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @Async
    @EventListener
    public void handleUserPasswordResetEvent(NewAuditEvent event) {
        log.debug("Auditing changes to ".concat(event.getEntityName()));
        AuditSource auditSource = auditService.createNewEntry(event);
        log.debug("Audited changes to ".concat(event.getEntityName()).concat(" with ID ").concat(auditSource.getId().toString()));
    }

}
