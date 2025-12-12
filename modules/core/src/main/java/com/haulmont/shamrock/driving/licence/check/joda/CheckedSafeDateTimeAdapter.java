/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */
package com.haulmont.shamrock.driving.licence.check.joda;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class CheckedSafeDateTimeAdapter {
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmm'");
    public final static DateTimeFormatter DATE_TIME_WITH_SECONDS_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmmss'");

    public static class Deserializer extends JsonDeserializer<DateTime> {
        @Override
        public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            String s = jp.getText();
            if (StringUtils.isBlank(s)) return  null;

            if (s.length() == 12) {
                return DATE_TIME_FORMATTER.parseDateTime(s);
            } else {
                return DATE_TIME_WITH_SECONDS_FORMATTER.parseDateTime(s);
            }
        }
    }

    public static class Serializer extends JsonSerializer<DateTime> {
        @Override
        public void serialize(DateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (null == value) {
                gen.writeNull();
            } else {
                gen.writeString(value.toString(DATE_TIME_FORMATTER));
            }
        }
    }
}
