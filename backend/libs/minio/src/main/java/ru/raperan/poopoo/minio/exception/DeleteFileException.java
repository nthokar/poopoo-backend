package ru.raperan.poopoo.minio.exception;

public class DeleteFileException extends MinioOperationException {

    public DeleteFileException(Exception e) {
        super("Ошибка при попытке удаления файла из MinIO", e);
    }

}
