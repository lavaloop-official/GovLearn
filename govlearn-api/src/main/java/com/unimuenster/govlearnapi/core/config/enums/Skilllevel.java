package com.unimuenster.govlearnapi.core.config.enums;

public enum Skilllevel {
    Anfaenger(0),
    Fortgeschritten(1),
    Experte(2);

    private final long longValue;

    Skilllevel(long longValue) {
        this.longValue = longValue;
    }

    public long getLongValue() {
        return longValue;
    }
}

