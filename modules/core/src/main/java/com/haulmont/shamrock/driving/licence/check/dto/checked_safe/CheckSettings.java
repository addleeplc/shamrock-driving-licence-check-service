/*
 * Copyright 2008 - 2026 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CheckSettings {

    @JsonProperty("cpc_enabled")
    private Boolean cpcEnabled;
    @JsonProperty("tachograph_enabled")
    private Boolean tachographEnabled;

    public Boolean getCpcEnabled() {
        return cpcEnabled;
    }

    public Boolean getTachographEnabled() {
        return tachographEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CheckSettings that = (CheckSettings) o;
        return Objects.equals(cpcEnabled, that.cpcEnabled) && Objects.equals(tachographEnabled, that.tachographEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpcEnabled, tachographEnabled);
    }
}
