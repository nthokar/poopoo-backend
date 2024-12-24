package ru.raperan.poopoo.mainservice.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
public interface JsonPolymorphicEntity {
    // Базовый интерфейс
}
