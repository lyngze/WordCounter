package dk.lyngekp.wordcount;

import dk.lyngekp.file.FileUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class DirectoryWordCounterTest {
    @Test
    void countsWordsForAllFilesInDirectory() {
        FileUtil fileUtilMock = Mockito.mock(FileUtil.class);
        FileWordCounter fileWordCounterMock = Mockito.mock(FileWordCounter.class);
        DirectoryWordCounter directoryWordCounter = new DirectoryWordCounter(fileUtilMock, fileWordCounterMock);

        String directoryPath = "directoryPath";
        List<String> fileList = List.of("file1.txt", "file2.txt", "file3.txt");
        Mockito.when(fileUtilMock.getFilePathsInDirectory(directoryPath)).thenReturn(fileList);
        directoryWordCounter.countWordsInDirectory(directoryPath);
        for (String filePath : fileList) {
            Mockito.verify(fileWordCounterMock, Mockito.times(1)).countWordsInFile(filePath);
        }
    }
}