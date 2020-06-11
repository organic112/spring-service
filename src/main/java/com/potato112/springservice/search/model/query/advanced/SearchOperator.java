package com.potato112.springservice.search.model.query.advanced;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;


import java.util.List;


/**
 * Provides operators for Advanced logical Search and corresponding Hibernate Criterions
 */
public enum SearchOperator {

    EQUAL(new String[]{"="}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.eq(fieldName, paramValueList.get(0));
        }
    },
    NOT_EQUAL(new String[]{"<>"}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.ne(fieldName, paramValueList.get(0));
        }
    },
    LIKE(new String[]{"="}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.ilike(fieldName, paramValueList.get(0));
        }
    },
    LIKE_CASE_INSENSITIVE(new String[]{"="}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.like(fieldName, paramValueList.get(0));
        }
    },
    GREATER(new String[]{">"}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.gt(fieldName, paramValueList.get(0));
        }
    },
    GREATER_EQUAL(new String[]{">="}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.ge(fieldName, paramValueList.get(0));
        }
    },
    LOWER(new String[]{"<"}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.lt(fieldName, paramValueList.get(0));
        }
    },
    LOWER_EQUAL(new String[]{"<="}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.le(fieldName, paramValueList.get(0));
        }
    },
    BETWEEN(new String[]{"from", "to"}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.between(fieldName, paramValueList.get(0), paramValueList.get(1));
        }
    },
    IN(new String[]{"in"}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            return Restrictions.in(fieldName, paramValueList);
        }
    },
    NOT_NULL(new String[]{"="}) {
        @Override
        public Criterion getCriterion(String fieldName, List<Object> paramValueList) {
            Boolean isNotNull = (Boolean) paramValueList.get(0);
            if (isNotNull) {
                return Restrictions.isNotNull(fieldName);
            } else {
                return Restrictions.isNull(fieldName);
            }
        }
    };

    private final String[] symbols;

    private SearchOperator(String[] s) {
        symbols = s;
    }

    public String[] symbol() {
        return symbols;
    }

    /**
     * Provides Hibernate criterion for current search operation
     */
    public abstract Criterion getCriterion(String field, List<Object> paramValueList);
}
