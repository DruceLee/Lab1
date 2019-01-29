package sample.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Класс для хранения объектов типа Task
 * @author Андрей Шерстюк
 */
abstract public class TaskList implements Iterable<Task>, Serializable {

    /**
     * Метод для добавления задач
     * @param task Задача которую нужно добавить
     */
    abstract public void add(Task task);

    /**
     * Метод для удаления задачи с массиве
     * @param task Задача которую нужно удалить
     * @return возвращает boolean значение, существование удаленной задачи
     */
    abstract public boolean remove(Task task);

    /**
     * Метод который возвращает количество элементов в arraylist
     * @return возвращает количество элементов в массиве
     */
    abstract public int size();

    /**
     * Метод для получения задачи по индексу
     * @param index1 Номер задачи
     * @return возвращает задачу с номером index1
     */
    abstract public Task getTask(int index1);

    /**
     * Метод который возвращает клон списка задач что вызывает метод
     * @return возвращает клон даного списка задач
     */
    abstract public TaskList clone();

    /**
     * Метод для добавления задач
     * @param observableTaskList список задач которые нужно добавить в список
     */
    abstract public void add(ObservableTaskList observableTaskList);

    /**
     * Метод для определения равности списков задач
     * @param  tasks список с которым будет сравнён даный
     * @return возвращает boolean значение, если списки равны
     */
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

    /**
     * Метод для возвращения хеш-кода
     * @return возвращает значение хеш-кода списка
     */
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
     * @param from начало промежутка
     * @param to   конец промежутка
     * @return возвращает список задач которые будут выполнятся еще минимум 1 раз
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
