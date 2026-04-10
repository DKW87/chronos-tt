package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.enumeration.TimeScale;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

public class SettingsController {

    @FXML
    private Spinner<Integer> daysWeekSpinner;

    @FXML
    private Spinner<Integer> hoursDailySpinner;

    @FXML
    private ComboBox<TimeScale> timeScaleComboBox;

    @FXML
    private CheckBox notifyOverworkCheckBox;

    @FXML
    private CheckBox aggregateProjectHoursCheckBox;

    @FXML
    public void initialize() {
        initElements();
        setListeners();
    }

    @FXML
    private void saveDaysWeek() {
        Integer days = daysWeekSpinner.getValue();
        // todo: call future service
    }

    @FXML
    private void saveHoursDaily() {
        Integer hours = hoursDailySpinner.getValue();
        // todo: call future service
    }

    @FXML
    private void saveTimeScale() {
        TimeScale timeScale = timeScaleComboBox.getValue();
        // todo: call future service
    }

    @FXML
    private void saveNotifyOverwork() {
        Boolean notifyOverwork = notifyOverworkCheckBox.isSelected();
        // todo: call future service
    }

    @FXML
    private void saveAggregateProjectHours() {
        Boolean aggregateHours = aggregateProjectHoursCheckBox.isSelected();
        // todo: call future service
    }

    private void initElements() {
        setSharedElements();

        boolean settingsAvailable = false;

        if (settingsAvailable) {
            // todo: get and set values from settings
        } else {
            initElementsDefault();
        }
    }

    private void setSharedElements() {
        daysWeekSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 7));
        hoursDailySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24));

        timeScaleComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(TimeScale timeScale) {
                return timeScale.getDescription();
            }

            @Override
            public TimeScale fromString(String string) {
                return null; // not needed but needs to be overridden
            }
        });
        timeScaleComboBox.getItems().setAll(TimeScale.values());
    }

    private void initElementsDefault() {
        daysWeekSpinner.getValueFactory().setValue(5);
        hoursDailySpinner.getValueFactory().setValue(8);
        timeScaleComboBox.setValue(TimeScale.values()[2]);
    }

    private void setListeners() {
        daysWeekSpinner.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveDaysWeek();
        });
        hoursDailySpinner.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) saveHoursDaily();
        });
    }

}