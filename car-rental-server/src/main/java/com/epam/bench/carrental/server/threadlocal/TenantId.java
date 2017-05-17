package com.epam.bench.carrental.server.threadlocal;

import org.springframework.stereotype.Component;

@Component
public class TenantId {
    private ThreadLocal<String> tenantId = new ThreadLocal<String>() {
        @Override protected String initialValue() {
            return "Public";
        }
    };

    public String getTenantId() {
        return tenantId.get();
    }

    public void setTenantId(String tenantId) {
        this.tenantId.set(tenantId);
    }
}
