package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;

/**
 * Command to close a file by clearing its content.
 * Implements the Command interface.
 */
public class CloseFileCommand implements Command {
    private StringBuilder fileContent;

    /**
     * Constructs a CloseFileCommand with the specified file content.
     *
     * @param fileContent the content of the file to be closed.
     */
    public CloseFileCommand(StringBuilder fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * Executes the close file command. If the file content is not empty,
     * it clears the content and prints a success message. Otherwise,
     * it prints a message indicating no file is currently open.
     *
     * @param parameter an optional parameter for the command (not used in this implementation).
     */
    @Override
    public void execute(String parameter) {
        if (!fileContent.isEmpty()) {
            fileContent.setLength(0);
            System.out.println("Successfully closed the file.");
        } else {
            System.out.println("No file is currently open.");
        }
    }
}