package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.StageManager;
import com.github.dkw87.chronostt.enumeration.TimeScale;
import com.github.dkw87.chronostt.model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.regex.Pattern;

public class SettingsController {

    @FXML
    public ComboBox<Project> projectComboBox;

    @FXML
    private Spinner<Integer> daysWeekSpinner;

    @FXML
    private Spinner<Integer> hoursDailySpinner;

    @FXML
    private ComboBox<TimeScale> timeScaleComboBox;

    @FXML
    private CheckBox notifyOvertimeCheckBox;

    @FXML
    private CheckBox aggregateProjectHoursCheckBox;

    private static final Logger LOG = LoggerFactory.getLogger(SettingsController.class);
    private static final Pattern POSITIVE_INT = Pattern.compile("^[1-9]\\d?$|^$");
    private static final int MAX_DAYS = 7;
    private static final int MAX_HOURS = 24;
    private static final int MIN_VALUE = 1;
    private static final int DEFAULT_WORKING_DAYS = 5;
    private static final int DEFAULT_WORKING_HOURS = 8;

    @FXML
    public void initialize() {
        initElements();
        setListeners();
    }

    @FXML
    private void saveDaysWeek() {
        Integer days = daysWeekSpinner.getValue();
        LOG.info("Saving daysWeek({})", days);
    }

    @FXML
    private void saveHoursDaily() {
        Integer hours = hoursDailySpinner.getValue();
        LOG.info("Saving hoursDaily({})", hours);
    }

    @FXML
    private void saveTimeScale() {
        TimeScale timeScale = timeScaleComboBox.getValue();
        LOG.info("Saving timeScale({})", timeScale);
    }

    @FXML
    private void saveNotifyOvertime() {
        Boolean notifyOvertime = notifyOvertimeCheckBox.isSelected();
        LOG.info("Saving notifyOvertime({})", notifyOvertime);
    }

    @FXML
    private void saveAggregateProjectHours() {
        Boolean aggregateHours = aggregateProjectHoursCheckBox.isSelected();
        LOG.info("Saving aggregateProjectHours({})", aggregateHours);
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
        daysWeekSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_VALUE, MAX_DAYS));
        daysWeekSpinner.getEditor().setTextFormatter(
                new TextFormatter<>(c -> POSITIVE_INT.matcher(c.getControlNewText()).matches() ? c : null)
        );

        hoursDailySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_VALUE, MAX_HOURS));
        hoursDailySpinner.getEditor().setTextFormatter(
                new TextFormatter<>(c -> POSITIVE_INT.matcher(c.getControlNewText()).matches() ? c : null)
        );

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
        daysWeekSpinner.getValueFactory().setValue(DEFAULT_WORKING_DAYS);
        hoursDailySpinner.getValueFactory().setValue(DEFAULT_WORKING_HOURS);
        timeScaleComboBox.setValue(TimeScale.THIRTY_MINUTES);
        notifyOvertimeCheckBox.setSelected(true);
        aggregateProjectHoursCheckBox.setSelected(true);
    }

    private void setListeners() {
        daysWeekSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal > MAX_DAYS) return;
            if (!Objects.equals(newVal, oldVal)) saveDaysWeek();
        });
        hoursDailySpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal > MAX_HOURS) return;
            if (!Objects.equals(newVal, oldVal)) saveHoursDaily();
        });
    }

    public void startTracking() {
        StageManager.getInstance().showTrackingView();
    }

    public void manageProjectsView() {}

    public void showTrackedDaysChart() {}
}