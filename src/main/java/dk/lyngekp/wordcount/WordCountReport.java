package dk.lyngekp.wordcount;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class WordCountReport {
    private final Set<String> excludedWords = new HashSet<>();
    private final Map<String, Integer> countedWords = new HashMap<>();
    private final Map<String, Integer> countedExcludedWords = new HashMap<>();
}
