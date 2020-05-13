package com.potato112.springservice.jms.bulkaction.model.interfaces;

import java.util.Arrays;
import java.util.Set;

public interface SysStatus {

    String MESSAGE = "unsupported status string to parse as Enum constant";

    /**
     * set of statuses legal to be changed to from current status
     * e.g. SysStatus.CREATED -> SysStatus.PROCESSED, SysStatus.NOT_PROCESSED
     */
    Set<SysStatus> getExitStatuses();

    /**
     *  parse status from string (e.g. when fetched from external resources with no control over case sensitivity)
     */
    static <T extends Enum<?>> T parseStatusFromMixedCaseString(Class<T> enumType, String statusName) {

        T[] enumConstants = enumType.getEnumConstants();

        T statusEnum = Arrays.stream(enumConstants).filter(status -> status.name().compareToIgnoreCase(statusName) == 0)
                .findFirst().orElseThrow(() -> new IllegalArgumentException(MESSAGE));
        return statusEnum;
    }


}
