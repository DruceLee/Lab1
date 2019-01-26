package sample.model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String title;
    private Date time = null;
    private Date start = null;
    private Date end = null;
    private int interval;
    private boolean active;

    public Task() {
    }

    public Task(String title, boolean active) {
        this.title = title;
        this.active = active;
    }

    public Task(String title, Date time, boolean active) {
        this.title = title;
        this.time = time;
        this.active = active;
    }

    public Task(String title, Date start, Date end, int interval, boolean active) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.active = active;
        this.time = null;
    }

    public Task(String title, Date time, Date start, Date end, int interval, boolean active) {
        this.title = title;
        this.time = time;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.active = active;
    }

    public Task(String title) throws TaskException {
        setTitle(title);
    }

    //конструктор для задач которые не повторяются
    public Task(String title, Date time) throws TaskException {
        setTitle(title);
        this.time = time;
    }

    //конструктор для задач которые повторяются
    public Task(String title, Date start, Date end, int interval) throws TaskException {
        setTitle(title);
        setTime(start, end, interval);
    }

    //метод для изменения повторяемой задачи на неповторяемую
    public void setTime(Date time) throws TaskException {
        if (time.getTime() < 0)
            throw new TaskException(" time < 0");
        this.time = time;
        if (isRepeated())
            this.interval = 0;
    }

    //якщо задача не повторювалася метод має стати такою, що повторюється
    public void setTime(Date start, Date end, int interval) throws TaskException {
        if (start.getTime() < 0 || end.getTime() < 0)
            throw new TaskException(" start time or end time < 0");
        if (interval <= 0)
            throw new TaskException(" interval time <= 0");
        time = null;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    //запись нового времени относительно заданого
    public Date nextTimeAfter(Date current) {
        Date date = new Date();
        int a;
        a = interval * 1000;
        if (isRepeated() && active) {
            if (current.before(start) && !current.equals(start))
                return start;
            for (Date i = start; i.before(end); i.setTime(i.getTime() + a)) {
                if (current.getTime() >= i.getTime() && current.getTime() < i.getTime() + a && i.getTime() + a <= end.getTime()) {
                    date.setTime(i.getTime() + a);
                    return date;
                }
            }
        } else if (!isRepeated() && active) {
            if (time.after(current) && active)
                return time;
            else
                return null;
        }
        return null;
    }

    //проверка повторяемости задачи
    public boolean isRepeated() {
        if (interval > 0)
            return true;
        else
            return false;
    }

    //метод для возвращения времени следующего выполнения задачи, в случае если задача повторяется
    public Date getTime() {
        if (isRepeated())
            return start;
        else
            return time;
    }

    public void setTitle(String title) throws TaskException {
        if (title == null)
            throw new TaskException(" title is empty");
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getStartTime() {
        return start;
    }

    public Date getEndTime() {
        return end;
    }

    public int getRepeatInterval() {
        if (!isRepeated())
            return 0;
        else
            return interval;
    }

    public String toString() {
        return title + " " + time + " " + start + " " + end + " " + interval + " " + active + "\n";
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (object == null || object.getClass() != this.getClass())
            return false;

        Task task = (Task) object;
        if (isRepeated())
            return title.equals(task.getTitle())
                    && start.equals(task.getStartTime())
                    && end.equals(task.getEndTime())
                    && interval == task.getRepeatInterval()
                    && active == task.isActive();
        else
            return title.equals(task.getTitle())
                && time.equals(task.getTime())
                && active == task.isActive();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        if (isRepeated()) {
            result = prime * result + ((title == null) ? 0 : title.hashCode());
            result = prime * result + start.hashCode();
            result = prime * result + end.hashCode();
            result = prime * result + interval;
            result = prime * result + (!active ? 0 : 1);
        } else {
            result = prime * result + ((title == null) ? 0 : title.hashCode());
            result = prime * result + time.hashCode();
            result = prime * result + (!active ? 0 : 1);
        }
        return result;
    }

    @Override
    public Task clone() {
        Task task = new Task();
        try {
            task.setTitle(title);
            if (isRepeated()) {
                task.setTime(start, end, interval);
                task.time = this.time;
            } else {
                task.setTime(time);
                task.start = task.end = this.time;
                task.interval = this.interval;
            }
            task.setActive(active);
        } catch (TaskException e) {
            e.printStackTrace();
        }
        return task;
    }
}