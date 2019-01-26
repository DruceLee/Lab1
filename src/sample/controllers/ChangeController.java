package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.ObservableTaskList;
import sample.model.Task;

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

    private Task task = new Task();

    public void setTask(Task task) {
        this.task = task;
    }

    public void initialize() {
        if (task == null)
            return;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (task.isRepeated()) {
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

    }

    public void back(ActionEvent actionEvent) {
        Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        s.close();
    }
}
