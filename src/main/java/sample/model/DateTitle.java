package sample.model;

import java.util.Date;

/**
 * Класс для использования в TableView
 * @author Андрей Шерстюк
 */
public class DateTitle {
    /**Заглавие задачи*/
    private String title;
    /**Время выполнения задачи задачи*/
    private Date date;

    /**
     * Конструктор с параметрами
     * @param title - заглавие задачи
     * @param date - время выполнения задачи
     */
    public DateTitle(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    /**
     * Метод для получение поля {@link DateTitle#title}
     * @return возвращает заглавие задачи
     */
    public String getTitle() {
        return title;
    }

    /**
     * Метод для определения поля {@link DateTitle#title}
     * @param title - заглавие задачи
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Метод для получение поля {@link DateTitle#date}
     * @return возвращает время выполнения задачи
     */
    public Date getDate() {
        return date;
    }

    /**
     * Метод для определения поля {@link DateTitle#date}
     * @param date - время выполнения задачи
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
