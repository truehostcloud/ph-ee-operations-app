package org.apache.fineract.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AuditService {

    AuditSource createNewEntry(NewAuditEvent event);

    Page<AuditSource> getAudits(Specification<AuditSource> specification, Pageable pageable);
}
