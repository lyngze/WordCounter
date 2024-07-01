package dk.lyngekp.wordcount;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class WordCounter {
    private final WordCountReport wordCountReport;

    public WordCounter(List<String> excludedWords) {
        this.wordCountReport = new WordCountReport();
        this.wordCountReport.getExcludedWords().addAll(excludedWords.stream().map(String::toLowerCase).toList());
    }

    public void countWordsInString(String line) {
        String[] words = sanitizeLine(line).split("\\s+");
        for (String word : words) {
            countWord(word);
        }
    }

    private String sanitizeLine(String line) {
        // Ignore punctuation and keep words only
        return line.replaceAll("[^\\w\\sæøåÆØÅ]", "");
    }

    private void countWord(String word) {
        if (word.isEmpty()) {
            return;
        }

        String caseInsensitiveWord = word.toLowerCase();
        if (wordCountReport.getExcludedWords().contains(caseInsensitiveWord)) {
            addWordToCounter(caseInsensitiveWord, wordCountReport.getCountedExcludedWords());
        } else {
            addWordToCounter(caseInsensitiveWord, wordCountReport.getCountedWords());
        }
    }

    private void addWordToCounter(String caseInsensitiveWord, Map<String, Integer> counter) {
        counter.put(caseInsensitiveWord, counter.getOrDefault(caseInsensitiveWord, 0) + 1);
    }
}
