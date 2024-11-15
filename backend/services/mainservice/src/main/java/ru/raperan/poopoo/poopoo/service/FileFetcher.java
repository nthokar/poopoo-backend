package ru.raperan.poopoo.poopoo.service;

import org.springframework.core.io.Resource;
import ru.raperan.poopoo.poopoo.service.exceptions.ResourceNotFoundException;

import java.util.UUID;

public interface FileFetcher {

    Resource fetchFile(UUID fileId) throws ResourceNotFoundException;

}
