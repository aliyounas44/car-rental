package com.epam.bench.carrental.server.configurations;

import com.epam.bench.carrental.server.threadlocal.TenantId;
import com.sun.net.httpserver.HttpExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

public class CustomHttpInvokerServiceExporter extends SimpleHttpInvokerServiceExporter {

    TenantId tenantId;
    private final String TENANT_ID = "TenantId";

    @Autowired
    public CustomHttpInvokerServiceExporter(TenantId tenantId) {
        super();
        this.tenantId = tenantId;
    }

    public void handle(HttpExchange exchange) throws IOException {
        try {
            List<String> parameters = exchange.getRequestHeaders().get(TENANT_ID);
            String tenantId = "Public";
            if (parameters != null) {
                tenantId = parameters.get(0);
            }
            this.tenantId.setTenantId(tenantId);
            super.handle(exchange);
        } finally {
            tenantId.setTenantId(null);
        }

    }
}
