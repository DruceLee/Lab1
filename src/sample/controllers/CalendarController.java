package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarController {

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TableColumn<DateTitle, String> dateColumn;

    @FXML
    private TableColumn<DateTitle, String> titleColumn;

    @FXML
    private TableView<DateTitle> tableCalendar;

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
            TreeMap<Date, Set<Task>> setSortedMap = (TreeMap<Date, Set<Task>>) Tasks.calendar(taskList, date1, date2);

            if (setSortedMap.size() == 0)
                throw new TaskException("За заданими параметрами нічого не було знайдено.");

            Date date = setSortedMap.firstKey();
            Set<Task> tasks = setSortedMap.get(date.getTime());
            List<DateTitle> list = new ArrayList<>();
            for (Task task : tasks) {
                list.add(new DateTitle(task.getTitle(), date));
            }
            while ((date = setSortedMap.higherKey(date)) != null) {
                tasks = setSortedMap.get(date);
                for (Task task : tasks) {
                    list.add(new DateTitle(task.getTitle(), date));
                }
            }

            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableCalendar.setItems(FXCollections.observableArrayList(list));
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Дата введена неправильно");
            alert.show();
        } catch (TaskException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
