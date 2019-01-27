package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.ObservableTaskList;
import sample.model.Task;
import sample.model.TaskException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddController {

    @FXML
    private TextField title;
    @FXML
    private TextField time;
    @FXML
    private TextField start;
    @FXML
    private TextField end;
    @FXML
    private TextField interval;
    @FXML
    private TextField active;

    private ObservableTaskList observableTaskList;

    public void setTask(ObservableTaskList observableTaskList) {
        this.observableTaskList = observableTaskList;
    }

    public void initialize() {
        title.setPromptText("Title");
        time.setPromptText("Time format: yyyy-mm-dd hh:mm:ss");
        start.setPromptText("Start format: yyyy-mm-dd hh:mm:ss");
        end.setPromptText("End format: yyyy-mm-dd hh:mm:ss");
        interval.setPromptText("Interval format: integer");
        active.setPromptText("Active format: true or false");
    }

    public void add(ActionEvent actionEvent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String titleText = title.getText();
            Date times = time.getText().isEmpty() ? null : dateFormat.parse(time.getText());
            Date starts = start.getText().isEmpty() ? null : dateFormat.parse(start.getText());
            Date ends = end.getText().isEmpty() ? null : dateFormat.parse(end.getText());
            int intervals = interval.getText().isEmpty() ? -1 : Integer.parseInt(interval.getText());
            boolean actives = Boolean.parseBoolean(active.getText());

            if (times != null && starts == null && ends == null && intervals == -1) {
                Task task = new Task(titleText, times, actives);
                task.setStart(null);
                task.setEnd(null);
                observableTaskList.add(task);
                Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                s.close();
            } else if (times == null && starts != null && ends != null && intervals != -1) {
                Task task = new Task(titleText, starts, ends, intervals, actives);
                observableTaskList.add(task);
                Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                s.close();
            } else {
                throw new TaskException("Неправильне введення данних, заповніть поля для повторюваної або неповторюваної задачі.");
            }
        } catch (TaskException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.setWidth(600);
            alert.setHeight(400);
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Неправильне введення даних");
            alert.show();
        }
    }

    public void back(ActionEvent actionEvent) {
        Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        s.close();
    }
}
