package com.haulmont.shamrock.driving.licence.check;

import com.haulmont.monaco.config.annotations.Config;
import com.haulmont.monaco.config.annotations.Property;
import org.picocontainer.annotations.Component;

@Config
@Component
public interface ServiceConfiguration {

    String MQ_DRIVER_CHANGE_RESOURCE_NAME = "mq.driver-change.resourceName";
    String MQ_DRIVER_CHANGE_CONSUMER = "mq.driver-change.consumer";

    @Property("redis.resourceName")
    String getRedisResource();

    @Property("redis.mandateFrom.ttl")
    Long getRedisMandateFromTTL();
    @Property("redis.mandateForm.invalidateBeforeExpiryMinutes")
    Integer getRedisMandateFormInvalidateBeforeExpiryMinutes();

    @Property("checkedSafe.username")
    String getCheckedSafeUsername();
    @Property("checkedSafe.password")
    String getCheckedSafePassword();
    @Property("checkedSafe.callback.url")
    String getCheckedSafeCallbackUrl();
    @Property("checkedSafe.callback.token")
    String getCheckedSafeCallbackToken();
    @Property("checkedSafe.mandateForm.linkExpiryMinutes")
    Integer getCheckedSafeMandateFormLinkExpiryMinutes();
    @Property("checkedSafe.mandateForm.successRedirectUrl")
    String getCheckedSafeMandateFormSuccessRedirectUrl();

    @Property("licenceCheck.approved.working_status")
    String getLicenceCheckApprovedWorkingStatus();
    @Property("licenceCheck.approved.working_status_reason")
    String getLicenceCheckApprovedWorkingStatusReason();
    @Property("licenceCheck.incorrect.working_status")
    String getLicenceCheckIncorrectWorkingStatus();
    @Property("licenceCheck.incorrect.working_status_reason")
    String getLicenceCheckIncorrectWorkingStatusReason();
    @Property("licenceCheck.incorrect.mailMessage")
    String getLicenceCheckIncorrectMailMessage();
    @Property("licenceCheck.declined.mailMessage")
    String getLicenceCheckDeclinedMailMessage();

    @Property("mq.resourceName")
    String getMqResource();
    @Property("mq.licenceCheckExchange")
    String getLicenceCheckExchange();

    @Property("mail.to")
    String getEmailTo();
    @Property("mail.subject")
    String getEmailSubject();

    @Property("shamrockUserId")
    Long getShamrockUserId();

    @Property("driverRegistry.apiKey")
    String getDriverRegistryApiKey();
}
