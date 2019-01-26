package sample.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LinkedTaskList extends TaskList {
    private int index = 0;
    private Element start = null;
    private Element end = null;

    private class Element {
        Task task = null;
        Element next = null;
        Element previous = null;

        Element(Task value) {
            this.task = value;
        }

    }

    public LinkedTaskList() {
    }

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

    @Override
    public void add(ObservableTaskList observableTaskList) {
        for (int i = 0; i < observableTaskList.size(); i++) {
            add(observableTaskList.getTask(i));
        }
    }

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

    @Override
    public int size() {
        return index;
    }

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
