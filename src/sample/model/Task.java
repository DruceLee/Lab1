package sample.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Класс задач со свойствами <b>title</b>, <b>time</b>, <b>start</b>, <b>end</b>, <b>interval</b>, <b>active</b>.
 * @author Андрей Шерстюк
 */
public class Task implements Serializable {
    /** Поле заглавия задачи */
    private String title;
    /** Поле времени выполнения задачи */
    private Date time = null;
    /** Поле начала выполнения задачи */
    private Date start = null;
    /** Поле конца выполнения задачи */
    private Date end = null;
    /** Поле интервала повторения задачи */
    private int interval;
    /** Поле активности задачи */
    private boolean active;

    /** Конструктор - создание нового объекта */
    public Task() {
    }

    /** Конструктор - создание нового объекта с определенными значениями
     * @param title - заглавие
     * @param active - активность
     */
    public Task(String title, boolean active) {
        this.title = title;
        this.active = active;
    }

    /** Конструктор - создание нового объекта с определенными значениями
     * @param title - заглавие
     * @param time - время выполнения задачи
     * @param active - активность
     */
    public Task(String title, Date time, boolean active) {
        this.title = title;
        this.time = time;
        this.active = active;
    }

    /** Конструктор - создание нового объекта с определенными значениями
     * @param title - заглавие
     * @param start - время начала выполнения задачи
     * @param end - время окончания выполнения задачи
     * @param interval - значение через которое задача будет повторятся
     * @param active - активность
     */
    public Task(String title, Date start, Date end, int interval, boolean active) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.active = active;
        this.time = null;
    }


    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param title - заглавие
     */
    public Task(String title) throws TaskException {
        setTitle(title);
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param title - заглавие
     * @param time - время выполнения задачи
     */
    public Task(String title, Date time) throws TaskException {
        setTitle(title);
        this.time = time;
    }

    /** Конструктор - создание нового объекта с определенными значениями
     * @param title - заглавие
     * @param start - время начала выполнения задачи
     * @param end - время окончания выполнения задачи
     * @param interval - значение через которое задача будет повторятся
     */
    public Task(String title, Date start, Date end, int interval) throws TaskException {
        setTitle(title);
        setTime(start, end, interval);
    }

    /** Метод для изменения повторяемой задачи на неповторяемую
     * @param time - время выполнения задачи
     * @exception TaskException - время задано неправильно
     */
    public void setTime(Date time) throws TaskException {
        if (time.getTime() < 0)
            throw new TaskException(" time < 0");
        this.time = time;
        if (isRepeated())
            this.interval = 0;
    }

    /** Метод для изменения неповторяемой задачи на повторяемую
     * @param start - время начала выполнения задачи
     * @param end - время конца выполнения задачи
     * @param interval - значение через которое задача будет повторятся
     * @exception TaskException - время задано неправильно
     */
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

    /** Метод для определения следующего время выполнения задачи
     * @param current - время, относительно которого проходит поиск
     */
    public Date nextTimeAfter(Date current) {
        Date date = new Date();
        int a;
        a = interval * 1000;
        if (isRepeated() && active) {
            if (current.before(start) && !current.equals(start))
                return start;
            for (Date i = (Date) start.clone(); i.before(end); i.setTime(i.getTime() + a)) {
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

    /**
     * Метод для определения является ли задача повторяемой
     * @return возвращает boolean значение, является ли задача повторяемой
     */
    public boolean isRepeated() {
        if (interval > 0)
            return true;
        else
            return false;
    }

    /**
     * Метод для получение поля {@link Task#time}
     * @return возвращает время выполнения задачи
     */
    public Date getTime() {
        return time;
    }

    /**
     * Метод для определения поля {@link Task#title}
     * @param title - заглавие задачи
     * @exception TaskException - заглавие задано неправильно
     */
    public void setTitle(String title) throws TaskException {
        if (title == null)
            throw new TaskException(" title is empty");
        this.title = title;
    }

    /**
     * Метод для получение поля {@link Task#start}
     * @return возвращает время начала выполнения задачи
     */
    public Date getStart() {
        return start;
    }

    /**
     * Метод для определения поля {@link Task#start}
     * @param start - время начала выполнения задачи
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Метод для получение поля {@link Task#end}
     * @return возвращает время конца выполнения задачи
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Метод для определения поля {@link Task#end}
     * @param end - время конца выполнения задачи
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * Метод для получение поля {@link Task#interval}
     * @return возвращает значение через которое задачу будет повторятся
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Метод для определения поля {@link Task#interval}
     * @param interval - значение через которое задачу будет повторятся
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * Метод для получения поля {@link Task#title}
     * @return возвращает заглавие задачи
     */
    public String getTitle() {
        return title;
    }

    /**
     * Метод для получения поля {@link Task#active}
     * @return возвращает активность задачи
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Метод для получения поля {@link Task#active}
     * @return возвращает активность задачи
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Метод для получение поля {@link Task#start}
     * @return возвращает время начала выполнения задачи
     */
    public Date getStartTime() {
        return start;
    }

    /**
     * Метод для получение поля {@link Task#end}
     * @return возвращает время конца выполнения задачи
     */
    public Date getEndTime() {
        return end;
    }

    /**
     * Метод для получение поля {@link Task#interval}
     * @return возвращает значение через которое задачу будет повторятся
     */
    public int getRepeatInterval() {
        return interval;
    }

    /**
     * Метод для вывода Task в виде строки
     * @return возвращает строку состоящую из элементов класса Task
     */
    public String toString() {
        return title + " " + time + " " + start + " " + end + " " + interval + " " + active + "\n";
    }

    /**
     * Метод для сравнивания объектов класса Task
     * @param object - объект с которым сравнивают даный
     * @return возвращает boolean значение, равны ли два объекта
     */
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

    /**
     * Метод возвращающий hashCode объекта класса Task
     * @return возвращает значение хеш-кода объекта*/
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

    /**
     * Метод для создания клона текущего объекта класса Task
     * @return возвращает клон текущего объекта
     */
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