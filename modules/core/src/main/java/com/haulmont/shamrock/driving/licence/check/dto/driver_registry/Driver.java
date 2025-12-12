/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.driver_registry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haulmont.bali.jackson.joda.LocalDateAdapter;
import org.joda.time.LocalDate;

import java.util.Objects;
import java.util.UUID;

public class Driver {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("callsign")
    private String callsign;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("date_of_birth")
    @JsonSerialize(using = LocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = LocalDateAdapter.Deserializer.class)
    private LocalDate dateOfBirth;
    @JsonProperty("driving_licence")
    private String drivingLicence;
    @JsonProperty("home_address")
    private HomeAddress homeAddress;
    @JsonProperty("working_status")
    private WorkingStatus status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(String drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    public HomeAddress getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(HomeAddress homeAddress) {
        this.homeAddress = homeAddress;
    }

    public WorkingStatus getStatus() {
        return status;
    }

    public void setStatus(WorkingStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(callsign, driver.callsign) && Objects.equals(firstName, driver.firstName) && Objects.equals(lastName, driver.lastName) && Objects.equals(fullName, driver.fullName) && Objects.equals(dateOfBirth, driver.dateOfBirth) && Objects.equals(drivingLicence, driver.drivingLicence) && Objects.equals(homeAddress, driver.homeAddress) && Objects.equals(status, driver.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callsign, firstName, lastName, fullName, dateOfBirth, drivingLicence, homeAddress, status);
    }

}
