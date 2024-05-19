package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.interfaces.Command;

/*import java.util.HashMap;
import java.util.Map;

public class PrintTableRowsCommand implements Command {
    private Database database;
    private String tableName;

    public PrintTableRowsCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            System.out.println("Rows from table " + tableName + ":");
            printTable(table);
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    private void printTable(Table table) {
        Map<String, Integer> columnWidths = calculateColumnWidths(table);
        printSeparator(columnWidths);
        printHeader(table, columnWidths);
        printSeparator(columnWidths);
        for (Row row : table.getRows()) {
            printRow(row, columnWidths);
        }
        printSeparator(columnWidths);
    }

    private Map<String, Integer> calculateColumnWidths(Table table) {
        Map<String, Integer> columnWidths = new HashMap<>();
        for (String columnName : table.getColumnNames()) {
            columnWidths.put(columnName, Math.max(columnName.length(), getMaxColumnWidth(table, columnName)));
        }
        return columnWidths;
    }

    private int getMaxColumnWidth(Table table, String columnName) {
        int maxWidth = columnName.length();
        for (Row row : table.getRows()) {
            Object value = row.getValue(columnName);
            if (value != null) {
                maxWidth = Math.max(maxWidth, value.toString().length());
            }
        }
        return maxWidth;
    }

    private void printSeparator(Map<String, Integer> columnWidths) {
        for (String columnName : columnWidths.keySet()) {
            System.out.print("+");
            for (int i = 0; i < columnWidths.get(columnName) + 2; i++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }

    private void printHeader(Table table, Map<String, Integer> columnWidths) {
        for (String columnName : table.getColumnNames()) {
            System.out.printf("| %-" + columnWidths.get(columnName) + "s ", columnName);
        }
        System.out.println("|");
    }

    private void printRow(Row row, Map<String, Integer> columnWidths) {
        for (String columnName : row.getColumnNames()) {
            Object value = row.getValue(columnName);
            String valueString = (value != null) ? value.toString() : "null";
            System.out.printf("| %-" + columnWidths.get(columnName) + "s ", valueString);
        }
        System.out.println("|");
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}*/
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
/**
 * Command to print rows of a table with pagination.
 */
public class PrintTableRowsCommand implements Command {
    private Database database;
    private String tableName;
    private int pageSize = 5;

    /**
     * Constructs a PrintTableRowsCommand with the specified database.
     *
     * @param database The database containing the table.
     */
    public PrintTableRowsCommand(Database database) {
        this.database = database;
    }

    /**
     * Executes the command to print rows of the specified table.
     */
    @Override
    public void execute() {
        Table table = database.getTableByName(tableName);
        if (table != null) {
            System.out.println("Rows from table " + tableName + ":");
            browseTable(table);
        } else {
            System.out.println("Table '" + tableName + "' not found.");
        }
    }

    /**
     * Browses the table with pagination.
     *
     * @param table The table to browse.
     */
    private void browseTable(Table table) {
        List<Row> rows = table.getRows();
        Iterator<Row> iterator = rows.iterator();
        Scanner scanner = new Scanner(System.in);

        int currentPage = 1;
        int totalPages = (rows.size() + pageSize - 1) / pageSize;

        boolean exit = false;
        while (!exit) {
            System.out.println("Page " + currentPage + " of " + totalPages + ":");

            printHeader(table);

            int startIdx = (currentPage - 1) * pageSize;
            int endIdx = Math.min(currentPage * pageSize, rows.size());
            for (int i = startIdx; i < endIdx; i++) {
                printRow(rows.get(i), table);
            }

            System.out.println("\nCommands: next, previous, exit");
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "next":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("Already on the last page.");
                    }
                    break;
                case "previous":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Already on the first page.");
                    }
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command. Please enter 'next', 'previous', or 'exit'.");
            }
        }
    }

    /**
     * Prints the header of the table.
     *
     * @param table The table.
     */
    private void printHeader(Table table) {
        for (String columnName : table.getColumnNames()) {
            System.out.print("|");
            System.out.printf("%-20s", columnName);
        }
        System.out.println("|");
        for (String columnName : table.getColumnNames()) {
            System.out.print("|--------------------");
        }
        System.out.println("|");
    }

    /**
     * Prints a row of the table.
     *
     * @param row   The row to print.
     * @param table The table.
     */
    private void printRow(Row row, Table table) {
        for (String columnName : table.getColumnNames()) {
            System.out.print("|");
            Object value = row.getValue(columnName);
            String valueString = (value != null) ? value.toString() : "null";
            System.out.printf("%-20s", valueString);
        }
        System.out.println("|");
    }

    /**
     * Sets the name of the table to print.
     *
     * @param tableName The name of the table.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}