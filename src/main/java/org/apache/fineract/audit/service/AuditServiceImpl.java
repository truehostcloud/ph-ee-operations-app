package org.apache.fineract.audit.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.audit.data.AuditSource;
import org.apache.fineract.audit.data.AuditSourceRepository;
import org.apache.fineract.audit.events.NewAuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuditServiceImpl implements AuditService {
    private final AuditSourceRepository auditSourceRepository;


    public AuditServiceImpl(AuditSourceRepository auditSourceRepository) {
        this.auditSourceRepository = auditSourceRepository;
    }

    @Override
    @Transactional
    public AuditSource createNewEntry(NewAuditEvent event) {
        AuditSource audit = new AuditSource();
        audit.setEntityName(event.getEntityName());
        audit.setResourceId(audit.getResourceId());
        audit.setActionName(event.getActionName());
        audit.setDataAsJson(event.getDataAsJson());
        audit.setProcessingResult(event.getProcessingResult());
        audit.setMadeOnDate(event.getMadeOnDate());
        audit.setMaker(event.getMaker());
        return auditSourceRepository.save(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditSource> getAudits(Specification<AuditSource> specification, Pageable pageable) {
        return auditSourceRepository.findAll(specification, pageable);
    }
}
