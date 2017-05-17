package com.epam.bench.carrental.client.userinterface.customized.components;

import java.time.LocalDate;

import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import com.epam.bench.carrental.client.bl.formatters.DateFormatter;

public class DatePickerImpl implements DatePicker {

    JDatePickerImpl jDatePicker;
    public DatePickerImpl() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        jDatePicker = new JDatePickerImpl(datePanel, new DateFormatter());
    }
    @Override
    public LocalDate getSelectedDate() {
        if (StringUtils.isBlank(jDatePicker.getJFormattedTextField().getText())) {
            return null;
        }
        return LocalDate.parse(jDatePicker.getJFormattedTextField().getText());
    }

    @Override
    public JComponent getComponent() {
        return jDatePicker;
    }

}
