package project.OOP2.f22621615.functionality;


import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.database.Row;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.interfaces.Command;

import java.util.List;
import java.text.DecimalFormat;

/**
 * Command to perform aggregate operations on numeric columns of a table.
 */
public class AggregateCommand implements Command {
    private Database database;
    private String tableName;
    private String searchColumnName;
    private Object searchValue;
    private String targetColumnName;
    private String operation;

    /**
     * Constructs an AggregateCommand with the specified database.
     *
     * @param database The database containing the table to perform aggregate operations on.
     */
    public AggregateCommand(Database database) {
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
     * Sets the name of the column to search in.
     *
     * @param searchColumnName The name of the column to search in.
     */
    public void setSearchColumnName(String searchColumnName) {
        this.searchColumnName = searchColumnName;
    }

    /**
     * Sets the value to search for in the specified column.
     *
     * @param searchValue The value to search for.
     */
    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * Sets the name of the target column to perform the aggregate operation on.
     *
     * @param targetColumnName The name of the target column.
     */
    public void setTargetColumnName(String targetColumnName) {
        this.targetColumnName = targetColumnName;
    }

    /**
     * Sets the type of aggregate operation to perform.
     *
     * @param operation The aggregate operation to perform.
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Executes the command to perform the aggregate operation.
     * Options: sum, product, maximum, minimum.
     *
     * @param parameter The command parameters in the format "tableName searchColumnName searchValue targetColumnName operation".
     */
    @Override
    public void execute(String parameter) {
        String[] params = parameter.split("\\s+");
        if (params.length == 5) {
            setTableName(params[0]);
            setSearchColumnName(params[1]);
            setSearchValue(params[2]);
            setTargetColumnName(params[3]);
            setOperation(params[4]);
            performAggregate();
        } else {
            System.out.println("Invalid parameters. Usage: aggregate <tableName> <searchColumnName> <searchValue> <targetColumnName> <operation>");
        }
    }

    /**
     * Performs the aggregate operation based on the specified parameters.
     */
    private void performAggregate() {
        Table table = database.getTableByName(tableName);
        if (table == null) {
            System.out.println("Table '" + tableName + "' not found.");
            return;
        }

        DataType searchColumnType = table.getColumn(searchColumnName).getType();
        DataType targetColumnType = table.getColumn(targetColumnName).getType();
        if (!isNumericType(searchColumnType) || !isNumericType(targetColumnType)) {
            System.out.println("Error: Search column and target column must be numeric.");
            return;
        }

        double result = 0;
        double productResult = 1;
        boolean first = true;

        List<Row> rows = table.getRows();
        for (Row row : rows) {
            Object searchColumnValue = row.getValue(searchColumnName);
            if (searchColumnValue.toString().equals(searchValue.toString())) {
                Object targetColumnValue = row.getValue(targetColumnName);
                double numericValue = Double.parseDouble(targetColumnValue.toString());
                switch (operation) {
                    case "sum":
                        result += numericValue;
                        break;
                    case "product":
                        productResult *= numericValue;
                        break;
                    case "maximum":
                        if (first) {
                            result = numericValue;
                            first = false;
                        } else {
                            result = Math.max(result, numericValue);
                        }
                        break;
                    case "minimum":
                        if (first) {
                            result = numericValue;
                            first = false;
                        } else {
                            result = Math.min(result, numericValue);
                        }
                        break;
                    default:
                        System.out.println("Unsupported operation: " + operation);
                        return;
                }
            }
        }

        if (operation.equals("product")) {
            DecimalFormat df = new DecimalFormat("#,###");
            String formattedResult = df.format(productResult);
            System.out.println("Aggregate result: " + formattedResult);
        } else {
            System.out.println("Aggregate result: " + result);
        }
    }

    /**
     * Checks if the specified data type is numeric (INTEGER or FLOAT).
     *
     * @param dataType The data type to check.
     * @return True if the data type is numeric, false otherwise.
     */
    private boolean isNumericType(DataType dataType) {
        return dataType == DataType.INTEGER || dataType == DataType.FLOAT;
    }
}