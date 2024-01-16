package com.unimuenster.govlearnapi.core.config.enums;

public enum Verantwortungsbereich {
    Stratege(0),
    Entscheidungsträger(1),
    Umsetzer(2);
    

    private final long longValue;

    Verantwortungsbereich(long longValue) {
        this.longValue = longValue;
    }

    public long getLongValue() {
        return longValue;
    }
}
