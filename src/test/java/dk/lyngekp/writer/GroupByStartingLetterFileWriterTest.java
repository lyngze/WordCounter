package dk.lyngekp.writer;

import dk.lyngekp.file.FileUtil;
import dk.lyngekp.wordcount.WordCountReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupByStartingLetterFileWriterTest {
    private static String REPORT_DIRECTORY = "src/test/resources/groupbytest";
    private FileUtil fileUtil = new FileUtil();

    @BeforeEach
    void purgeOutputFolder() throws IOException {
        List<String> filePathsInDirectory = fileUtil.getFilePathsInDirectory(REPORT_DIRECTORY);
        for (String filePath : filePathsInDirectory) {
            Files.delete(Paths.get(filePath));
        }
    }

    @Test
    void groupsByStartingLetterOfCountedWordsWithoutExcluded() {
        WordCountReport report = new WordCountReport();
        report.getCountedWords().put("lorem", 20);
        report.getCountedWords().put("ipsum", 10);

        GroupByStartingLetterFileWriter groupByStartingLetterFileWriter = new GroupByStartingLetterFileWriter(fileUtil);

        groupByStartingLetterFileWriter.createReport(report, REPORT_DIRECTORY);
        assertEquals(1, fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/i.txt").count());
        assertTrue(fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/i.txt").anyMatch(s -> s.equals("ipsum 10")));
        assertEquals(1, fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/l.txt").count());
        assertTrue(fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/l.txt").anyMatch(s -> s.equals("lorem 20")));
        assertEquals(0, fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/excludedWords.txt").count());
    }

    @Test
    void groupsByStartingLetterOfCountedWordsWithExcluded() {
        WordCountReport report = new WordCountReport();
        report.getExcludedWords().add("lorem");
        report.getCountedExcludedWords().put("lorem", 20);
        report.getCountedExcludedWords().put("elon", 5);
        report.getCountedWords().put("ipsum", 10);

        GroupByStartingLetterFileWriter groupByStartingLetterFileWriter = new GroupByStartingLetterFileWriter(fileUtil);

        groupByStartingLetterFileWriter.createReport(report, REPORT_DIRECTORY);
        assertEquals(1, fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/i.txt").count());
        assertTrue(fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/i.txt").allMatch(s -> s.equals("ipsum 10")));
        assertNull(fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/l.txt")); // File does not exist
        assertEquals(2, fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/excludedWords.txt").count());
        assertTrue(fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/excludedWords.txt").anyMatch(s -> s.equals("lorem 20")));
        assertTrue(fileUtil.getFileLinesStream(REPORT_DIRECTORY + "/excludedWords.txt").anyMatch(s -> s.equals("elon 5")));
    }
}