package com.unimuenster.govlearnapi.core.config.enums;

public enum Format {
    OnlineLive(0),
    Praesenz(1),
    Hybrid(2),
    OnlineSelbstorganisiert(3);
    

    private final long longValue;

    Format(long longValue) {
        this.longValue = longValue;
    }

    public long getLongValue() {
        return longValue;
    }
}
