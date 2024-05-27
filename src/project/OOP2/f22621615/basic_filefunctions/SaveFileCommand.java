package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Command to save the database content to a file.
 */
public class SaveFileCommand implements Command, FileCommand {
    private String fileName;
    private StringBuilder fileContent;
    private Database database;

    /**
     * Constructs a SaveFileCommand with the specified file name, file content, and database.
     *
     * @param fileName    The name of the file to save to.
     * @param fileContent The content to be saved to the file.
     * @param database    The database containing the data to be saved.
     */
    public SaveFileCommand(String fileName, StringBuilder fileContent, Database database) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.database = database;
    }

    /**
     * Executes the command to save the database content to a file.
     *
     * @param parameter This parameter is not used in this implementation.
     */
    @Override
    public void execute(String parameter) {
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("No file path provided.");
            return;
        }

        saveToFile(fileName);
    }

    /**
     * Saves the database content to the specified file.
     *
     * @param fileName The name of the file to save to.
     */
    private void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(database.getDataAsString());
            System.out.println("Successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }

    /**
     * Sets the name of the file to save to.
     *
     * @param fileName The name of the file.
     */
    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the name of the file to save to.
     *
     * @return The name of the file.
     */
    @Override
    public String getFileName() {
        return fileName;
    }
}