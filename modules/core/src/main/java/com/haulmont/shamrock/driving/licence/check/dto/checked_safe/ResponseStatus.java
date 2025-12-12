package com.haulmont.shamrock.driving.licence.check.dto.checked_safe;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResponseStatus {
    @JsonProperty("Success")
    SUCCESS(),
    @JsonProperty("Fail")
    FAIL(),
    @JsonProperty("User status updated successfully")
    USER_UPDATED

}
