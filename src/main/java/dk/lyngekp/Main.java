package dk.lyngekp;

import dk.lyngekp.wordcount.DirectoryWordCounter;
import dk.lyngekp.wordcount.FileWordCounter;
import dk.lyngekp.wordcount.WordCounter;
import dk.lyngekp.file.FileUtil;
import dk.lyngekp.writer.GroupByStartingLetterFileWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar WordCounter.jar <directory> <excluded-words-file>");
            return;
        }

        String directoryPath = args[0];
        if (directoryPath == null || directoryPath.isEmpty()) {
            System.out.println("Directory path is empty");
            return;
        }

        FileUtil fileUtil = new FileUtil();
        List<String> excludedWords = Collections.emptyList();
        if (args.length > 1) {
            String excludedWordsFile = args[1];
            if (excludedWordsFile != null && !excludedWordsFile.isEmpty()) {
                Stream<String> fileLinesStream = fileUtil.getFileLinesStream(excludedWordsFile);
                excludedWords = fileLinesStream != null ? fileLinesStream.toList() : Collections.emptyList();
            }
        }

        WordCounter wordCounter = new WordCounter(excludedWords);
        DirectoryWordCounter directoryWordCounter = new DirectoryWordCounter(fileUtil, new FileWordCounter(fileUtil, wordCounter));
        directoryWordCounter.countWordsInDirectory(directoryPath);

        String reportDirectory = directoryPath + "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH mm ss"));

        GroupByStartingLetterFileWriter groupByStartingLetterFileWriter = new GroupByStartingLetterFileWriter(fileUtil);
        groupByStartingLetterFileWriter.createReport(wordCounter.getWordCountReport(), reportDirectory);
    }
}