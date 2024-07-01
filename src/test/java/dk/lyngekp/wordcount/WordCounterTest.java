package dk.lyngekp.wordcount;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WordCounterTest {
    @Test
    void countsWordsFromMultipleStrings() {
        List<String> wordList = List.of("test1", "test2", "test3", "test4", "test2; test3, test4...", "test3", "test4 test4");

        WordCounter wordCounter = new WordCounter(Collections.emptyList());
        wordList.forEach(wordCounter::countWordsInString);

        Map<String, Integer> countedWords = wordCounter.getWordCountReport().getCountedWords();
        assertEquals(countedWords.get("test1"), 1);
        assertEquals(countedWords.get("test2"), 2);
        assertEquals(countedWords.get("test3"), 3);
        assertEquals(countedWords.get("test4"), 4);
        assertEquals(countedWords.keySet().size(), 4);
    }

    @Test
    void countsWordsWithDanishLetters() {
        WordCounter wordCounter = new WordCounter(Collections.emptyList());
        wordCounter.countWordsInString("dåse cola, dåse øl med god rævesøvs, øl! øl! øl øl!");

        Map<String, Integer> countedWords = wordCounter.getWordCountReport().getCountedWords();
        assertEquals(2, countedWords.get("dåse"));
        assertEquals(1, countedWords.get("cola"));
        assertEquals(5, countedWords.get("øl"));
        assertEquals(1, countedWords.get("med"));
        assertEquals(1, countedWords.get("god"));
        assertEquals(1, countedWords.get("rævesøvs"));
    }

    @Test
    void countsWordsInStringWithNewlineCharacters() {
        WordCounter wordCounter = new WordCounter(Collections.emptyList());
        wordCounter.countWordsInString("linje 1 ting står her!\n\nSå kommer linje 3 ting\nLinje 4's ting er også med\n\n\nog så helt ned på linje 7");

        Map<String, Integer> countedWords = wordCounter.getWordCountReport().getCountedWords();
        assertEquals(4, countedWords.get("linje"));
        assertEquals(3, countedWords.get("ting"));
        assertEquals(1, countedWords.get("kommer"));
        assertEquals(1, countedWords.get("4s"));
        assertEquals(1, countedWords.get("også"));
        assertEquals(1, countedWords.get("helt"));
        assertEquals(1, countedWords.get("ned"));
    }

    @Test
    void countsExcludedWordsInSeparateList() {
        WordCounter wordCounter = new WordCounter(List.of("cola", "forbudt", "øl"));
        wordCounter.countWordsInString("dåse cola, dåse øl med god rævesøvs, øl! øl! øl øl!");

        Map<String, Integer> countedWords = wordCounter.getWordCountReport().getCountedWords();
        assertEquals(2, countedWords.get("dåse"));
        assertNull(countedWords.get("cola"));
        assertNull(countedWords.get("øl"));
        assertEquals(1, countedWords.get("med"));
        assertEquals(1, countedWords.get("god"));
        assertEquals(1, countedWords.get("rævesøvs"));

        Map<String, Integer> countedExcludedWords = wordCounter.getWordCountReport().getCountedExcludedWords();
        assertEquals(1, countedExcludedWords.get("cola"));
        assertEquals(5, countedExcludedWords.get("øl"));
        assertNull(countedExcludedWords.get("forbudt"));
    }
}