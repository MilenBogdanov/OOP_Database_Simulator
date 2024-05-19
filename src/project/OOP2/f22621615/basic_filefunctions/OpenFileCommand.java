package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The {@code OpenFileCommand} class represents a command to open a file.
 * It implements the {@link Command} and {@link FileCommand} interfaces and defines behavior
 * to execute the command and operate on a file.
 */
public class OpenFileCommand implements Command, FileCommand {
    private String fileName;
    private StringBuilder fileContent;
    private boolean fileOpened;

    /**
     * Constructs a new {@code OpenFileCommand} with the specified file content.
     *
     * @param fileContent the content of the file to be opened
     */
    public OpenFileCommand(StringBuilder fileContent) {
        this.fileContent = fileContent;
        this.fileOpened = false;
    }
    /**
     * Executes the open file command.
     * Reads the content of the specified file and appends it to the file content buffer.
     * If the file does not exist, creates a new empty file.
     */
    @Override
    public void execute() {
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Please specify a file to open.");
            return;
        }
        if (fileOpened){
            System.out.println("There is already opened file");
        }

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File not found. Created a new empty file.");
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
                if (!fileOpened) {
                    System.out.println("Opening file... ");
                    System.out.println("Successfully opened " + file.getName());
                    fileOpened = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error opening/creating the file: " + e.getMessage());
        }
    }
    /**
     * Sets the file name to be opened.
     *
     * @param fileName the name of the file to be opened
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * Retrieves the file name currently set for opening.
     *
     * @return the name of the file
     */
    @Override
    public String getFileName() {
        return fileName;
    }
    /**
     * Checks if a file has been successfully opened.
     *
     * @return {@code true} if a file has been opened, {@code false} otherwise
     */
    public boolean isFileOpened() {
        return fileOpened;
    }
}