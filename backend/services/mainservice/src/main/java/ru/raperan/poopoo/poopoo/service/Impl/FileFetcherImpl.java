package ru.raperan.poopoo.poopoo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.poopoo.domain.AudioFileMeta;
import ru.raperan.poopoo.poopoo.repositories.AudioFileMetaRepository;
import ru.raperan.poopoo.poopoo.service.FileFetcher;
import ru.raperan.poopoo.poopoo.service.FileStorage;
import ru.raperan.poopoo.poopoo.service.exceptions.ResourceNotFoundException;

import java.util.UUID;

@Service
public class FileFetcherImpl implements FileFetcher {

    @Autowired
    FileStorage fileStorage;

    @Autowired
    AudioFileMetaRepository audioFileMetaRepository;

    @Override
    public Resource fetchFile(UUID fileId) throws ResourceNotFoundException {
        final AudioFileMeta audioFileMeta = audioFileMetaRepository.findById(fileId)
                                                                   .orElseThrow(() -> new ResourceNotFoundException("AudioFileMeta"));
        return fileStorage.getFile(audioFileMeta);
    }

}
