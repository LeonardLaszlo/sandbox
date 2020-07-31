package com.evosoft.spring.training.anagramservice.domain;

import lombok.Getter;

import java.util.List;

@Getter
public final class AnagramResponse {
    private final String original;
    private final List<String> anagramList;

    public AnagramResponse(final String originalWord, final List<String> anagramFindings) {
        this.original = originalWord;
        this.anagramList = anagramFindings;
    }
}
