package net.lipecki.sqcompanion.group;

/**
 * Created by gregorry on 26.09.2015.
 */
public enum StatusCode {

    BLOCKER(3),

    CRITICAL(2),

    HEALTHY(1);

    private final int code;

    StatusCode(final int satusCode) {
        this.code = satusCode;
    }

    public Integer getCode() {
        return code;
    }

}
