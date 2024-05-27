package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

/**
 * Command to describe the structure of a table.
 */
public class DescribeTableCommand implements Command {
    private final Database database;
    private String tableName;

    /**
     * Constructs a DescribeTableCommand with the specified database.
     *
     * @param database The database containing the table to describe.
     */
    public DescribeTableCommand(Database database) {
        this.database = database;
    }

    /**
     * Sets the name of the table.
     *
     * @param tableName The name of the table.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Executes the command to describe the structure of the table.
     */
    @Override
    public void execute(String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            describe(parameter);
        } else {
            System.out.println("Invalid parameters. Usage: describe <tableName>");
        }
    }

    /**
     * Describes the structure of the specified table.
     *
     * @param tableName The name of the table to describe.
     */
    public void describe(String tableName) {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            System.out.println("Table: " + tableName);
            System.out.println("+----------------------+------------+");
            System.out.println("| Column Name          | Data Type  |");
            System.out.println("+----------------------+------------+");
            table.getColumns().forEach(column ->
                    System.out.printf("| %-20s | %-10s |%n", column.getName(), column.getType()));
            System.out.println("+----------------------+------------+");
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }
}