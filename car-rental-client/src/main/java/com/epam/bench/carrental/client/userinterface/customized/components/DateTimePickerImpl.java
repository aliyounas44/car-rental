package com.epam.bench.carrental.client.userinterface.customized.components;

import javax.swing.*;
import java.time.LocalDateTime;

public class DateTimePickerImpl implements DateTimePicker {

    com.github.lgooddatepicker.components.DateTimePicker dateTimePicker;

    public DateTimePickerImpl() {
        dateTimePicker = new com.github.lgooddatepicker.components.DateTimePicker();
    }

    @Override
    public LocalDateTime getSelectedDateTime() {
        return dateTimePicker.getDateTimePermissive();
    }

    @Override
    public JComponent getComponent() {
        return dateTimePicker;
    }
}
