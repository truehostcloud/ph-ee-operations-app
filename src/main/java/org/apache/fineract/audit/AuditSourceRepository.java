package org.apache.fineract.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditSourceRepository extends JpaRepository<AuditSource, Long> {
}
