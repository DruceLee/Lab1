package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.*;

import java.io.File;
import java.io.IOException;

public class MainController {

    private ObservableTaskList observableTaskList = new ObservableTaskList();

    @FXML
    private TableView<Task> table;

    @FXML
    private TableColumn<Task, String> columnTitle;

    @FXML
    private TableColumn<Task, String> columnTime;

    @FXML
    private TableColumn<Task, String> columnStart;

    @FXML
    private TableColumn<Task, String> columnEnd;

    @FXML
    private TableColumn<Task, String> columnInterval;

    @FXML
    private TableColumn<Task, String> columnActive;

    FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private void initialize() {
        TaskList taskList = new ArrayTaskList();
        File file = new File("src/sample/source/tasks.txt");
        TaskIO.readText(taskList, file);

        columnTitle.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
        columnTime.setCellValueFactory(new PropertyValueFactory<Task, String>("time"));
        columnStart.setCellValueFactory(new PropertyValueFactory<Task, String>("start"));
        columnEnd.setCellValueFactory(new PropertyValueFactory<Task, String>("end"));
        columnInterval.setCellValueFactory(new PropertyValueFactory<Task, String>("interval"));
        columnActive.setCellValueFactory(new PropertyValueFactory<Task, String>("active"));

        observableTaskList.add(taskList);
        table.setItems(observableTaskList.getTasks());
    }

    public void add(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            fxmlLoader.setLocation(getClass().getResource("../FXML/addScene.fxml"));
            Parent root = fxmlLoader.load();
            ((AddController) fxmlLoader.getController()).setTask(observableTaskList);
            stage.setTitle("Add Task");
            stage.setMinWidth(420);
            stage.setMinHeight(420);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calendar(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            fxmlLoader.setLocation(getClass().getResource("../FXML/calendarScene.fxml"));
            Parent root = fxmlLoader.load();
            ((CalendarController) fxmlLoader.getController()).setTask(observableTaskList);
            stage.setTitle("Calendar");
            stage.setScene(new Scene(root));
            stage.setMinHeight(540);
            stage.setMinWidth(600);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(ActionEvent actionEvent) {
        try {
            TaskList taskList = new LinkedTaskList();
            taskList.add(observableTaskList);
            TaskIO.writeText(taskList, new File("src/sample/source/tasks.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void delete(ActionEvent actionEvent) {
        Task task = table.getSelectionModel().getSelectedItem();
        observableTaskList.delete(task);
    }

    public void change(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/changeScene.fxml"));
            Parent root = loader.load();
            ((ChangeController) loader.getController()).setTask(table.getSelectionModel().getSelectedItem());
            stage.setTitle("Change Task");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
