package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

/**
 * Command to show the tables loaded in the database.
 */
public class ShowTablesCommand implements Command {
    private Database database;

    /**
     * Constructs a ShowTablesCommand with the specified database.
     *
     * @param database The database containing the tables.
     */
    public ShowTablesCommand(Database database) {
        this.database = database;
    }

    /**
     * Executes the command to display the tables loaded in the database.
     *
     * @param parameter This parameter is not used in this command.
     */
    @Override
    public void execute(String parameter) {
        System.out.println("Tables loaded in the database:");
        System.out.println("+----------------------+");
        System.out.println("|      Table Name      |");
        System.out.println("+----------------------+");

        for (Table table : database.getTables()) {
            System.out.printf("| %-20s |%n", table.getName());
        }

        System.out.println("+----------------------+");
    }
}