package sample.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

abstract public class TaskList implements Iterable<Task>, Serializable {

    abstract public void add(Task task);

    abstract public boolean remove(Task task);

    abstract public int size();

    abstract public Task getTask(int index1);

    abstract public TaskList clone();

    abstract public void add(ObservableTaskList observableTaskList);

    public boolean equals(TaskList tasks) {
        boolean b = false;
        int j = 0;
        for (int i = 0; i < size(); i++) {
            if (this.getTask(i).equals(tasks.getTask(i)))
                j++;
        }
        if (j == size())
            return true;
        return b;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;

        Iterator<Task> iterator = this.iterator();
        while (iterator.hasNext()) {
            result += prime + iterator.next().hashCode();
        }
        return result;
    }

    /**
     * Метод для нахождения активных задач в промежутке
     *
     * @param from начало промежутка
     * @param to   конец промежутка
     * @return arrayTaskList Список задач которые будут выполнятся еще минимум 1 раз
     */

    public TaskList incoming(Date from, Date to) {
        TaskList arrayTaskList = new ArrayTaskList();
        Date date = new Date();

        for (int i = 0; i < size(); i++) {
            if (getTask(i).nextTimeAfter(from).before(to)) {
                arrayTaskList.add(getTask(i));
            }
        }
        return arrayTaskList;
    }
}
