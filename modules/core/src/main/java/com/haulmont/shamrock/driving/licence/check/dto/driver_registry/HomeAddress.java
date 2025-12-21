/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.driver_registry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.monaco.jackson.annotations.SensitiveData;

import java.util.Objects;

public class HomeAddress {

    @JsonProperty("formatted_address")
    @SensitiveData
    private String formattedAddress;
    @JsonProperty("address_components")
    private AddressComponent addressComponents;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public AddressComponent getAddressComponents() {
        return addressComponents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HomeAddress that = (HomeAddress) o;
        return Objects.equals(formattedAddress, that.formattedAddress) && Objects.equals(addressComponents, that.addressComponents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formattedAddress, addressComponents);
    }
}
