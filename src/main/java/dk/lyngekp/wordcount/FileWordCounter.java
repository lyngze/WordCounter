package dk.lyngekp.wordcount;

import dk.lyngekp.file.FileUtil;

import java.util.stream.Stream;

public class FileWordCounter {
    private final FileUtil fileUtil;
    private final WordCounter wordCounter;

    public FileWordCounter(FileUtil fileUtil, WordCounter wordCounter) {
        this.fileUtil = fileUtil;
        this.wordCounter = wordCounter;
    }

    public void countWordsInFile(String filePath) {
        try (Stream<String> fileLinesStream = fileUtil.getFileLinesStream(filePath)) {
            fileLinesStream.forEach(wordCounter::countWordsInString);
        }
    }
}
