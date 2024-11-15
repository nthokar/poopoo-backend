package ru.raperan.poopoo.poopoo.api;

import org.springframework.web.bind.annotation.PathVariable;
import ru.raperan.poopoo.poopoo.dto.SearchResult;

public interface SearchApi {

    SearchResult search(@PathVariable String searchQuery);

}
