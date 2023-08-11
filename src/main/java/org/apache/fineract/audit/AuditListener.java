package org.apache.fineract.audit;

import org.apache.fineract.organisation.user.AppUser;
import org.apache.fineract.organisation.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Auditable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Component
@EntityListeners(AuditListener.class)
public class AuditListener {

    @Autowired
    private AuditSourceRepository sourceAuditRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyOperation(Object entity) {
        if (entity instanceof Auditable) {
            Auditable auditable = (Auditable) entity;

            String action;
            if (entityManager.contains(entity)) {
                action = "UPDATE";
            } else {
                action = "CREATE";
            }

            AuditSource audit = new AuditSource();
            String entityName = entity.getClass().getSimpleName();
            Long entityId = (Long) auditable.getId();  // Assuming getId() is available in Auditable interface
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AppUser currentUser = null;
            if (authentication != null && authentication.isAuthenticated()) {
                currentUser = appUserRepository.findAppUserByName(authentication.getName());
                audit.setMaker(currentUser);
            }
            String changes = captureChanges(retrieveOriginalStateFromDatabase(auditable), entity);  // Implement change tracking logic

            audit.setEntityName(entityName);
            audit.setResourceId(entityId);
            audit.setActionName(action);
            audit.setMadeOnDate(new Date());
            audit.setDataAsJson(changes);

            sourceAuditRepository.save(audit);
        }
    }

    private String captureChanges(Object originalEntity, Object updatedEntity) {
        StringBuilder changes = new StringBuilder();

        Field[] fields = originalEntity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object originalValue = field.get(originalEntity);
                Object updatedValue = field.get(updatedEntity);

                if (!Objects.equals(originalValue, updatedValue)) {
                    changes.append(field.getName())
                            .append(": ")
                            .append(originalValue)
                            .append(" -> ")
                            .append(updatedValue)
                            .append("\n");
                }
            } catch (IllegalAccessException e) {
                // Handle exceptions if necessary
            }
        }

        return changes.toString();
    }


    private Auditable retrieveOriginalStateFromDatabase(Auditable updatedEntity) {
        // Assuming Auditable has an ID field named "id"
        Long entityId = (Long) updatedEntity.getId();
        // Retrieve the original entity state from the database
        Auditable originalEntity = entityManager.find(Auditable.class, entityId);

        return originalEntity;
    }

}

