package com.epam.bench.carrental.server.aspect;

import com.epam.bench.carrental.server.threadlocal.TenantId;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TenantVerificationAspect {
    @Autowired
    TenantId tenantId;

    @Before("@annotation(com.epam.bench.carrental.server.aspect.customannotation.RequiredTenant)")
    public void invoke(JoinPoint jp) {
        if (StringUtils.isBlank(tenantId.getTenantId())) {
            throw new RuntimeException("Tenant id can not be null!");
        }
    }
}
