package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Command to insert a row into a table.
 */
public class InsertCommand implements Command {
    private final Database database;
    private String tableName;
    private String[] values;

    /**
     * Constructs an InsertCommand with the specified database.
     *
     * @param database The database containing the table to insert into.
     */
    public InsertCommand(Database database) {
        this.database = database;
    }

    /**
     * Executes the command to insert a row into the table.
     *
     * @param parameter The parameter associated with the command.
     */
    @Override
    public void execute(String parameter) {
        String[] params = parameter.split("\\s+");
        if (params.length >= 2) {
            String tableName = params[0];
            Table table = database.getTableByName(tableName);
            if (table != null) {
                values = Arrays.copyOfRange(params, 1, params.length); // Initialize the values array here
                if (values.length == table.getColumns().size()) {
                    Row newRow = new Row();
                    for (int i = 0; i < values.length; i++) {
                        newRow.addValue(table.getColumns().get(i).getName(), values[i]);
                    }
                    table.addRow(newRow);
                    updateTextFile(table);
                    System.out.println("Row inserted successfully into table '" + tableName + "'.");
                } else {
                    System.out.println("Number of values does not match the number of columns in the table.");
                }
            } else {
                System.out.println("Table '" + tableName + "' not found.");
            }
        } else {
            System.out.println("Invalid parameters. Usage: insert <tableName> <value1> <value2> ... <valueN>");
        }
    }

    /**
     * Updates the associated text file with the newly inserted row.
     *
     * @param table The table to update.
     */
    private void updateTextFile(Table table) {
        String fileName = table.getAssociatedTextFile();
        try {
            FileWriter writer = new FileWriter(fileName, true);

            StringBuilder newRow = new StringBuilder();
            for (String value : values) {
                newRow.append(value).append(" ");
            }
            newRow.append(System.lineSeparator());

            writer.write(newRow.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error updating text file: " + e.getMessage());
        }
    }
}