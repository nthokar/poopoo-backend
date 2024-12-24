package ru.raperan.poopoo.mainservice.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.domain.FileMeta;
import ru.raperan.poopoo.mainservice.repositories.FileMetaRepository;
import ru.raperan.poopoo.mainservice.service.FileFetcher;
import ru.raperan.poopoo.mainservice.service.FileStorage;
import ru.raperan.poopoo.mainservice.service.exceptions.ResourceNotFoundException;

import java.util.UUID;

@Service
public class FileFetcherImpl implements FileFetcher {

    @Autowired
    FileStorage fileStorage;

    @Autowired
    FileMetaRepository fileMetaRepository;

    @Override
    public Resource fetchFile(UUID fileId) throws ResourceNotFoundException {
        final FileMeta fileMeta = fileMetaRepository.findById(fileId)
                                                    .orElseThrow(() -> new ResourceNotFoundException("AudioFileMeta"));
        return fileStorage.getFile(fileMeta);
    }

}
