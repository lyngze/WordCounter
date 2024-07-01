package dk.lyngekp.writer;

import dk.lyngekp.file.FileUtil;
import dk.lyngekp.wordcount.WordCountReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByStartingLetterFileWriter {
    private final FileUtil fileUtil;

    public GroupByStartingLetterFileWriter(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public void createReport(WordCountReport wordCountReport, String reportDirectory) {
        Map<Character, List<String>> startingLetterReport = groupCountedWordsByStartingLetter(wordCountReport.getCountedWords());
        fileUtil.createDirectory(reportDirectory);
        writeReportToDirectory(startingLetterReport, reportDirectory);
        writeExcludedInfo(wordCountReport, reportDirectory);
    }

    private void writeReportToDirectory(Map<Character, List<String>> startingLetterReport, String reportDirectory) {
        for (Map.Entry<Character, List<String>> letterReport : startingLetterReport.entrySet()) {
            String fileName = letterReport.getKey() + ".txt";
            fileUtil.writeStringToFile(reportDirectory + "/" + fileName, String.join("\n", letterReport.getValue()));
        }
    }

    private void writeExcludedInfo(WordCountReport wordCountReport, String reportDirectory) {
        String excludedInfo = wordCountReport.getCountedExcludedWords().entrySet().stream()
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .collect(Collectors.joining("\n"));

        fileUtil.writeStringToFile(reportDirectory + "/excludedWords.txt", excludedInfo);
    }

    private Map<Character, List<String>> groupCountedWordsByStartingLetter(Map<String, Integer> countedWords) {
        Map<Character, List<String>> startingLetterReport = new HashMap<>();
        for (Map.Entry<String, Integer> countedWordEntry : countedWords.entrySet()) {
            char startingLetter = countedWordEntry.getKey().charAt(0);
            if (!startingLetterReport.containsKey(startingLetter)) {
                startingLetterReport.put(startingLetter, new ArrayList<>());
            }

            List<String> letterLines = startingLetterReport.get(startingLetter);
            letterLines.add(countedWordEntry.getKey() + " " + countedWordEntry.getValue());
        }

        return startingLetterReport;
    }
}
