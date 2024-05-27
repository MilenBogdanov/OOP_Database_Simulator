package project.OOP2.f22621615.basic_filefunctions;
import project.OOP2.f22621615.interfaces.Command;

public class ExitCommand implements Command {
    @Override
    public void execute(String parameter) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}