package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;

/**
 * The {@code CloseFileCommand} class represents a command to close a file.
 * It implements the {@link Command} interface and defines behavior to execute the command.
 */
public class CloseFileCommand implements Command {
    private StringBuilder fileContent;
    /**
     * Constructs a new {@code CloseFileCommand} with the specified file content.
     *
     * @param fileContent the content of the file to be closed
     */
    public CloseFileCommand(StringBuilder fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * Executes the close file command.
     * If the file content length is greater than or equal to 0, the file content is cleared.
     * Otherwise, a message indicating that no file is currently open is printed.
     */
    @Override
    public void execute() {
        if (fileContent.length() >= 0) {
            fileContent.setLength(0);
            System.out.println("Successfully closed the file.");
        } else {
            System.out.println("No file is currently open.");
        }
    }
}