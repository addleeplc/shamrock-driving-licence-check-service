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
import com.haulmont.monaco.jackson.annotations.SensitiveData;
import org.joda.time.LocalDate;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Driver {
    @JsonProperty("pid")
    private Long pid;

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("number")
    private String number;
    @JsonProperty("callsign")
    private String callsign;
    @JsonProperty("first_name")
    @SensitiveData
    private String firstName;
    @JsonProperty("last_name")
    @SensitiveData
    private String lastName;
    @JsonProperty("middle_name")
    @SensitiveData
    private String middleName;
    @JsonProperty("full_name")
    @SensitiveData
    private String fullName;
    @JsonProperty("date_of_birth")
    @JsonSerialize(using = LocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = LocalDateAdapter.Deserializer.class)
    private LocalDate dateOfBirth;
    @JsonProperty("driving_licence")
    @SensitiveData
    private String drivingLicence;
    @JsonProperty("home_address")
    private HomeAddress homeAddress;
    @JsonProperty("working_status")
    private WorkingStatus status;
    @JsonProperty("driving_licence_expiry")
    @JsonSerialize(using = LocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = LocalDateAdapter.Deserializer.class)
    private LocalDate drivingLicenceExpiry;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
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

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDrivingLicence() {
        return drivingLicence;
    }

    public HomeAddress getHomeAddress() {
        return homeAddress;
    }

    public WorkingStatus getStatus() {
        return status;
    }

    public void setStatus(WorkingStatus status) {
        this.status = status;
    }

    public LocalDate getDrivingLicenceExpiry() {
        return drivingLicenceExpiry;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(callsign, driver.callsign) && Objects.equals(firstName, driver.firstName) && Objects.equals(lastName, driver.lastName) && Objects.equals(fullName, driver.fullName) && Objects.equals(dateOfBirth, driver.dateOfBirth) && Objects.equals(drivingLicence, driver.drivingLicence) && Objects.equals(homeAddress, driver.homeAddress) && Objects.equals(status, driver.status) && Objects.equals(drivingLicenceExpiry, driver.drivingLicenceExpiry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callsign, firstName, lastName, fullName, dateOfBirth, drivingLicence, homeAddress, status, drivingLicenceExpiry);
    }

}
