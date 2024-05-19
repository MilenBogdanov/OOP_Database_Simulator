package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * The {@code SaveFileCommand} class represents a command to save the currently opened file.
 * It implements the {@link Command} and {@link FileCommand} interfaces and defines behavior
 * to execute the command and operate on a file.
 */
public class SaveFileCommand implements Command, FileCommand {
    private OpenFileCommand openFileCommand;
    private StringBuilder fileContent;
    /**
     * Constructs a new {@code SaveFileCommand} with the specified {@code OpenFileCommand} and file content.
     *
     * @param openFileCommand the {@code OpenFileCommand} instance used for opening the file
     * @param fileContent     the content of the file to be saved
     */
    public SaveFileCommand(OpenFileCommand openFileCommand, StringBuilder fileContent) {
        this.openFileCommand = openFileCommand;
        this.fileContent = fileContent;
    }
    /**
     * Sets the file name for saving.
     *
     * @param fileName The name of the file to be set.
     */
    @Override
    public void setFileName(String fileName) {
    }
    /**
     * Retrieves the file name of the currently opened file.
     *
     * @return the file name of the currently opened file
     */
    @Override
    public String getFileName() {
        return openFileCommand.getFileName();
    }
    /**
     * Executes the save file command.
     * Saves the content of the currently opened file to the file with the same name.
     * If no file is currently opened, displays an error message.
     */
    @Override
    public void execute() {
        String fileName = getFileName();
        if (fileName != null && !fileName.isEmpty()) {
            saveToFile(fileName, fileContent);
        } else {
            System.out.println("No file is currently open. Use 'open' to open a file.");
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
            System.out.println("Successfully saved " + new File(fileName).getName());
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }
}