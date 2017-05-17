package com.epam.bench.carrental.client.userinterface.customized.components;

import javax.swing.*;
import java.time.LocalDateTime;

public interface DateTimePicker {
    LocalDateTime getSelectedDateTime();
    JComponent getComponent();
}
