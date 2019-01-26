package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.model.*;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.SortedMap;

public class CalendarController {

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    private ObservableTaskList observableTaskList;

    public void setTask(ObservableTaskList observableTaskList) {
        this.observableTaskList = observableTaskList;
    }

    public void back(ActionEvent actionEvent) {
        Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        s.close();
    }

    public void search(ActionEvent actionEvent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = dateFormat.parse(startTime.getText());
            Date date2 = dateFormat.parse(endTime.getText());
            TaskList taskList = new LinkedTaskList();
            taskList.add(observableTaskList);
            SortedMap<Date, Set<Task>> setSortedMap = Tasks.calendar(taskList, date1, date2);
            System.out.println(setSortedMap.toString());
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Дата введена неправильно");
        }
    }
}
