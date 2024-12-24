package ru.raperan.poopoo.mainservice.api;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("")
public interface SearchApi {

    @GetMapping("/search/{searchQuery}")
    List<String> searchSimilar(@PathVariable String searchQuery);

    @PostMapping("/search/{searchQuery}")
    List<?> search(@PathVariable String searchQuery, @Param("filter") String filter);

}
