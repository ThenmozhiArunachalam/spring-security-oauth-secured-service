package com.example.springsecurityoauthsecuredservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditApplicationEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(AuditApplicationEventListener.class);

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @EventListener(condition = "#event.auditEvent.type != 'CUSTOM_AUDIT_EVENT'")
    public void onAuditEvent(AuditApplicationEvent event) {

        AuditEvent actualAuditEvent = event.getAuditEvent();

        LOG.info("On audit application event: timestamp: {}, principal: {}, type: {}, data: {}",
                actualAuditEvent.getTimestamp(),
                actualAuditEvent.getPrincipal(),
                actualAuditEvent.getType(),
                actualAuditEvent.getData()
        );

        applicationEventPublisher.publishEvent(
                new AuditApplicationEvent(
                        new AuditEvent(actualAuditEvent.getPrincipal(), "CUSTOM_AUDIT_EVENT")
                )
        );
    }

    @EventListener(condition = "#event.auditEvent.type == 'CUSTOM_AUDIT_EVENT'")
    public void onCustomAuditEvent(AuditApplicationEvent event) {
        LOG.info("Handling custom audit event ...");
    }
}
