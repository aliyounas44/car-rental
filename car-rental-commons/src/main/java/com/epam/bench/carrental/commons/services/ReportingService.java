package com.epam.bench.carrental.commons.services;

import java.time.LocalDate;

import com.epam.bench.carrental.commons.beans.ReportDTO;

public interface ReportingService {
    public ReportDTO fetchReport(LocalDate fromDate, LocalDate toDate);
}