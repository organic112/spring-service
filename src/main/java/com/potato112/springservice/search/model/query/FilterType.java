package com.potato112.springservice.search.model.query;

/**
 * Possible types of filters for simple search
 */
public enum  FilterType {
    EQUALS,
    DATE_EQUALS,
    DATE_AFTER,
    DATE_BEFORE,
    NOT_EQUALS,
    LIKE,
    LIKE_CASE_INSENSITIVE,
    GREATER,
    GREATER_EQUALS,
    LESS,
    LESS_EQUALS,
    IN,
    NOT_IN,
    INTEGER_LIKE;
}
