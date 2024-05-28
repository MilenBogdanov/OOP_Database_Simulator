package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.enums.CommandEnum;
import project.OOP2.f22621615.functionality.*;
import project.OOP2.f22621615.interfaces.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Central hub for executing various commands in the application.
 */
public class CommandCenter {
    private OpenFileCommand openFileCommand;
    private Map<CommandEnum, Command> commands;
    private Database database;
    private StringBuilder fileContent;
    private String fileName;
    private String lastLoadedFile;

    /**
     * Constructs a CommandCenter with the specified database.
     *
     * @param database The database instance to operate on.
     */
    public CommandCenter(Database database) {
        this.database = database;
        this.fileContent = new StringBuilder();
        this.fileName = "";
        this.openFileCommand = new OpenFileCommand(fileContent);
        initializeCommands();
    }

    /**
     * Initializes all the available commands and maps them to their respective enums.
     */
    private void initializeCommands() {
        commands = new HashMap<>();

        commands.put(CommandEnum.OPEN, new OpenFileCommand(fileContent));
        commands.put(CommandEnum.LOAD, new LoadTableFromTextFileCommand(database));
        commands.put(CommandEnum.CLOSE, new CloseFileCommand(fileContent));
        commands.put(CommandEnum.SAVE, new SaveFileCommand(fileName, fileContent, database));
        commands.put(CommandEnum.SAVEAS, new SaveFileAsCommand(fileContent));
        commands.put(CommandEnum.HELP, new PrintHelpCommand());
        commands.put(CommandEnum.EXIT, new ExitCommand());
        commands.put(CommandEnum.SHOWTABLES, new ShowTablesCommand(database));
        commands.put(CommandEnum.DESCRIBE, new DescribeTableCommand(database));
        commands.put(CommandEnum.PRINT, new PrintTableRowsCommand(database));
        commands.put(CommandEnum.EXPORT, new ExportTableCommand(database));
        commands.put(CommandEnum.SELECT, new SelectCommand(database));
        commands.put(CommandEnum.ADDCOLUMN, new AddColumnCommand(database));
        commands.put(CommandEnum.UPDATE, new UpdateCommand(database));
        commands.put(CommandEnum.DELETE, new DeleteCommand(database));
        commands.put(CommandEnum.INSERT, new InsertCommand(database));
        commands.put(CommandEnum.RENAME, new RenameTableCommand(database));
        commands.put(CommandEnum.COUNT, new CountRowsCommand(database));
        commands.put(CommandEnum.AGGREGATE, new AggregateCommand(database));
        commands.put(CommandEnum.INNERJOIN, new InnerJoinCommand(database));
    }

    /**
     * Executes the specified command with the given parameter.
     *
     * @param commandName The name of the command to execute.
     * @param parameter   The parameter associated with the command.
     */
    public void executeCommand(CommandEnum commandName, String parameter) {
        Command command = commands.get(commandName);
        if (command != null) {
            if (command instanceof UpdateCommand) {
                ((UpdateCommand) command).setFileName(lastLoadedFile);
            } else if (command instanceof SaveFileCommand) {
                ((SaveFileCommand) command).setFileName(lastLoadedFile);
            }
            command.execute(parameter);
            if (commandName == CommandEnum.LOAD) {
                lastLoadedFile = parameter;
            }
        } else {
            System.out.println("Invalid command. Type 'help' to see the list of commands.");
        }
    }
}