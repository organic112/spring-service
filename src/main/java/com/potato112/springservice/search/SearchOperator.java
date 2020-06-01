package com.potato112.springservice.search;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;


/**
 * Provides operators for adv. search
 */
public enum SearchOperator {

    EQUAL(new String[]{"="}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.eq(field, values.get(0));
        }
    },
    NOT_EQUAL(new String[]{"<>"}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.ne(field, values.get(0));
        }
    },
    LIKE(new String[]{"="}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.ilike(field, values.get(0));
        }
    },
    LIKE_CASE_INSENSITIVE(new String[]{"="}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.like(field, values.get(0));
        }
    },
    GREATER(new String[]{">"}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.gt(field, values.get(0));
        }
    },
    GREATER_EQUAL(new String[]{">="}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.ge(field, values.get(0));
        }
    },
    LOWER(new String[]{"<"}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.lt(field, values.get(0));
        }
    },
    LOWER_EQUAL(new String[]{"<="}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.le(field, values.get(0));
        }
    },
    BETWEEN(new String[]{"from", "to"}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.between(field, values.get(0), values.get(1));
        }
    },
    IN(new String[]{"in"}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            return Restrictions.in(field, values.get(0));
        }
    },
    NOT_NULL(new String[]{"="}) {
        @Override
        public Criterion getCriterion(String field, List<Object> values) {
            Boolean isNotNull = (Boolean) values.get(0);
            if (isNotNull) {
                return Restrictions.isNotNull(field);
            } else {
                return Restrictions.isNull(field);
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
    public abstract Criterion getCriterion(String field, List<Object> values);
}
