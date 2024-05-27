package project.OOP2.f22621615.interfaces;

/**
 * The {@code Command} interface represents a command that can be executed.
 * Classes implementing this interface must provide an implementation for the {@link #execute(String)} method.
 */
public interface Command {
    /**
     * Executes the command.
     * Implementations of this method should define the behavior of the command.
     *
     * @param parameter The parameter associated with the command.
     */
    void execute(String parameter);
}