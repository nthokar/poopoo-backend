package ru.raperan.poopoo.service;

import java.io.InputStream;
import java.util.UUID;

public interface FileStorageClient {

    /**
     * Сохраняет переданные байты как файл.
     *
     * @param bytes сохраняемые байты
     * @param id    id файла
     */
    void save(byte[] bytes, String id);

    /**
     * Возвращает тело сообщения.
     *
     * @param id название файла
     * @return тело сообщения
     */
    InputStream find(String id);

}