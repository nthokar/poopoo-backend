package ru.raperan.poopoo.mainservice.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.raperan.poopoo.mainservice.api.SearchApi;
import ru.raperan.poopoo.mainservice.service.Impl.SearchService;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchController implements SearchApi {

    private final SearchService searchService;

    @Override
    public List<?> search(String searchQuery, String filter) {
        List<?> result = searchService.findMostSimilar(searchQuery, filter);
        return result;
    }

    @Override
    public List<String> searchSimilar(String searchQuery) {
        return new HashSet<>(searchService.findMostSimilarStrings(searchQuery))
                .stream()
                .toList();
    }
}
