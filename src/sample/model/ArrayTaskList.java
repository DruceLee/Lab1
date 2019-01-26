package sample.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

/**
 * Класс для сохранения задач в массиве
 */
public class ArrayTaskList extends TaskList {
    private Task[] tasks;
    private int index = 0;

    /**
     * Метод для добавления задач
     *
     * @param task Задача которую нужно добавить
     */
    @Override
    public void add(Task task) {
        if (index == 0)
            tasks = new Task[10];
        if (tasks.length == index) {
            Task[] tasks1 = new Task[index * 2];
            for (int i = 0; i < index; i++)
                tasks1[i] = tasks[i];
            tasks = tasks1;
        }
        tasks[index++] = task;
    }

    @Override
    public void add(ObservableTaskList observableTaskList) {
        for (int i = 0; i < observableTaskList.size(); i++) {
            add(observableTaskList.getTask(i));
        }
    }

    /**
     * Метод для удаления задачи с массиве
     *
     * @param task Задача которую нужно удалить
     * @return existence Существование удаленной задачи
     */
    @Override
    public boolean remove(Task task) {
        boolean existence = false;
        for (int i = 0; i < index; i++) {
            if (tasks[i].equals(task)) {
                existence = true;
                for (int j = i; j < index; j++) {
                    tasks[j] = tasks[j + 1];
                }
                index--;
                break;
            }
        }
        return existence;
    }

    /**
     * Метод который возвращает
     * количество элементов в arraylist
     *
     * @return index Количество элементов в массиве
     */
    @Override
    public int size() {
        return index;
    }

    /**
     * Метод для получения задачи по индексу
     *
     * @param index1 Номер задачи
     * @return tasks[index1] Задача с номером index1
     */
    @Override
    public Task getTask(int index1) {
        return tasks[index1];
    }


    public TaskList incoming(Date from, Date to) {
        TaskList arrayTaskList = new ArrayTaskList();
        Date date = new Date();

        for (int i = 0; i < size(); i++) {
            if (!(getTask(i).nextTimeAfter(from).equals(date)) && getTask(i).nextTimeAfter(from).before(to)) {
                arrayTaskList.add(getTask(i));
            }
        }
        return arrayTaskList;
    }

    @Override
    public Iterator<Task> iterator() {
        Iterator<Task> it = new Iterator<>() {
            private int i = 0;
            private Task task = null;

            @Override
            public boolean hasNext() {
                return i < index;
            }

            @Override
            public Task next() {
                if (hasNext()) {
                    task = getTask(i++);
                    return task;
                }
                return null;
            }

            @Override
            public void remove() {
                if (i > 0) {
                    i--;
                    ArrayTaskList.this.remove(task);
                } else {
                    throw new IllegalStateException();
                }
            }
        };
        return it;
    }

    @Override
    public String toString() {
        return Arrays.toString(tasks);
    }

    @Override
    public ArrayTaskList clone() {
        ArrayTaskList ar = new ArrayTaskList();

        for (int i = 0; i < index; i++) {
            ar.add(tasks[i].clone());
        }

        return ar;
    }
}