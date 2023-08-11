package org.apache.fineract.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AuditServiceImpl implements AuditService{
    private final AuditSourceRepository auditSourceRepository;

    public AuditServiceImpl(AuditSourceRepository auditSourceRepository) {
        this.auditSourceRepository = auditSourceRepository;
    }

    @Override
    public AuditSource createNewEntry(NewAuditEvent event) {
        AuditSource audit = new AuditSource();
        audit.setEntityName(event.getEntityName());
        audit.setResourceId(audit.getResourceId());
        audit.setActionName(event.getActionName());
        audit.setDataAsJson(event.getDataAsJson());
        audit.setProcessingResult(event.getProcessingResult());
        audit.setMadeOnDate(new Date());
        audit.setMaker(event.getMaker());
        return auditSourceRepository.save(audit);
    }

    @Override
    public Page<AuditSource> getAudits(Specification<AuditSource> specification, Pageable pageable) {
        return null;
    }
}
