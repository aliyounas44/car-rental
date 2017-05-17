package com.epam.bench.carrental.server.configurations;

import com.epam.bench.carrental.commons.services.*;
import com.epam.bench.carrental.server.booking.implementation.CarBookingDetailImpl;
import com.epam.bench.carrental.server.booking.repositories.BookingRepository;
import com.epam.bench.carrental.server.car.repositories.FleetRepository;
import com.epam.bench.carrental.server.car.service.implementations.FleetServiceImpl;
import com.epam.bench.carrental.server.carrentaldetail.repositories.CarRentalDetailRepository;
import com.epam.bench.carrental.server.carrentaldetail.service.implementations.CarRentalInformationImpl;
import com.epam.bench.carrental.server.customer.repositories.CustomerRepository;
import com.epam.bench.carrental.server.customer.service.implementations.CustomerServiceImpl;
import com.epam.bench.carrental.server.multitenancy.CustomCurrentTenantIdentifierResolver;
import com.epam.bench.carrental.server.multitenancy.CustomMultiTenantConnectionProvider;
import com.epam.bench.carrental.server.rentalclass.repositories.RentalClassRepository;
import com.epam.bench.carrental.server.rentalclass.service.implementations.RentalClassServiceImpl;
import com.epam.bench.carrental.server.report.service.implementations.ReportingServiceImpl;
import com.epam.bench.carrental.server.serverinformation.implementations.ServerInformationImpl;
import org.fluttercode.datafactory.impl.DataFactory;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.bench.carrental")
@EnableJpaRepositories(basePackageClasses={CustomerRepository.class, FleetRepository.class, CarRentalDetailRepository.class, RentalClassRepository.class, BookingRepository.class})
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class ParentSpringConfigurations {
	@Bean
	public ServerInformation serverInformationImpl() {
		return new ServerInformationImpl();
	}

	@Bean
	public CustomerService customerServiceImpl() {
		return new CustomerServiceImpl();
	}

	@Bean
	public FleetService fleetServiceImpl() {
		return new FleetServiceImpl();
	}

	@Bean
	public CarRentalDetailService carRentalInformationImpl() {
		return new CarRentalInformationImpl();
	}

	@Bean
	public RentalClassService rentalClassServiceImpl() {
		return new RentalClassServiceImpl();
	}

	@Bean
	public ReportingService reportingServiceImpl() {
		return new ReportingServiceImpl();
	}

	@Bean
	public CarBookingDetailService carBookingDetailImpl() {
		return new CarBookingDetailImpl();
	}

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.H2).setName("CarRentalDb").build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(dataSource());
		lef.setJpaVendorAdapter(jpaVendorAdapter());
		lef.setPackagesToScan("com.epam.bench.carrental");
		lef.getJpaPropertyMap().put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
		lef.getJpaPropertyMap().put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
		lef.getJpaPropertyMap().put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, customCurrentTenantIdentifieResolver());
		lef.getJpaPropertyMap().put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, customMultiTenantConnectionProvider());
		return lef;
	}

	@Bean
	public CustomCurrentTenantIdentifierResolver customCurrentTenantIdentifieResolver() {
		return new CustomCurrentTenantIdentifierResolver();
	}

	@Bean
	public CustomMultiTenantConnectionProvider customMultiTenantConnectionProvider() {
		return new CustomMultiTenantConnectionProvider();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);
		return mapper;
	}

	@Bean
	public DataFactory dataFactory() {
		return new DataFactory();
	}
}
