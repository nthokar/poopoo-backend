package ru.raperan.poopoo.mainservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.domain.JsonPolymorphicEntity;
import ru.raperan.poopoo.mainservice.dto.AlbumBaseInfo;
import ru.raperan.poopoo.mainservice.dto.AuthorBaseInfo;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;
import ru.raperan.poopoo.mainservice.repositories.AlbumRepository;
import ru.raperan.poopoo.mainservice.repositories.AuthorRepository;
import ru.raperan.poopoo.mainservice.repositories.TrackRepository;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final TrackService trackService;

    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final AuthorRepository authorRepository;

    public List<?> findMostSimilar(String searchQuery, String filter) {
        switch (filter) {
            case "tracks": {
                List<String> names = trackRepository.findAllNames();
                List<Pair<String, Double>> mostSimilar = execQueryFlat(searchQuery, names);
                return mostSimilar.stream()
                        .map(Pair::getLeft)
                        .flatMap(trackName -> trackRepository.findAllByName(trackName).stream())
                        .map(trackService::getTrackBaseInfo)
                        .toList();
            }
            case "artists": {
                List<String> names = authorRepository.findAllNames();
                List<Pair<String, Double>> mostSimilar = execQueryFlat(searchQuery, names);
                return mostSimilar.stream()
                        .map(Pair::getLeft)
                        .flatMap(trackName -> authorRepository.findAllByName(trackName).stream())
                        .map(AuthorBaseInfo::fromAuthor)
                        .toList();
            }
            case "albums": {
                List<String> names = albumRepository.findAllNames();
                List<Pair<String, Double>> mostSimilar = execQueryFlat(searchQuery, names);
                return mostSimilar.stream()
                        .map(Pair::getLeft)
                        .flatMap(trackName -> albumRepository.findAllByName(trackName).stream())
                        .map(a -> AlbumBaseInfo.fromAlbum(a, trackService.findTracksBaseInfoByAlbumId(a)))
                        .toList();
            }
            default: throw new IllegalArgumentException("Unknown filter: " + filter);
        }
    }
//
//    public List<JsonPolymorphicEntity> findMostSimilarObjects(String searchQuery) {
//
//        var names = Stream.of(
//                albumRepository.findAllNames().stream().map(a -> Pair.of(0, a)),
//                trackRepository.findAllNames().stream().map(a -> Pair.of(1, a)),
//                authorRepository.findAllNames().stream().map(a -> Pair.of(2, a)))
//                .flatMap(i -> i)
//                .toList();
//
//        List<Pair<Pair<Integer, String>, Double>> mostSimilar = execQuery(searchQuery, names);
//        return mostSimilar.stream()
//                .map(Pair::getLeft)
//                .map(p -> switch (p.getLeft()) {
//                    case 0 -> albumRepository.findByName(p.getRight()).get();
//                    case 1 -> trackRepository.findByName(p.getRight()).get();
//                    case 2 -> authorRepository.findByName(p.getRight()).get();
//                    default -> throw new RuntimeException("Unexpected value: " + p.getLeft());
//                })
//                .toList();
//    }

    public List<String> findMostSimilarStrings(String searchQuery) {

        var names = Stream.of(
                        albumRepository.findAllNames().stream().map(a -> Pair.of(0, a)),
                        trackRepository.findAllNames().stream().map(a -> Pair.of(1, a)),
                        authorRepository.findAllNames().stream().map(a -> Pair.of(2, a)))
                .flatMap(i -> i)
                .toList();

        List<Pair<Pair<Integer, String>, Double>> mostSimilar = execQuery(searchQuery, names);
        return mostSimilar.stream()
                .map(Pair::getLeft)
                .map(Pair::getRight)
                .toList();
    }


    public List<Pair<Pair<Integer, String>, Double>> execQuery(String query, List<Pair<Integer, String>> candidates) {
        List<Pair<Pair<Integer, String>, Double>> rated = new ArrayList<>();

        String normilized1 = normalize(query);
        for (Pair<Integer, String> candidate : candidates) {
            String normilized2 = normalize(candidate.getRight());

            double result = quasiLivenstein(normilized1,normilized2);
            rated.add(Pair.of(candidate, result));
        }
        List<Pair<Pair<Integer, String>, Double>> result = new ArrayList<>();
        rated.sort(Comparator.comparingDouble(Pair::getRight));
        double sigma = standardDeviation(rated);
        double period = 1;
        double avg = average(rated);
        for (var fileRecordValueWrapper : rated) {
            if (fileRecordValueWrapper.getRight() >= avg + (period * sigma)) {
                while (fileRecordValueWrapper.getRight() >= avg + (period + 1) * sigma) {
                    period += 1;
                }
                result.add(fileRecordValueWrapper);
            }
        }
        return result;
    }

    public List<Pair<String, Double>> execQueryFlat(String query, List<String> candidates) {
        List<Pair<String, Double>> rated = new ArrayList<>();

        String normilized1 = normalize(query);
        for (String candidate : candidates) {
            String normilized2 = normalize(candidate);

            double result = quasiLivenstein(normilized1,normilized2);
            rated.add(Pair.of(candidate, result));
        }
        List<Pair<String, Double>> result = new ArrayList<>();
        rated.sort(Comparator.comparingDouble(Pair::getRight));
        double sigma = standardDeviationFlat(rated);
        double period = 1;
        double avg = averageFlat(rated);
        for (var fileRecordValueWrapper : rated) {
            if (fileRecordValueWrapper.getRight() >= avg + (period * sigma)) {
                while (fileRecordValueWrapper.getRight() >= avg + (period + 1) * sigma) {
                    period += 1;
                }
                result.add(fileRecordValueWrapper);
            }
        }
        return result;
    }


    private List<Pair<Pair<Integer, String>, Integer>> findMostSimilarStrings(String target, List<Pair<Integer, String>> candidates) {
        List<Pair<Pair<Integer, String>, Integer>> mostSimilar = new ArrayList<>();

        for (Pair<Integer, String> candidate : candidates) {
            int distance = quasiLivenstein(target, candidate.getRight());
            mostSimilar.add(Pair.of(candidate, distance));
        }

        mostSimilar.sort(Comparator.comparingInt(Pair::getRight));

        return mostSimilar;
    }

    private double averageFlat(List<Pair<String, Double>> ratedFiles) {
        double sum = 0;
        if (Objects.isNull(ratedFiles) || ratedFiles.isEmpty()) return 0;
        for (var ratedFile : ratedFiles) {
            sum += ratedFile.getRight();
        }
        return sum/ratedFiles.size();
    }

    private double average(List<Pair<Pair<Integer, String>, Double>> ratedFiles) {
        double sum = 0;
        if (Objects.isNull(ratedFiles) || ratedFiles.isEmpty()) return 0;
        for (var ratedFile : ratedFiles) {
            sum += ratedFile.getRight();
        }
        return sum/ratedFiles.size();
    }

    private double standardDeviationFlat(List<Pair<String, Double>> ratedFiles) {
        double average = averageFlat(ratedFiles);
        double sum = 0;
        if (Objects.isNull(ratedFiles) || ratedFiles.isEmpty()) return 0;
        for (var ratedFile : ratedFiles) {
            double x = ratedFile.getRight();
            sum += Math.pow((x - average), 2);
        }
        return Math.sqrt(sum / ratedFiles.size());
    }
    
    private double standardDeviation(List<Pair<Pair<Integer, String>, Double>> ratedFiles) {
        double average = average(ratedFiles);
        double sum = 0;
        if (Objects.isNull(ratedFiles) || ratedFiles.isEmpty()) return 0;
        for (var ratedFile : ratedFiles) {
            double x = ratedFile.getRight();
            sum += Math.pow((x - average), 2);
        }
        return Math.sqrt(sum / ratedFiles.size());
    }

    private static int quasiLivenstein(String str1, String str2) {
        int total = 0;
        int i = 0;

        while (i < str1.length()) {
            int count = 1;
            int k = 1;
            while (i + count <= str1.length()) {
                String substring = str1.substring(i, i + count);
                if (!str2.contains(substring))  {
                    count -= 1;
                    break;
                }
                count += 1;
                if (i  + count > str1.length()) {
                    count -= 1;
                    break;
                }
                k = k * (count + 1);
            }
            if (count > 0) i += count;
            else i++;
            total += k;
        }
        return total;
    }

    private static String[] getWords(String s) {
        return s.split("\\s+");
    }

    private static String normalize(String string)
    {
        string = string.toLowerCase();

        String[] words = getWords(string);
        Arrays.sort(words);
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word);
            //sb.append(' ');
        }
        //sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
