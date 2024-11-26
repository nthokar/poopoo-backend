package ru.raperan.poopoo.minio.exception;

public class ReceiveFileException extends MinioOperationException {

    public ReceiveFileException(Throwable cause) {
        super("Ошибка при получении файла в MinIO", cause);
    }

}