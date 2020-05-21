package com.potato112.springservice.domain.common;

public interface Validator<T> {

    /**
     *
     * @param value
     */
    void validate(T value);

}
