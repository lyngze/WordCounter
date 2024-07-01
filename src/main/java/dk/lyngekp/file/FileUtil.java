package dk.lyngekp.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {
    public List<String> getFilePathsInDirectory(String directoryPath) {
        List<String> filePathList = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        filePathList.add(file.getAbsolutePath());
                    }
                }
            }
        }

        return filePathList;
    }

    public Stream<String> getFileLinesStream(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if(!Files.exists(path)) {
                return null;
            }

            return Files.lines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("File could not be opened for given path: " + filePath, e);
        }
    }

    public void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists()) {
            return;
        }

        boolean directoryCreated = directory.mkdir();
        if (!directoryCreated) {
            throw new RuntimeException("Directory could not be created: " + directoryPath);
        }
    }

    public void writeStringToFile(String filePath, String content) {
        try {
            Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not write file to path: " + filePath, e);
        }
    }
}
