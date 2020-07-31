package com.evosoft.spring.training.anagramservice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class WordStatistics {
    private int shortestWord;
    private int longestWord;
    private double averageLengthOfWords;
}
