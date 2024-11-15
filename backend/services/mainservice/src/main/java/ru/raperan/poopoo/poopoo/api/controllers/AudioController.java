package ru.raperan.poopoo.poopoo.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.raperan.poopoo.poopoo.service.FileFetcher;
import ru.raperan.poopoo.poopoo.service.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.UUID;

@RestController
public class AudioController {

    @Autowired
    FileFetcher fileFetcher;


    @RequestMapping(value = "/audio/{fileId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> getAudioFile(@PathVariable UUID fileId) throws IOException, ResourceNotFoundException {


        Resource resource = fileFetcher.fetchFile(fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));
        headers.add("Content-Disposition", "attachment");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
