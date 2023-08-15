package org.apache.fineract.audit.specs;

import org.springframework.data.jpa.domain.Specification;

public abstract class BaseSpecification<T, U> {
    private static final String wildcard = "%";

    public abstract Specification<T> getFilter(U request);

    protected String containsLowerCase(String searchField) {
        return wildcard + searchField.toLowerCase() + wildcard;
    }
}
