package project.OOP2.f22621615.database;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@code Row} class represents a row in a database table.
 * It contains a map of column names to values and provides methods to add, retrieve, and set values.
 */
public class Row {
    private Map<String, Object> values;

    /**
     * Constructs a new {@code Row} with an empty map of values.
     */
    public Row() {
        this.values = new LinkedHashMap<>();
    }

    /**
     * Adds a value to the row for the specified column name.
     *
     * @param columnName the name of the column
     * @param value      the value to add
     */
    public void addValue(String columnName, Object value) {
        values.put(columnName, value);
    }

    /**
     * Retrieves the value of a specified column from the row.
     *
     * @param columnName the name of the column
     * @return the value of the specified column, or {@code null} if the column does not exist in the row
     */
    public Object getValue(String columnName) {
        return values.get(columnName);
    }

    /**
     * Sets the value of a specified column in the row.
     *
     * @param columnName the name of the column
     * @param value      the new value to set
     */
    public void setValue(String columnName, Object value) {
        values.put(columnName, value);
    }

    /**
     * Returns the set of column names present in the row.
     *
     * @return the set of column names
     */
    public Set<String> getColumnNames() {
        return values.keySet();
    }

    /**
     * Returns a string representation of the row.
     * The string representation consists of all values in the row, separated by spaces.
     *
     * @return a string representation of the row
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String columnName : getColumnNames()) {
            Object value = values.get(columnName);
            sb.append(value != null ? value : "null").append(" ");
        }
        return sb.toString().trim();
    }
}
