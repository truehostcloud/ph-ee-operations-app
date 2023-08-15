package org.apache.fineract.audit.service;

import org.apache.fineract.audit.data.AuditSource;
import org.apache.fineract.audit.events.NewAuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AuditService {

    AuditSource createNewEntry(NewAuditEvent event);

    Page<AuditSource> getAudits(Specification<AuditSource> specification, Pageable pageable);
}
