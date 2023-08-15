package org.apache.fineract.audit.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditSourceRepository extends JpaRepository<AuditSource, Long> {
    Page<AuditSource> findAll(Specification<AuditSource> specification, Pageable pageable);
}
