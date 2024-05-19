package project.OOP2.f22621615.basic_filefunctions;
import project.OOP2.f22621615.interfaces.Command;

/**
 * The {@code ExitCommand} class represents a command to exit the program.
 * It implements the {@link Command} interface and defines behavior to execute the command.
 */
public class ExitCommand implements Command {
    /**
     * Executes the exit command.
     * Prints a message indicating that the program is exiting and terminates the program.
     */
    @Override
    public void execute() {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}