package ru.raperan.poopoo.exception;

public class DeleteFileException extends MinioOperationException {

    public DeleteFileException(Exception e) {
        super("Ошибка при попытке удаления файла из MinIO", e);
    }

}
