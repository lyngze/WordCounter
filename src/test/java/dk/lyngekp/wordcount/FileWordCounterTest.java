package dk.lyngekp.wordcount;

import dk.lyngekp.file.FileUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class FileWordCounterTest {

    @Test
    void countWordsInFile() {
        FileUtil fileUtilMock = Mockito.mock(FileUtil.class);
        WordCounter wordCounterMock = Mockito.mock(WordCounter.class);
        String filepath = "testfile.txt";
        List<String> lines = List.of("test streng 1", "anden streng med ord", "endnu flere ord", "noget helt fjerde");
        Mockito.when(fileUtilMock.getFileLinesStream(filepath)).thenReturn(lines.stream());

        FileWordCounter fileWordCounter = new FileWordCounter(fileUtilMock, wordCounterMock);
        fileWordCounter.countWordsInFile(filepath);
        for (String line : lines) {
            Mockito.verify(wordCounterMock, Mockito.times(1)).countWordsInString(line);
        }
    }
}