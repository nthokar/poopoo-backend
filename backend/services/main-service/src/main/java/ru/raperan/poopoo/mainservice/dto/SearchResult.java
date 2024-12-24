package ru.raperan.poopoo.mainservice.dto;


import ru.raperan.poopoo.mainservice.domain.JsonPolymorphicEntity;

import java.util.List;

public record SearchResult (
    List<JsonPolymorphicEntity> results
) {

}
