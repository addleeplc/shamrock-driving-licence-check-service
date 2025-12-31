/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check;

import com.haulmont.monaco.AppContext;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.haulmont.shamrock.driving.licence.check.ServiceConfiguration.DRIVER_INACTIVE_STATUSES;

@Component
public class ConfigurationService {

    @Inject
    private ServiceConfiguration serviceConfiguration;

    private Set<String> inactiveStatuses;

    public void start() {
        updateInactiveStatuses(serviceConfiguration.getDriverInactiveStatuses());

        AppContext.getConfig().registerListener(event ->{
            if (event.getKey().equals(DRIVER_INACTIVE_STATUSES)) {
                updateInactiveStatuses(event.getValue());
            }
        });
    }

    private void updateInactiveStatuses(String val) {
        this.inactiveStatuses = new HashSet<>(Arrays.asList(val.split(",")));
    }

    public Set<String> getInactiveStatuses() {
        return inactiveStatuses;
    }
}
