package dk.easv.moviecollection.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Creator {
    private static final String MOVIES_DIRECTORY_PATH = "src/main/resources/dk/easv/moviecollection/movies";

    protected String setFilePath(String filePath) throws IOException {
        Path sourcePath = Paths.get(filePath);

        Path destinationDir = Paths.get(MOVIES_DIRECTORY_PATH);

        // Ensure the destination directory exists
        if (!Files.exists(destinationDir)) {
            Files.createDirectories(destinationDir);
        }

        // Create the destination path by appending the filename to the directory
        Path destinationPath = destinationDir.resolve(sourcePath.getFileName());

        // Move the file
        Files.move(sourcePath, destinationPath);

        // Update the path field with the destination path
        return destinationPath.toString();

    }
}
