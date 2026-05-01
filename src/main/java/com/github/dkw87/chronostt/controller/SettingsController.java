package com.github.dkw87.chronostt.controller;

import com.github.dkw87.chronostt.StageManager;
import com.github.dkw87.chronostt.enumeration.TimeScale;
import com.github.dkw87.chronostt.model.Project;
import com.github.dkw87.chronostt.service.ProjectsService;
import com.github.dkw87.chronostt.service.SettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.regex.Pattern;

public class SettingsController {

    @FXML
    private ComboBox<Project> projectComboBox;

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

    @FXML
    private void initialize() {
        initElements();
        setListeners();
    }

    @FXML
    private void saveDaysWeek() {
        Integer days = daysWeekSpinner.getValue();
        SettingsService.getInstance().storeDaysWeek(days);
    }

    @FXML
    private void saveHoursDaily() {
        Integer hours = hoursDailySpinner.getValue();
        SettingsService.getInstance().storeHoursDaily(hours);
    }

    @FXML
    private void saveTimeScale() {
        TimeScale timeScale = timeScaleComboBox.getValue();
        SettingsService.getInstance().storeTimeScale(timeScale);
    }

    @FXML
    private void saveNotifyOvertime() {
        boolean notifyOvertime = notifyOvertimeCheckBox.isSelected();
        SettingsService.getInstance().storeNotifyOvertime(notifyOvertime);
    }

    @FXML
    private void saveAggregateProjectHours() {
        boolean aggregateHours = aggregateProjectHoursCheckBox.isSelected();
        SettingsService.getInstance().storeAggragateProjectHours(aggregateHours);
    }

    @FXML
    private void startTracking() {
        final Project project = projectComboBox.getSelectionModel().getSelectedItem();
        if (project == null)  {
            LOG.warn("No project selected");
            return;
        }
        SettingsService.getInstance().storeLastSelectedProject(project);
        StageManager.getInstance().getTrackingController().setProjectToTrack(project);
        StageManager.getInstance().showTrackingView();
    }

    @FXML
    private void manageProjectsView() {
        StageManager.getInstance().showManageProjectsView();
    }

    @FXML
    private void showTrackedDaysChart() {}

    private void initElements() {
        initProjectComboBox();
        initDaysWeekSpinner();
        initHoursDailySpinner();
        initTimeScaleComboBox();
        initNotifyOvertimeCheckBox();
        initAggregateProjectHoursCheckBox();
    }

    private void initProjectComboBox() {
        projectComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Project project) {
                if (project == null) return "";
                return project.getName();
            }

            @Override
            public Project fromString(String string) {
                return null; // not used
            }
        });
        projectComboBox.getItems().setAll(ProjectsService.getInstance().getProjects());
        projectComboBox.setValue(SettingsService.getInstance().getLastSelectedProject());
    }

    private void initDaysWeekSpinner() {
        final int daysWeek = SettingsService.getInstance().getDaysWeek();
        daysWeekSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_VALUE, MAX_DAYS, daysWeek)
        );
        daysWeekSpinner.getEditor().setTextFormatter(
                new TextFormatter<>(c -> POSITIVE_INT.matcher(c.getControlNewText()).matches() ? c : null)
        );
    }

    private void initHoursDailySpinner() {
        final int hoursDaily = SettingsService.getInstance().getHoursDaily();
        hoursDailySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_VALUE, MAX_HOURS, hoursDaily)
        );
        hoursDailySpinner.getEditor().setTextFormatter(
                new TextFormatter<>(c -> POSITIVE_INT.matcher(c.getControlNewText()).matches() ? c : null)
        );
    }

    private void initTimeScaleComboBox() {
        timeScaleComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(TimeScale timeScale) {
                if (timeScale == null) return "";
                return timeScale.getDescription();
            }

            @Override
            public TimeScale fromString(String string) {
                return null; // not used
            }
        });
        timeScaleComboBox.getItems().setAll(TimeScale.values());
        timeScaleComboBox.setValue(SettingsService.getInstance().getTimeScale());
    }

    private void initNotifyOvertimeCheckBox() {
        notifyOvertimeCheckBox.setSelected(
                SettingsService.getInstance().getNotifyOvertime()
        );
    }

    private void initAggregateProjectHoursCheckBox() {
        aggregateProjectHoursCheckBox.setSelected(
                SettingsService.getInstance().getAggragateProjectHours()
        );
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

    public void setProjectComboBox(Project project) {
        projectComboBox.setValue(project);
        SettingsService.getInstance().storeLastSelectedProject(project);
    }

}