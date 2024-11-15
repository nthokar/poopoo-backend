package ru.raperan.poopoo.exception;

public class SaveFileException extends MinioOperationException {

    public SaveFileException(Throwable cause) {
        super("Ошибка при сохранении файла в MinIO", cause);
    }

}