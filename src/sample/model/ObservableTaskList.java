package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Класс для использования в TableView
 * @author Андрей Шерстюк*/
public class ObservableTaskList {

    /**Список задач реагирующий на изменения, состоящий из объектов типа Task*/
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    /**
     * Метод для получение поля {@link ObservableTaskList#tasks}
     * @return возвращает список
     */
    public ObservableList<Task> getTasks() {
        return tasks;
    }

    /**
     * Метод для определения поля {@link ObservableTaskList#tasks}
     * @param tasks - время начала выполнения задачи
     */
    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Метод для добавления задач
     * @param task - задача для добавления в список
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Метод для добавления задач
     * @param taskList - список задач для добавления в tasks
     */
    public void add(TaskList taskList) {
        if (taskList == null)
            return;
        for (Task task : taskList) {
            tasks.add(task);
        }
    }

    /**
     * Метод для получения задачи по индексу
     * @param index - номер задачи которую нужно получить
     * @return возвращает задачу по индексу
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Метод для получения размера tasks
     * @return возвращает размер
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Метод для удаления задачи
     * @param task - задача которую нужно удалить
     */
    public void delete(Task task) {
        tasks.remove(task);
    }
}
