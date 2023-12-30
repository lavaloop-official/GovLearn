package com.unimuenster.govlearnapi.core.config.enums;

public enum ReportReason {
    Unangemessen(0),
    KeinBezug(1),
    Sonstiges(2);
    

    private final long longValue;

    ReportReason(long longValue) {
        this.longValue = longValue;
    }

    public long getLongValue() {
        return longValue;
    }
}
