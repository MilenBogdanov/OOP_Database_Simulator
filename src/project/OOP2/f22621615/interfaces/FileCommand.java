package project.OOP2.f22621615.interfaces;

/**
 * The {@code FileCommand} interface represents a command that operates on a file.
 * Classes implementing this interface must provide implementations for the {@link #setFileName(String)}
 * and {@link #getFileName()} methods to set and retrieve the file name, respectively.
 */
public interface FileCommand {
    /**
     * Sets the file name to be operated on by the command.
     *
     * @param fileName the name of the file
     */
    void setFileName(String fileName);

    /**
     * Retrieves the file name currently set for the command.
     *
     * @return the name of the file
     */
    String getFileName();
}