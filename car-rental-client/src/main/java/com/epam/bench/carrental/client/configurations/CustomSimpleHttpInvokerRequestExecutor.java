package com.epam.bench.carrental.client.configurations;

import org.springframework.core.env.Environment;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;

public class CustomSimpleHttpInvokerRequestExecutor extends SimpleHttpInvokerRequestExecutor {
    @Resource
    Environment environment;
    private final String TENANT_ID = "TenantId";
    @Override
    protected void prepareConnection(HttpURLConnection con, int contentLength) throws IOException {
        con.setRequestProperty(TENANT_ID, environment.getProperty(TENANT_ID));
        super.prepareConnection(con, contentLength);
    }
}