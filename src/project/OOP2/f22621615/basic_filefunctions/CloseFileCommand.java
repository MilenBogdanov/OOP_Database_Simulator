package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;

public class CloseFileCommand implements Command {
    private StringBuilder fileContent;

    public CloseFileCommand(StringBuilder fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public void execute(String parameter) {
        if (fileContent.length() >= 0) {
            fileContent.setLength(0);
            System.out.println("Successfully closed the file.");
        } else {
            System.out.println("No file is currently open.");
        }
    }
}