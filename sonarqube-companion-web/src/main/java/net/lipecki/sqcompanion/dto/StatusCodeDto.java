package net.lipecki.sqcompanion.dto;

import net.lipecki.sqcompanion.repository.HealthStatus;

/**
 * Created by gregorry on 26.09.2015.
 */
public enum StatusCodeDto {

    BLOCKER(3),

    CRITICAL(2),

    HEALTHY(1);

    private final int code;

    StatusCodeDto(final int satusCode) {
        this.code = satusCode;
    }

    public static StatusCodeDto of(final HealthStatus healthStatus) {
        switch (healthStatus) {
            case BLOCKER:
                return BLOCKER;
            case CRITICAL:
                return CRITICAL;
            case HEALTHY:
                return HEALTHY;
            default:
                throw new IllegalArgumentException(String.format("No matching StatusCode for HalthStatus " +
                        "[statusToMap=%s]", healthStatus));
        }
    }

    public Integer getCode() {
        return code;
    }

}
