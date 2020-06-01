package com.potato112.springservice.search;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public enum SearchFieldType {

    INTEGER(Integer.class, SearchOperator.BETWEEN) {
        @Override
        public Object getValue(String value, Class<?> returnType) {
            return Integer.parseInt(value);
        }
    },
    INTEGER_NO_FORMAT(Integer.class, SearchOperator.BETWEEN) {
        @Override
        public Object getValue(String value, Class<?> returnType) {
            return Integer.parseInt(value);
        }
    },
    INTEGER_EXACT(Integer.class, SearchOperator.EQUAL) {
        @Override
        public Object getValue(String value, Class<?> returnType) {
            return Integer.parseInt(value);
        }
    },
    SHORT(Short.class, SearchOperator.EQUAL) {
        @Override
        public Object getValue(String value, Class<?> returnType) {
            return Short.parseShort(value);
        }
    },
    BIG_DECIMAL(BigDecimal.class, SearchOperator.BETWEEN) {
        @Override
        public Object getValue(String value, Class<?> returnType) {
            return new BigDecimal(value);
        }
    },
    STRING(String.class, SearchOperator.LIKE) {
        @Override
        public Object getValue(String value, Class<?> returnType) {
            return value;
        }
    },
    STRING_EXACT(String.class, SearchOperator.EQUAL) {
        @Override
        public Object getValue(String value, Class<?> returnType) {
            return value;
        }
    },
    BOOLEAN(Boolean.class, SearchOperator.EQUAL) {
        @Override
        public Object getValue(String value, Class<?> returnType) {
            return Boolean.parseBoolean(value);
        }
    },
    DATE(LocalDate.class, SearchOperator.BETWEEN) {
        @Override
        public Object getValue(String value, Class<?> returnType) {
            long time = Long.parseLong(value);
            LocalDate localDate = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate;
        }
    };
    // TODO missing Enums

    private final Class<?> obType;

    private final SearchOperator searchOperator;

    private SearchFieldType(Class<?> clazz, SearchOperator operator) {

        this.obType = clazz;
        this.searchOperator = operator;
    }

    public Class<?> getObjectType() {
        return obType;
    }

    public SearchOperator getSearchOperator() {
        return searchOperator;
    }

    public static SearchFieldType getType(Class<?> type) {

        for (SearchFieldType sft : SearchFieldType.values()) {
            if (sft.obType.isAssignableFrom(type)) {
                return sft;
            }
        }
        return null;
    }

    // FIXME with MasterGrid
    //public abstract Object getValue(String value, Class<?> returnType, MasterTableManager masterTableManager);
    public abstract Object getValue(String value, Class<?> returnType);
}
