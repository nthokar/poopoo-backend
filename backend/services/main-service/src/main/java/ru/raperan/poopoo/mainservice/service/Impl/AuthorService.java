package ru.raperan.poopoo.mainservice.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.repositories.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;



}
