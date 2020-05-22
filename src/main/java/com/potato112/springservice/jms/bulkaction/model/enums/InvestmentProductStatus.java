package com.potato112.springservice.jms.bulkaction.model.enums;

import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;

import java.util.Set;

public enum InvestmentProductStatus implements SysStatus {


    CREATED {
        @Override
        public Set<SysStatus> getExitStatuses() {
            return null;
        }
    },
    PROCESSED {
        @Override
        public Set<SysStatus> getExitStatuses() {
            return null;
        }
    },
    NOT_PROCESSED {
        @Override
        public Set<SysStatus> getExitStatuses() {
            return null;
        }
    },

}
