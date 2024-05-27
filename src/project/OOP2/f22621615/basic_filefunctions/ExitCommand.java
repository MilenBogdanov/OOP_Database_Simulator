package project.OOP2.f22621615.basic_filefunctions;
import project.OOP2.f22621615.interfaces.Command;

/**
 * Command to exit the program.
 */
public class ExitCommand implements Command {
    /**
     * Executes the command to exit the program.
     *
     * @param parameter This parameter is not used in this implementation.
     */
    @Override
    public void execute(String parameter) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}