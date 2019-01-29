package sample.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для сохранения задач в списке
 * @author Андрей Шерстюк
 */
public class LinkedTaskList extends TaskList {
    /** Размер списка */
    private int index = 0;

    /** Объект класса Element хранящий ссылку начала списка */
    private Element start = null;

    /** Объект класса Element хранящий ссылку конеца списка */
    private Element end = null;

    /** Внутренний класс */
    private class Element {
        /** Объект типа Task */
        Task task;

        /** Объект класса Element хранящий ссылку на следующий элемент */
        Element next = null;

        /** Объект класса Element хранящий ссылку на предыдущий элемент */
        Element previous = null;

        /**
         * Конструктор класса Element с параметрами
         * @param value - объект типа Task
         */
        Element(Task value) {
            this.task = value;
        }

    }

    /** Конструктор без параметров */
    public LinkedTaskList() {
    }


    /**
     * Метод для добавления задач
     * @param task1 Задача которую нужно добавить
     */
    @Override
    public void add(Task task1) {
        if (task1 == null)
            System.out.println("Nothing added! Task is empty.");
        else {
            if (index == 0) {
                start = new Element(task1);
                end = start;
                index++;
            } else {
                end.next = new Element(task1);
                end.next.previous = end;
                end = end.next;
                index++;
            }
        }
    }

    /**
     * Метод для добавления задач
     * @param observableTaskList список задач которые нужно добавить в список
     */
    @Override
    public void add(ObservableTaskList observableTaskList) {
        for (int i = 0; i < observableTaskList.size(); i++) {
            add(observableTaskList.getTask(i));
        }
    }

    /**
     * Метод для удаления задачи с массиве
     * @param task Задача которую нужно удалить
     * @return возвращает boolean значение, существование удаленной задачи
     */
    @Override
    public boolean remove(Task task) {
        if (task == null)
            return false;
        else {
            Element counter = start;

            while (counter != null) {
                if (!counter.task.equals(task)) {
                    counter = counter.next;
                } else {
                    if (counter.next == null) {
                        counter.task = null;
                        end = counter.previous;
                        counter = counter.previous;
                        counter.next = null;
                    } else if (counter.next.next == null) {
                        counter.task = counter.next.task;
                        counter.next = null;
                        end = counter;
                    } else {
                        counter.task = counter.next.task;
                        counter.next = counter.next.next;
                        counter = counter.next;
                        counter.previous = counter.previous.previous;
                    }
                    index--;
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Метод который возвращает количество элементов в arraylist
     * @return возвращает количество элементов в массиве
     */
    @Override
    public int size() {
        return index;
    }

    /**
     * Метод для получения задачи по индексу
     * @param index1 Номер задачи
     * @return возвращает задачу с номером index1
     */
    @Override
    public Task getTask(int index1) {
        int i;

        if (index1 <= index / 2) {
            i = 0;
            Element counter = start;

            while (counter != null) {
                if (i == index1) {
                    return counter.task;
                } else {
                    counter = counter.next;
                    i++;
                }
            }
        } else {
            i = index - 1;
            Element counter = end;

            while (counter != null) {
                if (i == index1) {
                    return counter.task;
                } else {
                    counter = counter.previous;
                    i--;
                }
            }
        }

        return null;
    }

    @Override
    public Iterator<Task> iterator() {
        Iterator<Task> it = new Iterator<>() {
            private Element element = start;
            private boolean vizov;

            @Override
            public boolean hasNext() {
                if (index == 0)
                    return false;
                return element != null;
            }

            @Override
            public Task next() {
                Task task;
                if (hasNext()) {
                    task = element.task;
                    element = element.next;
                } else {
                    return null;
                }
                vizov = true;
                return task;
            }

            @Override
            public void remove() throws IllegalStateException {
                if (hasNext() && vizov) {
                    LinkedTaskList.this.remove(element.previous.task);
                    vizov = false;
                } else {
                    throw new IllegalStateException();
                }
            }
        };
        return it;
    }

    /**
     * Метод для возвращения списка задач в виде строки
     * @return возвращает строку состоящую из объектов типа Task
     */
    @Override
    public String toString() {
        Task task;
        String s = "LinkedTaskList{" +
                "n=" + index +
                "}: ";
        for (int i = 0; i < index; i++) {
            task = getTask(i);
            if (task.isRepeated())
                s += "\n[task №" + i + ", title = " + task.getTitle() +
                        ", startTime = " + task.getStartTime() + ", endTime = " +
                        task.getEndTime() + ", interval = " + task.getRepeatInterval() + ", active = " +
                        task.isActive() + ".]";
            else
                s += "\n[task №" + i + ", title = " + task.getTitle() +
                        ", time = " + task.getTime() + ", active = " +
                        task.isActive() + ".]";
        }
        return s;
    }

    /**
     * Метод который возвращает клон списка задач что вызывает метод
     * @return возвращает клон даного списка задач
     */
    @Override
    public LinkedTaskList clone() {
        LinkedTaskList ls = new LinkedTaskList();
        Element current = start;
        for (int i = 0; i < index; i++) {
            ls.add(current.task);
            current = current.next;
        }
        return ls;
    }
}
