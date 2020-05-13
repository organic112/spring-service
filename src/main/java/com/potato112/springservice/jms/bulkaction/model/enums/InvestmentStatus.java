package com.potato112.springservice.jms.bulkaction.model.enums;

import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum InvestmentStatus implements SysStatus {

    IMPORTED {
        @Override
        public Set<SysStatus> getExitStatuses() {
            return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(PROCESSED, NOT_PROCESSED)));
        }
    },
    PROCESSED{
        @Override
        public Set<SysStatus> getExitStatuses() {
            return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(CLOSED)));
        }
    },
    NOT_PROCESSED{
        @Override
        public Set<SysStatus> getExitStatuses() {
            return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(CLOSED)));
        }
    },
    CLOSED{
        @Override
        public Set<SysStatus> getExitStatuses() {
            return Collections.unmodifiableSet(new HashSet<>());
        }
    }


}
