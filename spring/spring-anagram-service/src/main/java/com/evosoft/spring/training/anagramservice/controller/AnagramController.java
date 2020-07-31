package com.evosoft.spring.training.anagramservice.controller;

import com.evosoft.spring.training.anagramservice.domain.AnagramResponse;
import com.evosoft.spring.training.anagramservice.domain.CustomErrorType;
import com.evosoft.spring.training.anagramservice.domain.WordStatistics;
import com.evosoft.spring.training.anagramservice.domain.WordLengthFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
final class AnagramController {
    private static final Logger LOGGER = Logger.getLogger(AnagramController.class.getName());

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(value = "/anagram/{word}", method = RequestMethod.GET)
    public ResponseEntity<?> findAnagram(@PathVariable("word") final String word) {
        final Resource resource = resourceLoader.getResource("classpath:/dictionary.txt");
        final String ordered = lexicographicalOrder(word);

        try (Stream<String> stream = Files.lines(Paths.get(resource.getURI()))) {
            List<String> result = stream
                    .filter(s -> lexicographicalOrder(s).equals(ordered))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new AnagramResponse(word, result), HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/length/{length}", method = RequestMethod.GET)
    public ResponseEntity<?> lengthFrequency(@PathVariable("length") final int length) {
        final Resource resource = resourceLoader.getResource("classpath:/dictionary.txt");

        try (Stream<String> stream = Files.lines(Paths.get(resource.getURI()))) {
            long count = stream
                    .filter(s -> s.length() == length)
                    .count();
            WordLengthFrequency result = WordLengthFrequency.builder()
                    .lengthOfWord(length)
                    .frequency(count)
                    .build();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ResponseEntity<?> statistics() {
        final Resource resource = resourceLoader.getResource("classpath:/dictionary.txt");

        try (Stream<String> stream = Files.lines(Paths.get(resource.getURI()))) {
            List<String> lines = stream.collect(Collectors.toList());
            final int min = lines.stream()
                    .mapToInt(String::length)
                    .min()
                    .orElse(0);

            final int max = lines.stream()
                    .mapToInt(String::length)
                    .max()
                    .orElse(0);

            final double avg = lines.stream()
                    .mapToInt(String::length)
                    .average()
                    .orElse(0);

            final WordStatistics result = WordStatistics.builder()
                    .shortestWord(min)
                    .longestWord(max)
                    .averageLengthOfWords(avg)
                    .build();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    private String lexicographicalOrder(final String input) {
        return input.chars()
                .sorted()
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
