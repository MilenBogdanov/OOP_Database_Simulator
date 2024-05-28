package project.OOP2.f22621615.database;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Table} class represents a table in a database.
 * It contains information about the table's name, columns, and rows, and provides methods
 * to manipulate the table's structure and data.
 */
public class Table {
    private String name;
    private List<Column> columns;
    private List<Row> rows;

    /**
     * Constructs a new {@code Table} with the specified name.
     *
     * @param name the name of the table
     */
    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    /**
     * Adds a column to the table.
     *
     * @param column the column to add
     */
    public void addColumn(Column column) {
        columns.add(column);
    }

    /**
     * Returns the name of the table.
     *
     * @return the name of the table
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of columns in the table.
     *
     * @return the list of columns
     */
    public List<Column> getColumns() {
        return columns;
    }

    /**
     * Returns the list of rows in the table.
     *
     * @return the list of rows
     */
    public List<Row> getRows() {
        return rows;
    }

    /**
     * Adds a row to the table.
     *
     * @param row the row to add
     */
    public void addRow(Row row) {
        rows.add(row);
    }

    /**
     * Returns a string representation of the table.
     *
     * @return a string representation of the table
     */
    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                ", rows=" + rows +
                '}';
    }

    /**
     * Returns the names of all columns in the table.
     *
     * @return the names of all columns
     */
    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        for (Column column : columns) {
            columnNames.add(column.getName());
        }
        return columnNames;
    }

    /**
     * Returns the associated text file for the table.
     *
     * @return the name of the associated text file
     */
    public String getAssociatedTextFile() {
        return name + ".txt";
    }

    /**
     * Retrieves a column by its name.
     *
     * @param columnName the name of the column to retrieve
     * @return the column with the specified name, or {@code null} if not found
     */
    public Column getColumn(String columnName) {
        for (Column column : columns) {
            if (column.getName().equals(columnName)) {
                return column;
            }
        }
        return null;
    }

    /**
     * Retrieves a column by its name. This method is the same as getColumn but added for compatibility.
     *
     * @param columnName the name of the column to retrieve
     * @return the column with the specified name, or {@code null} if not found
     */
    public Column getColumnByName(String columnName) {
        return getColumn(columnName);
    }

    /**
     * Sets the name of the table.
     *
     * @param name the new name of the table
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns a string containing the names of all columns in the table, separated by commas.
     *
     * @return a string containing column names separated by commas
     */
    public String getColumnNamesAsString() {
        StringBuilder columnNamesString = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                columnNamesString.append(", ");
            }
            columnNamesString.append(columns.get(i).getName());
        }
        return columnNamesString.toString();
    }
}