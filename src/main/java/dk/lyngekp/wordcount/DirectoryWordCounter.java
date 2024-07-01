package dk.lyngekp.wordcount;

import dk.lyngekp.file.FileUtil;

import java.util.List;

public class DirectoryWordCounter {
    private final FileUtil fileUtil;
    private final FileWordCounter fileWordCounter;

    public DirectoryWordCounter(FileUtil fileUtil, FileWordCounter fileWordCounter) {
        this.fileUtil = fileUtil;
        this.fileWordCounter = fileWordCounter;
    }

    public void countWordsInDirectory(String directoryPath) {
        List<String> filePathsInDirectory = fileUtil.getFilePathsInDirectory(directoryPath);
        for (String filePath : filePathsInDirectory) {
            fileWordCounter.countWordsInFile(filePath);
        }
    }
}
