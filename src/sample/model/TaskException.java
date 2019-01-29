package sample.model;

/**
 * Класс для генерации исключений
 * @author Андрей Шерстюк
 */
public class TaskException extends Exception {

    /**
     * Конструктор с параметром
     * @param message - сообщение
     */
    public TaskException(String message) {
        super(message);
    }

    public TaskException() {
        super();
    }
}
