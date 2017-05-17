package com.epam.bench.carrental.server.multitenancy;

import com.epam.bench.carrental.server.threadlocal.TenantId;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Autowired
    TenantId tenantId;

    @Override
    public String resolveCurrentTenantIdentifier() {
        return tenantId.getTenantId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
