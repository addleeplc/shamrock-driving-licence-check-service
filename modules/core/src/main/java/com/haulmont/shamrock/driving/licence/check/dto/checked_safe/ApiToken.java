package com.haulmont.shamrock.driving.licence.check.dto.checked_safe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haulmont.shamrock.driving.licence.check.joda.CheckedSafeDateTimeAdapter;
import org.joda.time.DateTime;
public class ApiToken {

    @JsonProperty("token")
    private String token;
    @JsonProperty("expired")
    private Boolean expired;
    @JsonProperty("validUntil")
    @JsonSerialize(using = CheckedSafeDateTimeAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeDateTimeAdapter.Deserializer.class)
    private DateTime validUntil;
    @JsonProperty("url")
    private String url;
    @JsonProperty("checkEvents")
    private String checkEvents;
    @JsonProperty("callbackToken")
    private String callbackToken;

    public String getToken() {
        return token;
    }

    public Boolean getExpired() {
        return expired;
    }

    public DateTime getValidUntil() {
        return validUntil;
    }

    public String getUrl() {
        return url;
    }

    public String getCheckEvents() {
        return checkEvents;
    }

    public String getCallbackToken() {
        return callbackToken;
    }

}
