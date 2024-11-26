package ru.raperan.poopoo.minio.exception;

public class SaveFileException extends MinioOperationException {

    public SaveFileException(Throwable cause) {
        super("Ошибка при сохранении файла в MinIO", cause);
    }

}