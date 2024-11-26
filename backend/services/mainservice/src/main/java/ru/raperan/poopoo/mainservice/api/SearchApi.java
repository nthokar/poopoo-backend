package ru.raperan.poopoo.mainservice.api;

import org.springframework.web.bind.annotation.PathVariable;
import ru.raperan.poopoo.mainservice.dto.SearchResult;

public interface SearchApi {

    SearchResult search(@PathVariable String searchQuery);

}
