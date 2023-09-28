package org.apache.fineract.audit.specs;

import org.springframework.data.jpa.domain.Specification;

public abstract class BaseSpecification<T, U> {
    private static final String WILD_CARD = "%";

    public abstract Specification<T> getFilter(U request);

    protected String containsLowerCase(String searchField) {
        return WILD_CARD + searchField.toLowerCase() + WILD_CARD;
    }
}
