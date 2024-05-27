package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFileCommand implements Command, FileCommand {
    private String fileName;
    private StringBuilder fileContent;
    private Database database;

    public SaveFileCommand(String fileName, StringBuilder fileContent, Database database) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.database = database;
    }

    @Override
    public void execute(String parameter) {
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("No file path provided.");
            return;
        }

        saveToFile(fileName);
    }

    private void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(database.getDataAsString());
            System.out.println("Successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}