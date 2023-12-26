package com.unimuenster.govlearnapi.core.config.enums;

public enum Role {
    Member(0),
    Moderator(1),
    Admin(2);
    

    private final long longValue;

    Role(long longValue) {
        this.longValue = longValue;
    }

    public long getLongValue() {
        return longValue;
    }
}
