package com.haulmont.shamrock.driving.licence.check;

import com.haulmont.monaco.annotations.Module;
import com.haulmont.monaco.container.ModuleLoader;

@Module(
        name = "shamrock-driving-licence-check-service-module",
        depends = {
                "monaco-jetty",
                "monaco-core",
                "monaco-config",
                "monaco-graylog-reporter",
                "monaco-sentry-reporter",
                "monaco-mq",
                "monaco-pico-test",
                "monaco-redis",
                "monaco-rs",
                "monaco-unirest",
                "monaco-scheduler"
        }
)
public class ShamrockDrivingLicenceCheckServiceModule extends ModuleLoader {

    public ShamrockDrivingLicenceCheckServiceModule () {
        super();
        packages(ShamrockDrivingLicenceCheckServiceModule.class.getPackageName());
    }
}
