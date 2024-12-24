package ru.raperan.poopoo.mainservice.dto;

import ru.raperan.poopoo.mainservice.domain.Author;

import java.util.UUID;

public record AuthorBaseInfo(
    UUID id,
    String name

) {

    public static AuthorBaseInfo fromAuthor(Author author) {
        return new AuthorBaseInfo(author.getId(), author.getName());
    }
}
