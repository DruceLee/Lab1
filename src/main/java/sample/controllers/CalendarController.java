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
import org.apache.log4j.Logger;
import sample.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс используется как обработчик событий страницы calendarScene.fxml
 * @author Андрей Шерстюк
 */
public class CalendarController {

    private static Logger logger = Logger.getLogger(CalendarController.class);

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

    /**
     * Метод для передачи списка задач в данный класс
     * @param observableTaskList - объект класса Task который будет изменен
     */
    public void setTask(ObservableTaskList observableTaskList) {
        this.observableTaskList = observableTaskList;
    }

    /**
     * Метод для закрытия даного окна и разблокировки главного окна
     * @param actionEvent - событие, что произошло
     */
    public void back(ActionEvent actionEvent) {
        Stage s = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        s.close();
    }

    /**
     * Метод для поиска среди задач по критериям
     * @param actionEvent - событие, что произошло
     * @exception ParseException если пользователь ввел данные в неправильном формате
     * @exception TaskException если за данными критериями ничего небыло найдено
     */
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
            Set<Task> tasks = setSortedMap.get(date);
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

            logger.info("По заданым критериям: " + dateFormat.format(date1) + " и "
                        + dateFormat.format(date2) + " найдено " + setSortedMap.size());

            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableCalendar.setItems(FXCollections.observableArrayList(list));
        } catch (ParseException e) {
            logger.error("Дата введена неправильно в окне calendar");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Дата введена неправильно");
            alert.show();
        } catch (TaskException e) {
            logger.info("По заданым критериями ничего небыло найдено.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
