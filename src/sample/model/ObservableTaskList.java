package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableTaskList {
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void add(TaskList taskList) {
        if (taskList == null)
            return;
        for (Task task : taskList) {
            tasks.add(task);
        }
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public void delete(Task task) {
        tasks.remove(task);
    }
}
