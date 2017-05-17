package com.epam.bench.carrental.client.bl;

import java.time.LocalDate;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.commons.beans.ReportDTO;
import com.epam.bench.carrental.commons.services.ReportingService;
import com.epam.bench.carrental.thread.service.RemoteCaller;

@Component
public class ReportStatisticsRequestHandler {
    @Autowired
    ReportingService reportingService;
    
    public void fetchReport(Consumer<ReportDTO> consumer, LocalDate fromDate, LocalDate toDate) {
	RemoteCaller remoteCaller = new RemoteCaller();
	remoteCaller.execute(() -> this.fetchReport(fromDate, toDate), consumer, exception -> CommonUI.showMessage("Unable to fetch Report."));
    }
    
    private ReportDTO fetchReport(LocalDate fromDate, LocalDate toDate) {
	return reportingService.fetchReport(fromDate, toDate);
    }
}
