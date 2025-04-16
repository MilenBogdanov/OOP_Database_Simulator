package project.OOP2.f22621615.functionality;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.exceptions.ColumnNotFoundException;
import project.OOP2.f22621615.exceptions.TableNotFoundException;
import project.OOP2.f22621615.interfaces.Command;

import java.util.HashSet;
import java.util.Set;

/**
 * The InnerJoinCommand class implements the Command interface to execute
 * an inner join operation between two tables in a database.
 */
public class InnerJoinCommand implements Command {
    private final Database database;
    private Table table1;
    private Column column1;
    private Table table2;
    private Column column2;

    /**
     * Constructs an InnerJoinCommand with the given database.
     *
     * @param database The database where the command is executed.
     */
    public InnerJoinCommand(Database database) {
        this.database = database;
    }

    /**
     * Executes the inner join operation using the provided parameters.
     *
     * @param parameter A string containing the parameters for the inner join operation.
     */
    @Override
    public void execute(String parameter) {
        try {
            String[] parameters = parameter.split("\\s+");
            if (parameters.length != 4) {
                throw new IllegalArgumentException("Invalid parameters. Usage: innerjoin <table1> <column1> <table2> <column2>");
            }

            String table1Name = parameters[0];
            String column1Name = parameters[1];
            String table2Name = parameters[2];
            String column2Name = parameters[3];

            table1 = database.getTableByName(table1Name);
            table2 = database.getTableByName(table2Name);

            if (table1 == null) {
                throw new TableNotFoundException("Table " + table1Name + " not found.");
            }

            if (table2 == null) {
                throw new TableNotFoundException("Table " + table2Name + " not found.");
            }

            column1 = table1.getColumnByName(column1Name);
            column2 = table2.getColumnByName(column2Name);

            if (column1 == null) {
                throw new ColumnNotFoundException("Column " + column1Name + " in table " + table1Name + " not found.");
            }

            if (column2 == null) {
                throw new ColumnNotFoundException("Column " + column2Name + " in table " + table2Name + " not found.");
            }

            Table joinedTable = performInnerJoin();
            database.addTable(joinedTable);
            System.out.println("Joined table identifier: " + joinedTable.getName());

        } catch (IllegalArgumentException | TableNotFoundException | ColumnNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Performs the inner join operation between the tables.
     *
     * @return The resulting table after the inner join operation.
     */
    private Table performInnerJoin() {
        String joinedTableName = table1.getName() + "_" + table2.getName() + "_join";
        Table joinedTable = new Table(joinedTableName);

        Set<String> columnNames = new HashSet<>();
        for (Column column : table1.getColumns()) {
            if (!column.equals(column1)) {
                String columnName = table1.getName() + "." + column.getName();
                columnNames.add(columnName);
                joinedTable.addColumn(new Column(columnName, column.getType()));
            }
        }
        for (Column column : table2.getColumns()) {
            if (!column.equals(column2)) {
                String columnName = table2.getName() + "." + column.getName();
                columnNames.add(columnName);
                joinedTable.addColumn(new Column(columnName, column.getType()));
            }
        }

        for (Row row1 : table1.getRows()) {
            Object value1 = row1.getValue(column1.getName());
            for (Row row2 : table2.getRows()) {
                Object value2 = row2.getValue(column2.getName());
                if (value1 != null && value2 != null && value1.toString().trim().equalsIgnoreCase(value2.toString().trim())) {
                    Row joinedRow = new Row();
                    for (String columnName : columnNames) {
                        if (columnName.startsWith(table1.getName())) {
                            Object columnValue = row1.getValue(columnName.substring(table1.getName().length() + 1));
                            joinedRow.addValue(columnName, columnValue);
                        } else if (columnName.startsWith(table2.getName())) {
                            Object columnValue = row2.getValue(columnName.substring(table2.getName().length() + 1));
                            joinedRow.addValue(columnName, columnValue);
                        }
                    }
                    joinedTable.addRow(joinedRow);
                }
            }
        }
        return joinedTable;
    }
}