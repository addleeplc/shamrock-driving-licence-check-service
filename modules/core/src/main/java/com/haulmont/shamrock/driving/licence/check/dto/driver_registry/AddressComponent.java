/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.driver_registry;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AddressComponent {

    @JsonProperty("street_name")
    private String streetName;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;

    public String getStreetName() {
        return streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddressComponent that = (AddressComponent) o;
        return Objects.equals(streetName, that.streetName) && Objects.equals(postalCode, that.postalCode) && Objects.equals(city, that.city) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, postalCode, city, country);
    }
}
