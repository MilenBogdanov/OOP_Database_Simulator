package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The {@code SaveFileAsCommand} class represents a command to save the file with a new name.
 * It implements the {@link Command} and {@link FileCommand} interfaces and defines behavior
 * to execute the command and operate on a file.
 */
public class SaveFileAsCommand implements Command, FileCommand {
    private String newFileName;
    private StringBuilder fileContent;

    /**
     * Constructs a new {@code SaveFileAsCommand} with the specified file content.
     *
     * @param fileContent the content of the file to be saved
     */
    public SaveFileAsCommand(StringBuilder fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * Sets the file name for saving.
     *
     * @param fileName the name of the file to be saved
     */
    @Override
    public void setFileName(String fileName) {
        this.newFileName = fileName;
    }

    /**
     * Retrieves the file name set for saving.
     *
     * @return the name of the file to be saved
     */
    @Override
    public String getFileName() {
        return this.newFileName;
    }

    /**
     * Executes the save file as command.
     * Saves the file with the new file name.
     */
    @Override
    public void execute(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            saveToFile(fileName, fileContent);
        } else {
            System.out.println("Please specify a file name to save.");
        }
    }

    /**
     * Saves the content to a file with the specified name.
     *
     * @param fileName    The name of the file to which the content will be saved.
     * @param fileContent The content to be saved to the file.
     */
    private void saveToFile(String fileName, StringBuilder fileContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(fileContent.toString());
            System.out.println("Successfully saved as " + new File(fileName).getName());
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }
}