package com.epam.bench.carrental.client.userinterface.customized.components;

import java.time.LocalDate;

import javax.swing.JComponent;

public interface DatePicker {
    LocalDate getSelectedDate();
    JComponent getComponent();
}
