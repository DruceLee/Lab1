package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Task;
import sample.model.TaskException;

import java.text.SimpleDateFormat;

public class ChangeController {

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

    private Task task;

    public void setTask(Task task) {
        this.task = task;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!task.isRepeated()) {
            title.setText(task.getTitle());
            time.setText(dateFormat.format(task.getTime()));
            active.setText(task.isActive() ? "true" : "false");
        } else {
            title.setText(task.getTitle());
            start.setText(dateFormat.format(task.getStart()));
            end.setText(dateFormat.format(task.getEnd()));
            active.setText(task.isActive() ? "true" : "false");
        }
    }

    public void change(ActionEvent actionEvent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            task.setTitle(title.getText());
            if (time.getText().isEmpty()) {
                task.setTime(dateFormat.parse(start.getText()), dateFormat.parse(end.getText()), Integer.parseInt(interval.getText()));
            } else {
                task.setTime(dateFormat.parse(time.getText()));
            }
            task.setActive("true".equals(active.getText()));
            Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            s.close();
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
