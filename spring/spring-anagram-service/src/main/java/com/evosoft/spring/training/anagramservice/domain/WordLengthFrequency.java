package com.evosoft.spring.training.anagramservice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class WordLengthFrequency {
    private int lengthOfWord;
    private long frequency;
}
