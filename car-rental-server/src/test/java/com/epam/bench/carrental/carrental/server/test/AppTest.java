package com.epam.bench.carrental.carrental.server.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.services.CustomerService;
import com.epam.bench.carrental.server.configurations.ParentSpringConfigurations;


/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ParentSpringConfigurations.class, loader=AnnotationConfigContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

    @Autowired
    CustomerService CustomerService = null;

    @Test
    public void test1AddAndGetData() {
	CustomerService.addCustomer(new CustomerDTO("Ali", "Aliyounas44@yahoo.com"));
	Assert.assertEquals(CustomerService.getCustomer().get(0).getEmailAddress(), "aliyounas44@yahoo.com");
	CustomerService.addCustomer(new CustomerDTO("Ali", "email@yahoo.com"));
	Assert.assertEquals(CustomerService.getCustomer().size(), 2);
    }

    @Test(expected=RuntimeException.class)
    public void test2AddData() {
	CustomerService.addCustomer(new CustomerDTO("Ali", "Aliyounas44@yahoo.com"));
    }
}
